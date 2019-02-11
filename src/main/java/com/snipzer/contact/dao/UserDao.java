package com.snipzer.contact.dao;

import java.util.List;

import com.snipzer.contact.entity.User;

public interface UserDao {
	long save(User contact);
	void delete(Long id);
	User get(Long id);
	List<User> getAll();
}
