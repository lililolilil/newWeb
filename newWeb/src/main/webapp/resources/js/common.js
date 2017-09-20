"use strict"; 

    var util = {
	   /**
	    * 파라미터를 변환해줌. 
	    */
	getParameter :  function(){
		try{
			var paramObj = new Object();
			
			var query = window.location.search.substring(1),
				vars = query.split("&");
			
			for( var i=0; i < vars.length; i++ ){
			     var pair = vars[i].split("=");

			     paramObj[ pair[0] ] = pair[1];
			}
			
			return paramObj;
		} catch(e) {
			self.log(e, "mcare_util_getParameter" );
		}
	}, 
	/**
	 * 메시지를 변수를 이용해ㅑ서 출력 
	 */
	msgFormatter: function(){
            var msg = arguments[0]; 
            for( var i=1; i <arguments.length; i++){
                msg = msg.replace(new RegExp('\\{'+(i-1)+'\\}','gi'), arguments[i]);
            }
            return msg; 

        },
	/**
	 * 날짜 형식 YYYY-MM-DD 텍스트로 포맷을 바꿔주는 유틸
	 * @param date {date}
	 * @return text { string } - ex)2015-08-21
	 */
	simpleDateFormat : function( date ){
		try{
			var yyyy = date.getFullYear().toString(),
				mm = (date.getMonth()+1).toString(),
				dd  = date.getDate().toString(),
				text =  yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]); 
			
			return text;
		} catch(e) {
			self.log(e, "util.simpleDateFormat" );
		}
	},
	/**
	 * 날짜 형식 YYYY-MM-DD HH:MI 텍스트로 포맷을 바꿔주는 유틸
	 * @param date {date}
	 * @return text { string } - ex)2015-08-21 09:00
	 */
	simpleDateTimeFormat : function( date ){
		try{
			var yyyy = date.getFullYear().toString(),
			mm = (date.getMonth()+1).toString(),
			dd  = date.getDate().toString(),
			hh = date.getHours(),
			mi = date.getMinutes(),
			text =  yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]) + " " + (hh<10?"0"+hh:hh)+":"+(mi<10?"0"+mi:mi); 
		
			return text;
		} catch(e) {
			self.log(e, "util.simpleDateTimeFormat" );
		}
	},
	progressBar : function(order){
	    var $mybar = $("#myBar"); 
	    var everyms, after5, after10; 
	    if(order === "start"){
			$("#progressbar").modal("show"); 
			var width = 1;  
			$mybar.removeClass("progress-bar-danger").removeClass("progress-bar-warning").addClass("progress-bar-success");
			everyms = setInterval(frame, 10); 
		    after5 = setTimeout(warning, 1000*5);// 5초 이후에 warning으로 바꾸자... 
		    after10 = setTimeout(danger, 1000*10);
		    
	    }else{
			$("#progressbar").modal("hide"); 
			clearInterval(everyms);
			clearInterval(after5);
			clearInterval(after10);
	    }
	    
	    function frame(){
			if(width >= 100){
			    clearInterval(everyms); 
			}
			++width; 
		 	$mybar.width(width+"%");
		}
		function warning(){
			$mybar.removeClass("progress-bar-success").addClass("progress-bar-warning"); 
		}
		function danger(){
			$mybar.removeClass("progress-bar-warning").addClass("progress-bar-danger"); 
		}
	},
	getConfirm : function(confirmTitle, confirmMessage, callback){
	    var confirmMessage = confirmMessage || ''; 
	    var confirmTitle = confirmTitle || ''; 
	    
	    $("#confirmBox").modal({
			show: true, 
			backdrop: false, 
			keyboard: false
	    }); 
	    $("#confirmBody").html(confirmMessage);  
	    $("#confirmTitle").html(confirmTitle); 
	    $("#confirmNo").click(function(){
			$("#confirmBox").modal("hide");  
			if(callback) callback(false); 
	    }); 
	    $("#confirmYes").click(function(){
			$("#confirmBox").modal("hide"); 
			if(callback) callback(true);  
	    });
	}
	

    }
