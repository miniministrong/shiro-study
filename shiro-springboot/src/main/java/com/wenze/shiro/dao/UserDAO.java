package com.wenze.shiro.dao;

import com.wenze.shiro.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {
  /**
   * 根据用户名查询用户
   * @param username 用户名
   * @return 用户数据
   */
  public User queryUserByName(String username);
}
