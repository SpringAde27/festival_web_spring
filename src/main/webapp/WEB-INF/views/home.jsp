<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모두의 축제</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">
<%@ include file="include/top_path.jsp" %>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <%@ include file="include/nav.jsp" %>

  <%@ include file="include/header.jsp" %>

  <%@ include file="include/footer.jsp" %>

  <%@ include file="include/bottom_path.jsp" %>

  <script>
    $(function() {
      // Script to Activate the Carousel
      $('.carousel').carousel({
        interval : 2000 //changes the speed
      });
    });
  </script>
</body>
</html>