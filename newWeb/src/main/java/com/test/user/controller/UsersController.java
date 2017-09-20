package com.test.user.controller;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.board.exception.AuthenticationException;
import com.test.commons.WebContants;
import com.test.user.User;
import com.test.user.service.UserService;

@Controller
@RequestMapping("users")
@EnableWebMvc
public class UsersController {
	Logger logger = LoggerFactory.getLogger(UsersController.class);  

	@Autowired
	private UserService userService;
	//회원가입 
	@RequestMapping(value="signUp", method=RequestMethod.GET)
	public String signUp(Model model) {
		model.addAttribute("title","회원가입");
		return "users/signUp";
	}

	@RequestMapping(value="signUp", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	@ResponseBody
	public Map<String,String> signUp(@RequestBody String userInfo, Model model) {
		Map<String, String> resultMap = new HashMap<>();
		User user = new User();
		try {
			user = new ObjectMapper().readValue(userInfo, User.class);
			userService.addUser(user);
			logger.info("회원가입 성공! (email: "+user.getEmail()+"이름 :" + user.getName() );

		} catch(DuplicateKeyException e){
			logger.error("회원가입에 실패 했습니다. 이미 가입 된 아이디 입니다." , e); 
			resultMap.put("msg", "회원가입에 실패 했습니다. 이미 가입 된 아이디 입니다."); 
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch(Exception e){
			logger.error("회원가입에 실패했습니다.",e);
			resultMap.put("msg", "회원가입에 실패 했습니다."); 
		}   

		return resultMap; 

	}

	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome() {
		return "users/welcome";
	}
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("title","로그인");
		return "users/login";
	}
	@RequestMapping(value="Nlogin", method=RequestMethod.GET)
	public String Naverlogin(Model model) {
		model.addAttribute("title","네이버로 로그인");
		return "users/naverLogin";
	}
	@RequestMapping(value="callBack", method=RequestMethod.GET)
	public String NaverLoginCallback(Model model) {
		model.addAttribute("title","네이버로 로그인 콜백 ");
		return "users/callback";
	}
	@RequestMapping(value="login", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	@ResponseBody 
	public Map<String,String> login(@RequestBody String loginInfo, HttpSession session) {
		Map<String, String> resultMap = new HashMap<>();
		User user = new User();
		try {
			JSONObject jsonObj = new JSONObject(loginInfo); 
			String email = jsonObj.getString("email"); 
			String passwd = jsonObj.getString("passwd"); 
			
			user = userService.login(email, passwd);
			
			if (user == null) {
				logger.info(email + "사용자가 로그인을 시도 하였으나 실패하였음.");
				resultMap.put("err", "잘못 된 로그인입니다. email과 비밀번호를 확인해 주세요"); 
			} else {
				logger.info(email + "사용자가 로그인함."); 
				session.setAttribute(WebContants.USER_KEY, user);
			}
			
		}catch(Exception e){
			logger.error("사용자 로그인 실패",e);
			resultMap.put("err", "로그인 실패 했습니다.관리자에게 문의해 주세요."); 
		}   

		return resultMap; 
	}
	
	@RequestMapping(value="logOut", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute(WebContants.USER_KEY);
		logger.info("사용자 로그아웃 되었습니다." );
		return "redirect:/";

	}

	@RequestMapping(value="editAccount", method=RequestMethod.GET)
	public String editAccount(HttpServletRequest req, HttpSession session, Model model) throws Exception {
		model.addAttribute("title","개인정보변경");
		model.addAttribute("menu", "editAccount"); 
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 다시 돌아오기 위해
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트

			return "redirect:/users/login?url=" + url;
		}

		return "users/editAccount";
	}

	@RequestMapping(value="editAccount", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	@ResponseBody 
	public Map<String,String> editAccount(@RequestBody String newInfo, HttpSession session) {
		User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
		HashMap<String,String> resultMap = new HashMap<>(); 

		if (loginUser == null) {
			logger.info("session에 사용자가 존재 하지 않음. ");
			resultMap.put("err", "notLogin"); 
		}else{
			try{
				JSONObject jsonObj = new JSONObject(newInfo); 
				if(loginUser.getPasswd().equals(jsonObj.getString("passwd"))){
					loginUser.setMobile(jsonObj.getString("mobile"));
					loginUser.setName(jsonObj.getString("name"));
					int check = userService.editAccount(loginUser);
					if (check < 1) {
						logger.info("사용자의 정보를 변경 할 수 없습니다 :  " + loginUser.getEmail()); 
						resultMap.put("err", "사용자 정보를 변경하지 못하였습니다 .<br> 다시 시도해 주세요."); 
					} else{
						logger.info("사용자의 정보가 변경 되었습니다. : " + loginUser.getMobile() +"/"+ loginUser.getEmail()); 
						session.setAttribute(WebContants.USER_KEY, loginUser);
					}
				}else{
					logger.info("입력한 비밀번호가 맞지 않음. "+ loginUser.getEmail()); 
					resultMap.put("err", "입력한 비밀번호가 맞지 않아 정보를 수정할 수 없습니다.");
				}
			}catch (JSONException e) {
				logger.error("json 파라미터 오류 ", e);
				resultMap.put("err", "시스템 문제로 인해 정보 변경이 불가합니다.<br>관리자에게 문의하세요."); 
			}
		}

		return resultMap;

	}

	@RequestMapping(value="changePasswd", method=RequestMethod.GET)
	public String changePasswd(HttpServletRequest req, HttpSession session, Model model) throws Exception {
		model.addAttribute("title","비밀번호 변경");
		model.addAttribute("menu", "changePasswd"); 
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 다시 돌아오기 위해
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");
			return "redirect:/users/login?url=" + url;     
		}

		return "users/changePasswd";
	}
	@RequestMapping(value="changePasswd", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	@ResponseBody 
	public Map<String,String> changePasswd(@RequestBody String newPass, HttpSession session) {
		User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
		HashMap<String,String> resultMap = new HashMap<>(); 

		if (loginUser == null) {
			logger.info("session에 사용자가 존재 하지 않음. ");
			resultMap.put("err", "notLogin"); 
		}else{
			try{

				JSONObject jsonObj = new JSONObject(newPass); 
				String curPw = jsonObj.getString("currentPasswd"); 
				String newPw = jsonObj.getString("newPasswd"); 
				if(loginUser.getPasswd().equals(curPw)){
					int check = userService.changePasswd(curPw, newPw, loginUser.getEmail());
					if (check < 1) {
						logger.info("비밀번호를 변경 할 수 없습니다 :  " + loginUser.getEmail()); 
						resultMap.put("err", "비밀번호를 변경하지 못하였습니다 .<br> 다시 시도해 주세요."); 
					} else{
						logger.info(loginUser.getEmail()+ " 사용자의 비밀번호가 변경 됨."); 
						session.invalidate(); 
					}
				}else{
					logger.info("입력한 비밀번호가 맞지 않음. "+ loginUser.getEmail()); 
					resultMap.put("err", "입력한 비밀번호가 맞지 않아 정보를 수정할 수 없습니다.");
				}
			}catch (JSONException e) {
				logger.error("json 파라미터 오류 ", e);
				resultMap.put("err", "시스템 문제로 인해 정보 변경이 불가합니다.<br>관리자에게 문의하세요."); 
			}
		}

		return resultMap;

	}

	@RequestMapping(value="bye", method=RequestMethod.GET)
	public String bye(HttpServletRequest req, HttpSession session, Model model) throws Exception {
		model.addAttribute("title","회원 탈퇴");
		model.addAttribute("menu", "withdrawal"); 
		User user = (User)session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 다시 돌아오기 위해
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");

			return "redirect:/users/login?url=" + url;     
		}

		return "users/bye";
	}

	@RequestMapping(value="bye", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	@ResponseBody 
	public Map<String,String> bye(@RequestBody String byeInfo, HttpSession session) {
		User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
		HashMap<String,String> resultMap = new HashMap<>(); 

		if (loginUser == null) {
			logger.info("session에 사용자가 존재 하지 않음. ");
			resultMap.put("err", "notLogin"); 
		}else{
			try{

				JSONObject jsonObj = new JSONObject(byeInfo); 
				String passwd = jsonObj.getString("passwd"); 
				String email = jsonObj.getString("email"); 
				//String reason = jsonObj.getString("reason"); 
				if(loginUser.getPasswd().equals(passwd)){
					if (loginUser == null || !loginUser.getEmail().equals(email)) {
						resultMap.put("err", "입력한 정보가 탈퇴하려는 사용자의 정보와 같지 않습니다."); 
					}else{
						User user = userService.login(email, passwd);
						userService.bye(user);
						logger.info(loginUser.getEmail()+ " 사용자가 탈퇴 함. "); 
						session.invalidate(); 
					}
				}else{
					logger.info("입력한 비밀번호가 맞지 않음. "+ loginUser.getEmail()); 
					resultMap.put("err", "입력한 비밀번호가 맞지 않아 탈퇴할 수 없습니다.");
				}
			}catch (JSONException e) {
				logger.error("json 파라미터 오류 ", e);
				resultMap.put("err", "시스템 문제로 인해 탈퇴가 불가능 합니다.<br>관리자에게 문의하세요."); 
			}
		}

		return resultMap;

	}
}