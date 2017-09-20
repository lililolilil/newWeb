<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script>
 	$(document).ready(function(){
 	   $("#welcome").modal("show");  
 	});
 </script>
    
 <div class="modal fade" id="welcome" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"> 회원가입을 환영합니다. </h4>
        </div>
        <div class="modal-body">
          <p>로그인 페이지로 이동합니다~. </p>
          <p>회원가입시 입력한 email로 로그인 해 주세요.</p>
        </div>
        <div class="modal-footer">
          <button type="button" onclick="javascript:location.href='login'" class="btn btn-primary" data-dismiss="modal">확인</button>
        </div>
      </div>
    </div>
  </div>
  