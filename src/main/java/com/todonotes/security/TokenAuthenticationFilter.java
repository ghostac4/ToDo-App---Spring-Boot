package com.todonotes.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.todonotes.exceptions.AuthenticationException;
import com.todonotes.utils.JWTToken;

public class TokenAuthenticationFilter extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String tokenFromRequest = request.getHeader("userToken");
		if(tokenFromRequest == null)
			throw new AuthenticationException("Credentials Not Found");
		long id = Long.parseLong(JWTToken.parseJWT(tokenFromRequest));
		request.setAttribute("userId", id);
		return true;
	}
}
