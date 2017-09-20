package com.test.user.service;
//구현체 

import com.test.mybatis.UserMapper;
import com.test.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
  @Autowired
  private UserMapper userMapper;
    
  public int addUser(User user) {
	return userMapper.insert(user);
  }

  public User login(String email, String passwd) {
    return userMapper.login(email, passwd);
  }

  public int editAccount(User user) {
    return userMapper.update(user);
  }

  public int changePasswd(String currentPasswd, String newPasswd, String email) {
    return userMapper.updatePasswd(currentPasswd, newPasswd, email);
  }

  public void bye(User user) {
    userMapper.delete(user);
  }

  public User getUser(String email) {
    return userMapper.selectOne(email);
  }
    
}