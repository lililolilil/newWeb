<%@page import="com.test.commons.WebContants"%>
<%@page import="com.test.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/newWeb">FreshTuna</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> 게시판 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/board/list?boardCd=free&curPage=1">자유게시판</a></li>
						<li><a href="${pageContext.request.contextPath}/board/list?boardCd=member&curPage=1">회원게시판</a></li>
						<li><a href="${pageContext.request.contextPath}/board/list?boardCd=qna&curPage=1">Q&A</a></li>
						<li><a href="${pageContext.request.contextPath}/board/list?boardCd=ref&curPage=1">자료실</a></li>
					</ul></li>
				<!-- <li><a href="#"> </a></li>
				<li><a href="#"></a></li> -->
			</ul>
			<%
				User loginUser = (User) session.getAttribute(WebContants.USER_KEY);
				boolean login = loginUser == null ? false : true;

				if (!login) {
			%><ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/users/signUp"><span
						class="glyphicon glyphicon-user"></span> Sign Up</a></li>
				<li><a href="${pageContext.request.contextPath}/users/login"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
			<%
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/users/editAccount"><span
						class="glyphicon glyphicon-user"></span>MyInfo</a></li>
				<li><a href="${pageContext.request.contextPath}/users/logOut"><span
						class="glyphicon glyphicon-log-in"></span> LogOut</a></li> 
			</ul>
			<%
				}
			%>


			<form class="navbar-form navbar-right">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>

		</div>
	</div>
</nav>