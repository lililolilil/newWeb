<%@page import="com.test.commons.WebContants"%>
<%@page import="com.test.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
	boolean login = loginUser == null ? false : true;
	String name = "" ; 
	String mobile = ""; 
	if (login) { //login한 상태일때 
		name = loginUser.getName();
		mobile = loginUser.getMobile();
	}
%>
<script>
    $(document).ready(function() {
		addEvent(); 
    });
    var addEvent = function() {
		$("#editForm").on("submit", function(e) {
	    	e.preventDefault(); 
		    var url = "${pageContext.request.contextPath}/users/editAccount";
		    //var form_data = $("#signUpForm").serialize(); 
		    var userInfo = {
			name : $("#name").val(),
			passwd : $("#passwd").val(),
			mobile : $("#mobile").val()
		    };
		    var request = $.ajax({
				method : "POST",
				url : url,
				contentType : "application/json",
				//messagefileDir = C:/Users/suyeon/git/mcare-catholic-daegu/WebContent/WEB-INF/messages
				data : JSON.stringify(userInfo),
				error : function(xhr, status, error) {
				    alert(error);
				},
				success : function(data) {
				    if(data.err != null){
						if(data.err === "notLogin"){
						    alert("로그인 후 다시 시도해 주세요."); 
						    location.href='login'; 
						}else{
							alert(data.err); 
						}
				    }else{
						alert("정보를 변경 했습니다. "); 
						location.reload(); 
				    }
				   
				}
		    }); 
		}); 
    }
</script>
<div class="jumbotron">
	<h1>내 정보 수정</h1>
	<p>
		비밀번호외의 자신의 계정 정보를 수정할 수 있습니다.<br /> 비밀번호는 <a href="changePasswd">비밀번호
			변경</a>메뉴를 이용하세요.<br />
</div>

<form id="editForm" class="form-horizontal">
	<label for="name" class="control-label col-sm-2">이름:</label>
	<div class="input-group">
		<input id="name" type="text" class="form-control" name="name"
			placeholder="Name" value="<%=name%>"> <span class="input-group-addon"><i
			class="glyphicon glyphicon-user"></i></span>
	</div>
	<label for="mobile" class="control-label col-sm-2">mobile:</label>
	<div class="input-group">
		<input id="mobile" type="number" class="form-control" name="mobile"
			placeholder="mobile" value="<%=mobile%>"> <span class="input-group-addon"><i
			class="glyphicon glyphicon-phone"></i></span>
	</div>
	<label for="passwd" class="control-label col-sm-2">현재 비밀번호:</label>
	<div class="input-group">
		<input id="passwd" type="password" class="form-control"
			name="passwd" placeholder="confirm Password"> <span
			class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
	</div>
	<div class="form-group" class="col-sm-10">
		<button type="submit" class="btn btn-default btn-block">Submit</button>
	</div>
</form>