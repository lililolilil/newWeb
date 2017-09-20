<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	
    $(document).ready(function() {
		addEvent(); 
    });
    var addEvent = function() {
		$("#changeForm").on("submit", function(e) {
			e.preventDefault(); 

		    if(validation()){
			
			    var url = "${pageContext.request.contextPath}/users/changePasswd";
			    //var form_data = $("#signUpForm").serialize(); 
			    var newPass = {
				    currentPasswd : $("#currentPasswd").val(),
					newPasswd : $("#newPasswd").val()
			    };
			    var request = $.ajax({
					method : "POST",
					url : url,
					contentType : "application/json",
					//messagefileDir = C:/Users/suyeon/git/mcare-catholic-daegu/WebContent/WEB-INF/messages
					data : JSON.stringify(newPass),
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
							alert("비밀번호를 변경 했습니다. 재로그인 해 주세요."); 
							location.href="login"; 
					    }
					   
					}
			    }); 
		    }	    	
		}); 
    }
    function validation(){
	   if($("#newPasswd").val() === $("#confirm").val()){
	       return true;
	   }else{
	       alert("새로운 패스워드를 잘못 입력하셨습니다. 다시 입력해 주세요."); 
	       return false;
	   }
	   
	   
	}
</script>
<div class="jumbotron">
	<h1>비밀번호 변경</h1>
	<p>
		비밀번호를 변경 할 수 있습니다. </p>
		
</div>

<form id="changeForm" class="form-horizontal">
	<label for="passwd" class="control-label col-sm-2">현재 비밀번호:</label>
	<div class="input-group">
		<input id="currentPasswd" type="password" class="form-control"
			name="currentPasswd" placeholder="현재 비밀번호를 입력하세요."> <span
			class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
	</div>
		<label for="newPasswd" class="control-label col-sm-2">새로운 비밀번호 :</label>
	<div class="input-group">
		<input id="newPasswd" type="password" class="form-control"
			name="newPasswd" placeholder="새로운 비밀번호를 입력하세요."> <span
			class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
	</div>
		<label for="confirm" class="control-label col-sm-2">새로운 비밀번호 확인:</label>
	<div class="input-group">
		<input id="confirm" type="password" class="form-control"
			name="confirm" placeholder="새로운 비밀번호 확인"> <span
			class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
	</div>
	<div class="form-group" class="col-sm-10">
		<button type="submit" class="btn btn-primary btn-block">Submit</button>
	</div>
</form>
