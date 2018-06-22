package com.todonotes.userservice.dao;

import com.todonotes.userservice.model.UserModel;

public interface IUserDao {

	boolean register(UserModel userModel);
	boolean checkUser(String email);
	long getUserIdByEmail(String email);
	boolean isVerified(long id);
	void setVerified(long id);
	boolean isVerified(String email);
	UserModel getUserByEmail(String email);
	UserModel getUserById(long id);
}
