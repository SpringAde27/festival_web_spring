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
    <h3>/user/admin page</h3>
    
    <a href="${pageContext.request.contextPath}/user/logout">Logout</a>
    
    <!-- principal : UserDetailsService에서 반환된 객체 -->
    <p>principal : <sec:authentication property="principal"/></p>
    <p>MemberVO : <sec:authentication property="principal.member"/></p>
    <p>UserID : <sec:authentication property="principal.member.user_id"/></p>
    <p>AuthList : <sec:authentication property="principal.member.authList"/></p>
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
  </script>
</body>
</html>