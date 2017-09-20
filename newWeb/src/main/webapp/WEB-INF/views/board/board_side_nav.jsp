<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script> 
 $(document).on("ready",function(){
	var menu = "${menu}"; 
	if (menu == null || menu === ""){
	    $(".side_nav").hide();
	    $(".content").removeClass("col-sm-10"); 
	}else{
	    $(".${menu}").addClass("active"); 
	}
 });
</script>
<div class="board_side_nav">
  <h2 class="side_title"> 게시판 </h2>
  <div class="list-group">
    <a href="list?boardCd=free&curPage=1" class="list-group-item free">
      <h4 class="list-group-item-heading">자유게시판 </h4>
      <!-- <p class="list-group-item-text"></p> -->
    </a>
    <a href="list?boardCd=member&curPage=1" class="list-group-item member">
      <h4 class="list-group-item-heading">회원게시판</h4>
      <p class="list-group-item-text">회원전용게시판</p>
    </a>
    <a href="list?boardCd=qna&curPage=1" class="list-group-item qna">
      <h4 class="list-group-item-heading">Q&A</h4>
      <p class="list-group-item-text">질문게시판</p>
    </a>
     <a href="list?boardCd=ref&curPage=1" class="list-group-item ref">
      <h4 class="list-group-item-heading">자료실</h4>
      <p class="list-group-item-text">회원전용 자료실</p>
    </a>
  </div>
</div>