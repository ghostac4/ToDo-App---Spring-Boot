package com.todonotes.userservice.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todonotes.userservice.model.LoginModel;
import com.todonotes.userservice.model.UserModel;
import com.todonotes.userservice.service.IUserService;

@RestController
public class UserController {
	
	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Validated @RequestBody UserModel userModel,BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		if(userService.register(userModel))
			return new ResponseEntity<>("Successfully Register. Verication Email Sent",HttpStatus.OK);
		return new ResponseEntity<>("Email Already Exists.",HttpStatus.NOT_ACCEPTABLE);
	}
	
	@GetMapping("/verifyEmail/{token}")
	public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) throws NumberFormatException, InterruptedException, ExecutionException{
		if(userService.verifyEmail(token))
			return new ResponseEntity<>("Email Verification Done",HttpStatus.OK);
		return new ResponseEntity<>(token,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody LoginModel loginModel,BindingResult bindingResult){
		if(bindingResult.hasErrors())
			return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
		if(userService.isEmailVerified(loginModel.getEmail())) {
			String token = userService.checkLogin(loginModel);
			if(token == null)
				return new ResponseEntity<>("Invalid Password",HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(token,HttpStatus.OK);
		}
		return new ResponseEntity<>("Email Not Verified",HttpStatus.NOT_FOUND);
	}
}
