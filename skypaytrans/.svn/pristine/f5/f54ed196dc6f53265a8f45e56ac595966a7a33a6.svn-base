package com.saifintex.dao;

import java.io.Serializable;
import java.util.List;

import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.web.entity.WebUsersEntity;

public interface BaseDAO<T, S extends Serializable> {

	public S save(T t);

	public T getOne(Class<T> t, S arg0);

	public List<T> findAll(Class<T> t);

	public void saveOrUpdate(T t);

	public List<T> findAll(Class<T> t, Integer pageNumber);

	public List<T> findAll(Class<T> t, Integer pageNumber, String sortBy);

	public List<T> findAll(Class<T> t, String sortBy);

	public T saveAndGet(T t);
	
	public T merge(T t);

	public UserEntity findByUserLogin(String param);

	public WebUsersEntity findByUserLoginWeb(String param);
	
	public AppPreferencesEntity getAppPreferences(int id);
	
	public UserEntity findByInviteCode(String refCode);
	public void update(T t);
	
	public T load(Class<T> clz,S id);
}
