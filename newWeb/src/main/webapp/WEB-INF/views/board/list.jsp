<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>

.write {
	margin-bottom: 1em;
}
</style>
<script>
    $(document).on("ready", function() {
	setParameter();
	$(".write").on("click", function() {
	    writeArticle();
	});
    });//ready
    var setParameter = function() {
	Parameter = util.getParameter();
    }

    function showArticle(articleNo) {
	var url = contextPath + "/board/view";
	var query = location.search; //boardCd, curPage 
	query += "&articleNo=" + articleNo + "&searchWord=";
	location.href = url + query;
    }
    function writeArticle() {
	var url = contextPath + "/board/write";
	var query = location.search; //boardCd, curPage 
	location.href = url + query;
    }
    function showList(listNum){
	var url = contextPath + "/board/list";
	if(!Parameter.searchWord){
	    Parameter.searchWord = ""; 
	}
	var query = "?boardCd="+Parameter.boardCd;  
		query += "&curPage="+listNum; 
		query += "&searchWord="+ Parameter.searchWord; 
	location.href=url+query; 
    }
</script>
<div class="list_title">
	<h1 class="text-center">${boardNm_ko}</h1>
</div>
<div id="${boardNm}">
	<div class="btnArea">
		<button type="button" class="btn btn-primary pull-right write">
			<span>글쓰기 </span><i class="glyphicon glyphicon-pencil"></i>
		</button>
	</div>
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
							<td style="text-align: center;">${listItemNo - status.index }</td>
							<td><a href="javascript:showArticle('${article.articleNo}')">${article.title}</a>
								<c:if test="${article.attachFileNum > 0 }">
									<img src="<c:url value="/resources/images/attach.png" />"
										alt="첨부파일" />
								</c:if> <c:if test="${article.commentNum > 0 }">
									<span class="badge">comment:${article.commentNum }</span>
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