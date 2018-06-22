package com.todonotes.userservice.service;

import java.util.concurrent.ExecutionException;

import com.todonotes.userservice.model.LoginModel;
import com.todonotes.userservice.model.UserModel;

public interface IUserService {

	boolean register(UserModel userModel);
	boolean verifyEmail(String token) throws NumberFormatException, InterruptedException, ExecutionException;
	String checkLogin(LoginModel loginModel);
	boolean isEmailVerified(String email);
}
