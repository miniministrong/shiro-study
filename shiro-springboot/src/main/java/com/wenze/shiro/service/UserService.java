package com.wenze.shiro.service;

import com.wenze.shiro.model.User;

public interface UserService {
  /**
   * 根据用户名查询用户信息
   * @param username 用户名
   * @return 用户信息
   */
  public User queryUserByName(String username);
}
