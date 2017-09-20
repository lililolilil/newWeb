<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript"
	src="<c:url value='/resources/js/board/view.js'/>"></script>

<style>
textarea.form_comment {
	height: 7em;
}

div.content_area {
	background-color: white;
	border: 1px solid #e3e3e3;
	border-radius: 4px;
	margin: 1em 0;
	min-height: 100px;
	padding: 19px;
}

.add_comment_btn {
	height: 120px;
}
</style>

<script type="text/javascript">
    $(document).on("ready", function() {
		board_view();
    });//ready
</script>

<div class="list_article" style="display: none;">
	<table class="table table-striped table-hover table-responsive">
		<colgroup>
			<col width='10%' />
			<col width='60%' />
			<col width='20%' />
			<col width='10%' />
		</colgroup>
		<thead>
			<tr>
				<th>NO</th>
				<th>TITLE</th>
				<th>DATE</th>
				<th>HIT</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${listItemNo!=0}">
					<c:forEach var="article" items="${list}" varStatus="status">
						<tr>
							<td style="text-align: center;">
							<c:if test="${param.articleNo == article.articleNo }">
									<img src="<c:url value="/resources/images/arrow.gif"/>"
										alt="현재위치" />
								</c:if> ${listItemNo - status.index }</td>
							<td><a href="javascript:showArticle('${article.articleNo}')">${article.title}&nbsp;</a>
								<c:if test="${article.attachFileNum > 0 }">
									<img src="<c:url value="/resources/images/attach.png" />"
										alt="첨부파일" />
								</c:if> <c:if test="${article.commentNum > 0 }">
									<span class="badge">&nbsp;${article.commentNum }</span>
								</c:if></td>
							<td style="text-align: center;"><fmt:formatDate
									pattern="yyyy.MM.dd" value="${article.regdate }" /></td>
							<td style="text-align: center;">${article.hit }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4"
							style="height: 200px; vertical-align: middle; text-align: center;">
							조회 된 글이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<ul class="pagination clearfix ">
		<c:if test="${prevPage > 0}">
			<a href="javascript:showList('${prevPage}')">이전</a>
		</c:if>
		<c:forEach var="i" begin="${firstPage}" end="${lastPage }">

			<c:choose>
				<c:when test="${param.curPage == i }">
					<li><a class="active" href="#">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:showList('${i}')">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${nextPage>0}">
			<a href="javascript:showList('${nextPage}')">다음</a>
		</c:if>
	</ul>
</div>
<!--  목록 (목록보기 버튼 클릭시 표시)  -->
<!--  버튼 영역/목록보기/이전글/다음글 /새글 쓰기/ -->
<div class="btn_area clearfix">
	<button type="button" class="btn btn-default pull-left list_btn">
		<span>목록보기 </span><i class="glyphicon glyphicon-th-list"></i>
	</button>
	<button type="button" class="btn btn-primary pull-right write_btn">
		<span>글쓰기 </span><i class="glyphicon glyphicon-pencil"></i>
	</button>
</div>
<!--  본문  -->
<div class="content_area">
	<form id="article_form" class="form-horizontal">
		<div class="form-group">
			<label class="control-label col-sm-2" for="title">제목:</label>
			<div class="col-sm-10">
				<p id="title" class="form-control-static">${title}&nbsp;&nbsp;<span						class="badge">${hit}</span>
				</p>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="writer">작성자:</label>
			<div class="col-sm-10">
				<p id="writer" class="form-control-static">
					${email} (edited:
					<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${regdate}" />
					)
				</p>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="content">내용: </label>
			<div class="col-sm-10">
				<div id="content" class="form-control-static">${content}</div>
			</div>
		</div>
		<!-- file 자유 게시판에는 없음. -->
		<c:if test="${boardCd != 'free'}">
			<div class="form-group" style="height: 100px;">
				<label class="control-label col-sm-2" for="file">첨부파일:</label>
				<div class="col-sm-10">
					<p id="file" class="form-control-static">
						<c:forEach var="file" items="${attachFileList }"
							varStatus="status">
							<a id="down_btn" href="#">${file.filename}</a>
							<%-- 	<a href="${uploadPath }${file.filename }">${file.filename }</a> --%>
							<c:if test="${user.email == file.email }">
								<a href="javascript:deleteAttachFile('${file.attachFileNo }')">x</a>
							</c:if>
							<br />
						</c:forEach>
					</p>
				</div>
			</div>
		</c:if>
	</form>
</div>

<form id="downloadfile" action="download" method="POST">
	<input type="hidden" name="filename" id="filename" />
</form>
<!-- 본문 끝  -->
<!--  이전 다음글  -->
<div class="prev_next">
	<ul class="pager">
		<c:if test="${prevArticle != null}">
			<li><a href="javascript:showArticle('${prevArticle.articleNo}')"
				data-toggle="tooltip" title="이전글 : ${prevArticle.title}">Previous</a></li>
		</c:if>
		<c:if test="${nextArticle != null}">
			<li><a href="javascript:showArticle('${nextArticle.articleNo}')"
				data-toggle="tooltip" title="다음글 : ${nextArticle.title}">Next</a></li>
		</c:if>
	</ul>
</div>
<hr />
<!-- 댓글 영역 -->
<table id="table_comment"
	class="table table-striped table-condense table-responsive">
	<caption>comment this article</caption>
	<colgroup>
		<col width='5%' />
		<col width='20%' />
		<col width='55%' />
		<col width='30%' />
	</colgroup>
	<thead>
		<tr>
			<th>-</th>
			<th>이름</th>
			<th>댓글</th>
			<th>등록일</th>
		</tr>
	</thead>
	<tbody>
		<tr>
		</tr>
	</tbody>
</table>
<hr />
<!--  댓글 form -->
<div class="comment_area clearfix">
	<form id="comment_form">
		<div class="form-group col-sm-2">

			<label class="control-label" for="name">이름:</label> <input
				type="text" id="name" name="name"
				class="form_comment_name form-control"/> <label
				class="control-label" for="passwd">비밀번호 :</label> <input type="text"
				id="passwd" name="passwd" class="form_comment_passwd form-control"/>

		</div>
		<div class="form-group col-sm-8">

			<label class="control-label" for="name">댓글:</label>
			<textarea class="form_comment form-control" id="comment"
				name="comment"></textarea>

		</div>
		<div class="col-sm-2 clearfix">
			<button type="submit" class="btn btn-info btn-block add_comment_btn">
				댓글<br>남기기
			</button>
		</div>
	</form>
</div>