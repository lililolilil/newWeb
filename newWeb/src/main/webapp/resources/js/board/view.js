/**
 * 
 */
var board_view = function(){

    setParameter();
    addEvent();  
    loadComment(); 

}
var setParameter = function() {
    Parameter = util.getParameter();
}
var addEvent = function() {
    var $writeBtn = $(".write_btn"); 

    $writeBtn.on("click", function(e) {
	writeArticle();
    });
    $(".list_btn").on("click", function(e) {
	if($(this).hasClass("active")){
	    $(this).removeClass("active"); 
	    $(this).html("목록보기 <i class='glyphicon glyphicon-th-list'></i>"); 
	    $(".list_article").hide(); 
	}else{
	    $(this).addClass("active"); 
	    $(this).html("목록접기 <i class='glyphicon glyphicon-th-list'></i>");
	    $(".list_article").show(); 
	}
    });
    $("#comment_form").on("submit", function(e){
	e.preventDefault(); 
	var url = contextPath+"/board/addComment";
	var form = $("#comment_form")[0]; 
	var commentInfo = {
		articleNo : Parameter.articleNo, 
		boardCd : Parameter.boardCd, 
		name : $(this).find("#name").val(), 
		passwd : $(this).find("#passwd").val(), 
		comment : $(this).find("#comment").val()
	}

	$(".add_comment_btn").prop("disable", true); 

	var request = $.ajax({
	    method: "POST",
	    url: url,
	    contentType:"application/json", 
	    data: JSON.stringify(commentInfo),
	    timeout: 60000, 
	    error : function(xhr, status, error) {
		alert(error);
		console.log("error:", error); 
		$(".add_comment_btn").prop("disable", false); 
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
		    alert("덧글을 추가 했습니다."); 
		    loadComment(); 
		}
		$(".add_comment_btn").prop("disable", false); 
	    }
	}); 
    }); 
    $("#down_btn").on("click", function(e){
	var fileName = $(this).text();
	$("#filename").val(fileName); 
	$("#downloadfile").submit(); 
    	/*formData.append("filename", fileName); 
    	util.progressBar("start"); 
    	var url = contextPath + "/board/download"; 
    	
    	var xhr = new XMLHttpRequest(); 
    	xhr.open("POST",url); 
    	xhr.send(formData); 
    	*/
    	
	/*var request = $.ajax({
	    method: "POST",
	    url: url,
	    contentType:false, 
	    processData: false, 
	    cache:false, 
	    data: formData, 
	    timeout: 600000, 
	    error : function(xhr, status, error) {
		alert(error);
		console.log("error:", error); 
		util.progressBar("stop");
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
		    alert("파일( "+fileName+") 을(를) 다운로드 했습니다. ")
		}
		util.progressBar("stop"); 
	    }
	}); */
    })
}
var loadComment = function(){
    	
    	var formData= new FormData(); 
    	formData.append("articleNo", Parameter.articleNo); 
    	util.progressBar("start"); 
    	var url = contextPath + "/board/getCommentList"; 
	var request = $.ajax({
	    method: "POST",
	    url: url,
	    contentType:false, 
	    processData: false, 
	    cache:false, 
	    data: formData, 
	    timeout: 600000, 
	    error : function(xhr, status, error) {
		alert(error);
		console.log("error:", error); 
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
		    showCommentList(data.commentList); 
		}
		util.progressBar("stop"); 
	    }
	}); 
	
}

var showCommentList = function(data){
    var $commentTable = $("#table_comment");
	tr = $("<tr></tr>"), 
	td = $("<td></td>"); 
    for(var i=0; i< data.length ; i++){
	var comment= data[i]; 
	var newComment = tr.clone().html(td.clone().html("")); 
	newComment.append(td.clone().html(comment.name)); 
	newComment.append(td.clone().html(comment.memo)); 
	newComment.append(td.clone().html(util.simpleDateFormat(new Date(comment.regdate))));  
	$commentTable.find("tbody").append(newComment); 
    }
}

function showArticle(articleNo) {
    var url = contextPath + "/board/view";
    var query = "?boardCd=" + Parameter.boardCd;
    query += "&curPage=" + Parameter.curPage; 
    query += "&articleNo=" + articleNo + "&searchWord=";
    location.href = url + query;
}
function writeArticle() {
    var url = contextPath + "/board/write";
    var query = location.search; //boardCd, curPage 
    location.href = url + query;
}
function showList(listNum) {
    var url = contextPath + "/board/list";
    if (!Parameter.searchWord) {
	Parameter.searchWord = "";
    }
    var query = "?boardCd=" + Parameter.boardCd;
    query += "&curPage=" + listNum;
    query += "&searchWord=" + Parameter.searchWord;
    location.href = url + query;
}