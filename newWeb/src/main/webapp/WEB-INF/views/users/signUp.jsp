<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
    //<![CDATA[ 
    $(document).ready(function(){
        $("#signUpForm").on("submit", function(e){
    		e.preventDefault(); 
    		var url = "${pageContext.request.contextPath}/users/signUp"; 
    		//var form_data = $("#signUpForm").serialize(); 
    		var userInfo = {
    			name : $("#name").val(),
    			email : $("#email").val(),
    			passwd : $("#passwd").val(), 
    			mobile : $("#mobile").val()
    		};  
    		var request = $.ajax({
	    		method: "POST",
			    url: url,
			    contentType:"application/json", 
			    //messagefileDir = C:/Users/suyeon/git/mcare-catholic-daegu/WebContent/WEB-INF/messages
			    data: JSON.stringify(userInfo),
			    error: function(xhr, status, error){
					alert(error); 
			    },
			    success: function(data){
					if(data.msg != null || data.msg != undefined){
					    alert(data.msg); 
					}else{
					    $("#welcome").modal("show");
					}
			    }
    		})
    		
        }); 
        
    }); 
    function check() {
	//var form = document.getElementById("signUpForm");
	//TODO 유효성 검사 
	return true;
    }

    //]]>
</script>
<body>
	<div class="jumbotron">
		<h3>회원 가입</h3>
	</div>
	<form id="signUpForm" class="form-horizontal">
		<label for="name" class="control-label col-sm-2">이름:</label>
		<div class="input-group">
			<input id="name" type="text" class="form-control" name="name"
				placeholder="Name"> <span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span>
		</div>
		<label for="passwd" class="control-label col-sm-2">비밀번호:</label>
		<div class="input-group">
			<input id="passwd" type="password" class="form-control" name="passwd"
				placeholder="Password"> <span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span>
		</div>
		<label for="pwd" class="control-label col-sm-2">비밀번호 확인:</label>
		<div class="input-group">
			<input id="confirm" type="password" class="form-control"
				name="confirm" placeholder="confirm Password"> <span
				class="input-group-addon"><i class="glyphicon glyphicon-ok"></i></span>
		</div>
		<label for="email" class="control-label col-sm-2">mail:</label>
		<div class="input-group">
			<input id="email" type="text" class="form-control" name="email"
				placeholder="Email"> <span class="input-group-addon"><i
				class="glyphicon glyphicon-envelope"></i></span>
		</div>
		<label for="mobile" class="control-label col-sm-2">mobile:</label>
		<div class="input-group">
			<input id="mobile" type="number" class="form-control" name="mobile"
				placeholder="mobile"> <span class="input-group-addon"><i
				class="glyphicon glyphicon-phone"></i></span>
		</div>
		<div class="form-group" class="col-sm-10">
				<button type="submit" class="btn btn-primary btn-block">Submit</button>
		</div>
	</form>
	    
 <div class="modal fade" id="welcome" role="dialog">
    <div class="modal-dialog modal-lg" style="top: 40%;">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"> 회원가입을 환영합니다. </h4>
        </div>
        <div class="modal-body">
          <p>로그인 페이지로 이동합니다~. </p>
          <p>회원가입시 입력한 email로 로그인 해 주세요.</p>
        </div>
        <div class="modal-footer">
          <button type="button" onclick="javascript:location.href='login'" class="btn btn-primary btn-block" data-dismiss="modal">확인</button>
        </div>
      </div>
    </div>
  </div>
  
</body>
</html>