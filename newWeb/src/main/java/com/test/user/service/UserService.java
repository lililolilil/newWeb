package com.test.user.service;

import com.test.user.User;

public interface UserService {
    
  //회원 가입
  public int addUser(User user);

  //로그인
  public User login(String email, String passwd);

  //내 정보 수정
  public int editAccount(User user);

  //비밀번호 변경
  public int changePasswd(String currentPasswd, String newPasswd, String email);

  //탈퇴
  public void bye(User user);

  //회원 찾기
  public User getUser(String email);
    
}