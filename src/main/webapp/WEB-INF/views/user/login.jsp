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
<title>로그인</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">
<%@ include file="../include/top_path.jsp" %>
</head>
<body>
  <%@ include file="../include/nav.jsp" %>

  <div class="container py-3">
    <section>
      <h1>Custom Login Page</h1>
      <h3><c:out value="${ error }"></c:out></h3>
      <h3><c:out value="${ logout }"></c:out></h3>
      
      <sec:authorize access="isAuthenticated()">
        <p>principal : <sec:authentication property="principal"/></p>
      </sec:authorize>
      
      <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
          <input type="text" name="user_id" value="admin">
        </div>
        <div>
          <input type="password" name="user_pw" value="">
        </div>
        <div>
          <input type="checkbox" name="remember-me">Remember me
        </div>
        <div>
          <button type="submit">submit</button>
        </div>
        <input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }">
      </form>
    </section>
  </div>

  <%@ include file="../include/footer.jsp" %>

  <%@ include file="../include/bottom_path.jsp" %>

  <script type="text/javascript">
  </script>
</body>
</html>