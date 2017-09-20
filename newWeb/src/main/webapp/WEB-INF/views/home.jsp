<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.test.commons.WebContants"%>
<%@ page session="false"%>
<head>
<title>Home</title>
<script src="<c:url value="/resources/js/home.js"/>"></script>
<script>
    $(document).on("ready", function(e) {
		init();
    }); 
</script>
</head>
<body>
	<div class="home_section">
		<nav class="col-sm-2">
		
		</nav>
		<section class="col-sm-10">
		<div class="home_container"></div>
		</section>
	</div>
</body>

