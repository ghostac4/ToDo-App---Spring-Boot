package com.todonotes.userservice.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.todonotes.userservice.model.UserModel;

@Repository
@SuppressWarnings("deprecation")
public class UserDao implements IUserDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean register(UserModel userModel) {
		Session session = sessionFactory.getCurrentSession();
		session.save(userModel);
		return true;
	}
	
	@Override
	public boolean checkUser(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("email", email));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		return userModel == null ? true:false;
	}
	
	@Override
	public long getUserIdByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("email", email));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		return userModel.getId();
	}

	@Override
	public boolean isVerified(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("id", id));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		return userModel.isVerified();
	}
	
	@Override
	public boolean isVerified(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("email", email));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		if(userModel == null)
			throw new UsernameNotFoundException("User Not Exists With emailid "+email);
		return userModel.isVerified();
	}
	
	@Override
	public void setVerified(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("id", id));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		userModel.setVerified(true);
	}
	
	@Override
	public UserModel getUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("email", email));
		return (UserModel) criteria.uniqueResult();
	}
	
	@Override
	public UserModel getUserById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class)
				.add(Restrictions.eq("id", id));
		return (UserModel) criteria.uniqueResult();
	}
}
