package com.test.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.test.commons.WebContants;
import com.test.user.User;

public class CheckLoginService {

	public boolean isLogin(HttpServletRequest request){
		HttpSession session = request.getSession();  
		User user = (User) session.getAttribute(WebContants.USER_KEY);  
		if(user == null){
			//ssion에 user가 없으면 
			return false;
		}else{
			//session 에 user가 있는지?  
			return true; 
		}
		
	}
}
