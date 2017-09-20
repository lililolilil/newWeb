

var init = function(){
    loadRecentArticle(); 
    addEvent(); 
}

var loadRecentArticle = function(){

    var formData= new FormData(); 
    //formData.append("articleNo", Parameter.articleNo); 
    util.progressBar("start"); 
    var url = contextPath + "/board/getRecentList"; 
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
		displayHomeArticle(data.articleList); 
	    }
	    util.progressBar("stop"); 
	}
    }); 
}

var displayHomeArticle = function(data){
    var div = $("<div></div>"), 
    a 	= $("<a></a>"), 
    span = $("<span></span>"),
    p = $("<p></p>"); 
    var $homeCon = $(".home_container"); 
    for(var i = 0 ; i<9 ; i++){
	var item = data[i]; 
	var article = div.clone().addClass("article").html(p.clone().html(item.title).addClass("tit").append(span.clone().html(item.hit).addClass("hit"))); 
	article.append(p.clone().html(item.content).addClass("con")); 
	article.append(p.clone().addClass("date").html(util.simpleDateTimeFormat(new Date(item.regdate)))); 
	
	$homeCon.append(div.clone().addClass("home_article").attr("data-index",Math.floor(i/3)).html(article)); 
    }
}
var addEvent = function(){
    $(document).on("mouseover", ".home_article", function(e){
	var $this = $(this); 
	var index = $this.data("index"); 
	$(".home_article[data-index='"+index+"'").not($this).addClass("out");
	$this.addClass("in"); 
    }); 
    $(document).on("mouseout", ".home_article", function(e){
	$(".home_article").removeClass("in out");
    }); 
}