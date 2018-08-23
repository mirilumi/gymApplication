package com.gym.application.service;

import com.gym.application.model.User;

import java.util.List;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public List<User> findAll();
    public User getOne(Integer id);
}
