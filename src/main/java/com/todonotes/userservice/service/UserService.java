package com.todonotes.userservice.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todonotes.userservice.dao.IUserDao;
import com.todonotes.userservice.exceptions.UserNotFoundException;
import com.todonotes.userservice.model.LoginModel;
import com.todonotes.userservice.model.UserModel;
import com.todonotes.utils.JWTToken;
import com.todonotes.utils.RabbitMQSender;
import com.todonotes.utils.RedisService;

@Service
@Transactional
public class UserService implements IUserService{

	@Autowired
	private IUserDao userDao;
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Override
	public boolean register(UserModel userModel) {
		if(userDao.checkUser(userModel.getEmail())) {
			userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
			userDao.register(userModel);
			String id = userDao.getUserIdByEmail(userModel.getEmail())+"";
			String token = JWTToken.getToken(id,86400000);
			rabbitMQSender.sendEmailQueue(userModel.getEmail()+";"+userModel.getName()+";"+token);
			RedisService.saveToken(id, token);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean verifyEmail(String token) throws NumberFormatException, InterruptedException, ExecutionException {
		long id = Long.parseLong(JWTToken.parseJWT(token));
		if(userDao.isVerified(id)) 
			return false;
		else {
			String tokenFromRedis = RedisService.getSavedToken(id+"");
			if(tokenFromRedis == null) 
				return false;
			if(token.equals(tokenFromRedis)) {
				userDao.setVerified(id);
				RedisService.delSavedToken(id+"");
				return true;
			}
			return false;
		}		
	}
	
	@Override
	public String checkLogin(LoginModel loginModel) {
		UserModel userModel = userDao.getUserByEmail(loginModel.getEmail());
		if(userModel == null)
			throw new UserNotFoundException("User Not Exists With emailid "+loginModel.getEmail());
		if(BCrypt.checkpw(loginModel.getPassword(), userModel.getPassword())) {
			return JWTToken.getToken(userModel.getId()+"",864000000*10);
		}
		return null;
	}
	
	@Override
	public boolean isEmailVerified(String email) {
		return userDao.isVerified(email);
	}
}
