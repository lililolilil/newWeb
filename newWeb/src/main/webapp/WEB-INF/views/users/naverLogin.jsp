<%@page import="com.test.commons.WebContants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>

<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
 <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
    $(document).ready(function() {
		addEvent(); 
    });
    var addEvent = function() {
		$("#loginForm").on("submit", function(e) {
	    	e.preventDefault(); 
		    var url = "${pageContext.request.contextPath}/users/login";
		    //var form_data = $("#signUpForm").serialize(); 
		    var loginInfo = {
				email : $("#email").val(),
				passwd : $("#passwd").val(),
		    };
		    var request = $.ajax({
				method : "POST",
				url : url,
				contentType : "application/json",
				//messagefileDir = C:/Users/suyeon/git/mcare-catholic-daegu/WebContent/WEB-INF/messages
				data : JSON.stringify(loginInfo),
				error : function(xhr, status, error) {
				    alert(error);
				},
				success : function(data) {
				    if(data.err != null){
						alert(data.err); 
				    }else{
						alert("로그인 하였습니다. "); 
						var prev_url = "${param.url}"; 
						if(prev_url !==""){
						    location.href= contextPath + prev_url; 
						}else{
							location.href= contextPath; 
						}
						
				    }//else
				   
				}//success
		    });//ajax  
		});//on 
    }//addEvent 
</script>
<form id="loginForm">
	<p style="margin: 0; padding: 0;">
		<input type="hidden" name="url" value="${param.url}" />
	</p>
	<label for="email" class="control-label col-sm-2">mail:</label>
	<div class="input-group">
		<input id="email" type="text" class="form-control" name="email"
			placeholder="Email"> <span class="input-group-addon"><i
			class="glyphicon glyphicon-envelope"></i></span>
	</div>
	<label for="passwd" class="control-label col-sm-2">비밀번호:</label>
	<div class="input-group">
		<input id="passwd" type="password" class="form-control" name="passwd"
			placeholder="Password"> <span class="input-group-addon"><i
			class="glyphicon glyphicon-lock"></i></span>
	</div>
	
	<div class="form-group">
		<button type="submit" class="btn btn-primary btn-block">Submit</button>
	</div>
</form>
 <%--  <%
    String clientId = "YOUR_CLIENT_ID";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("YOUR_CALLBACK_URL", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
 --%>
 <button class="btn btn-default btn-block" value="회원가입" onclick="location.href='signUp'">회원가입 </button>
  <div id="naver_id_login"></div>
  <script type="text/javascript">
  	var naver_id_login = new naver_id_login("<%=WebContants.NAVER_CLIENT_ID%>", "<%=WebContants.NAVER_CALLBACK_URL%>");
  	var state = naver_id_login.getUniqState();
  	naver_id_login.setButton("white", 2, 40);
  	naver_id_login.setDomain("<%=WebContants.NAVER_SERVICE_URL%>");
  	naver_id_login.setState(state);
  	naver_id_login.setPopup();
  	naver_id_login.init_naver_id_login();
  </script>
  
<!-- 본문 끝 -->
