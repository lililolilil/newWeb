package com.test.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.Service.CheckLoginService;

public class LoginCheckFilter implements Filter{
	Logger logger = LoggerFactory.getLogger(LoginCheckFilter.class);  
	CheckLoginService loginChecker = new CheckLoginService();
	
	private static String LOGIN_URL = "/users/login"; 
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fchain)
			throws IOException, ServletException {
		logger.info("LoginCheckFilter doFilter");
		if(loginChecker.isLogin((HttpServletRequest)request)){
			fchain.doFilter(request, response);
			
		}else{
			HttpServletRequest req = (HttpServletRequest)request; 
			//redirect url 
			// login?url=#이전 url 
			String login = req.getContextPath()+LOGIN_URL; 
			String url = req.getServletPath(); 
			String query = req.getQueryString();  
			if (query != null) url += "?"+ query;  
			logger.info("로그인 하지 않은 사용자의 접근!! 로그인 페이지로 redirect: " + url);
            url = URLEncoder.encode(url, "UTF-8");
			((HttpServletResponse)response).sendRedirect(login+"?url="+url);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
