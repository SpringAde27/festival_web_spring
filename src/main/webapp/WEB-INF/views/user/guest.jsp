<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
<title>접근제한</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">
<%@ include file="../include/top_path.jsp" %>
</head>
<body>
  <%@ include file="../include/nav.jsp" %>

  <div class="container py-3">
    <h3>/user/guest page</h3>
    
    <!-- 로그인하지 않은 사용자 -->
    <sec:authorize access="isAnonymous()">
      <a href="${pageContext.request.contextPath}/user/login">로그인</a>
    </sec:authorize>
    
    <!-- 로그인된 사용자 -->
    <sec:authorize access="isAuthenticated()">
      <a href="${pageContext.request.contextPath}/user/logout">로그아웃</a>
    </sec:authorize>
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
  </script>
</body>
</html>