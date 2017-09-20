<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-3.2.1.js"
	integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
	crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
var aaa = false || '기본값'; //앞에가 false면 뒤에 기본값 스트링이 들어간다.
var bbb = true || '얘는 무시되는 값'; //이런 경우에는 항상 true가 들어간다.
var ccc = "" || "빈 값을 입력하셨네요 ㅠㅠ"; //이런식으로 응용도 가능하다.


$(document).ready(function(){
    alert(aaa+"\n" + bbb+"\n"+ccc); 
}); 

</script>
</head>
<body>

</body>
</html>