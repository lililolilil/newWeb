<%@page import="com.test.commons.WebContants"%>
<%@page import="com.test.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
table>thead>tr>th {
	height: 3em;
	vertical-align: middle;
	text-align: center;
}

table>tbody>tr>td {
	vertical-align: middle;
	text-align: center;
}

.write {
	margin-bottom: 1em;
}
</style>
<script>
$(document).ready(function() {
	setParameter(); 
	addEvent(); 

});
var setParameter = function(){
 	Parameter = util.getParameter();   
}
var addEvent = function() {
    $("#writeForm").on("submit", function(e) {
	e.preventDefault(); 
		var boardCd = "${param.boardCd}"; 
	    var url = "${pageContext.request.contextPath}/board/write";
	    var form = $("#writeForm")[0]; 
	    var data = new FormData(form); 
	    data.append("customField","this is some extra data for testing"); 
	    $("#btnSubmit").prop("disable", true); 
	    
	    var request = $.ajax({
			method : "POST",
			enctype: "multipart/form-data",
			url : url,
			//messagefileDir = C:/Users/suyeon/git/mcare-catholic-daegu/WebContent/WEB-INF/messages
			data : data, 
			processData: false, 
			contentType: false, 
			cache: false, 
			timeout: 600000, 
			error : function(xhr, status, error) {
			    alert(error);
			    console.log("error:", e); 
			    $("#btnSubmit").prop("disable", true); 
			},
			success : function(data) {
			    if(data.err != null){
					if(data.err === "notLogin"){
					    alert("로그인 후 다시 시도해 주세요."); 
					    location.href=contextPath+'/users/login'; 
					}else{
						alert(data.err); 
					}
			    }else{
					alert("게시물을 저장했습니다."); 
					location.href = contextPath+"/board/list?boardCd="+Parameter["boardCd"]+"&curPage=1"; 
			    }
				$("#btnSubmit").prop("disable", true); 
			}
	    }); 
	}); 
}
</script>
<div class="jumbotron">
	<h1 class="text-center">${boardNm}</h1>
</div>

<div class="btnArea"></div>
<div class="formContainer" style="clear:both;">
	<form id="writeForm">
		<input id="boardCd" name="boardCd" type="hidden"
			value="${boardCd}" /> <label for="title" class="control-label">Title:</label>
		<div class="input-group" style="width: 100%;">
			<input id="title" type="text" class="form-control" name="title"
				placeholder="제목" />
		</div>
		<c:choose>
			<c:when test="${boardCd eq'free'}">
				<%
					User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
							boolean login = loginUser == null ? false : true;

							if (!login) {
				%>
				<label for="email" class="control-label">email:</label>
				<div class="input-group" style="width: 100%;">
					<input id="email" type="text" class="form-control" name="email"
						placeholder="abc123@gmail.com" />
				</div>
				<%
					}
				%>
			</c:when>
			<c:otherwise>
				<label for="file" class="control-label">file to attach:</label>
				<div class="input-group" style="width: 100%;">
					<input id="file" type="file" class="form-control" name="file"
						placeholder="파일명" />
				</div>
			</c:otherwise>
		</c:choose>
		<label for="content">Content:</label>
		<textarea class="form-control" rows="10" id="content" name="content"></textarea>
		<br>
		<button id="btnSubmit" type="submit" class="btn btn-primary btn-block">
			글쓰기 <i class="glyphicon glyphicon-upload"></i>
		</button>
	</form>


</div>
