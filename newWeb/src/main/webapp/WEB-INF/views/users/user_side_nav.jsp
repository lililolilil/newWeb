<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script> 
 $(document).on("ready",function(){
	var menu = "${menu}"; 
	if (menu == null || menu === ""){
	    $(".side_nav").hide();
	    $(".content").removeClass("col-sm-10"); 
	    $(".container").css("width","70%"); 
	}else{
	    $(".${menu}").addClass("active"); 
	}
 });
</script>
<div class="user_side_nav">
  <h2> 마이페이지 </h2>
  <div class="list-group">
    <a href="editAccount" class="list-group-item editAccount">
      <h4 class="list-group-item-heading"> 개인정보 변경 </h4>
      <p class="list-group-item-text"> 비밀번호를 제외한 개인정보를 변경 할 수 있습니다. </p>
    </a>
    <a href="changePasswd" class="list-group-item changePasswd">
      <h4 class="list-group-item-heading">비밀번호 변경 </h4>
      <p class="list-group-item-text"> 비밀번호를 변경 할 수 있습니다.</p>
    </a>
    <a href="bye" class="list-group-item withdrawal">
      <h4 class="list-group-item-heading">회원 탈퇴</h4>
      <p class="list-group-item-text">멤버십을 탈퇴할 수 있습니다.</p>
    </a>
  </div>
</div>