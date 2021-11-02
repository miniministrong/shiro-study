package com.wenze.shiro.service.impl;

import com.wenze.shiro.dao.UserDAO;
import com.wenze.shiro.model.User;
import com.wenze.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDAO userDAO;
  @Override
  public User queryUserByName(String username) {
    return userDAO.queryUserByName(username);
  }
}
