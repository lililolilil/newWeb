<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<button class="btn btn-default btn-block" value="회원가입" onclick="location.href='signUp'">회원가입 </button>
<!-- 본문 끝 -->
