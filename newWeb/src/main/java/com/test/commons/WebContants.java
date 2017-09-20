package com.test.commons;

public class WebContants {
	//Session key
	public final static String USER_KEY = "user";
	public final static String NOT_LOGIN = "Not Login";
	public final static String AUTHENTICATION_FAILED = "Authentication Failed";
	//Line Separator
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");
	public final static String UPLOAD_PATH = "C:/javaide/test/newWeb/uploaded/";
	//paging 
	public final static int NUM_PER_PAGE = 10;  //페이지당 레코드 수 
	public final static int PAGE_PER_BLOCK = 10;  //블록당 페이지 링크 수
	//naver로 로그인 test 
	public final static String NAVER_CLIENT_ID = "HskzFHfUZeJS8Hcgj2iJ";
	public final static String NAVER_CLIENT_SECRET = "_wmxEmqG4J";
	public final static String NAVER_CALLBACK_URL = "Http://127.0.0.1:8008/newWeb/users/callback";
	public final static String NAVER_SERVICE_URL = "http://127.0.0.1:8008";
	
	
} 
