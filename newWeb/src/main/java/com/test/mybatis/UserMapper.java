package com.test.mybatis;

import org.apache.ibatis.annotations.Param;

import com.test.user.User;

public interface UserMapper {
    
  public int insert(User user);

  public User login(
    @Param("email") String email, 
    @Param("passwd") String passwd);

  public int update(User user);

  public int updatePasswd(
    @Param("currentPasswd") String currentPasswd, 
    @Param("newPasswd") String newPasswd, 
    @Param("email") String email);

  public int delete(User user);

  public User selectOne(String email);
    
}