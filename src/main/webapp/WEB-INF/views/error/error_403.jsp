<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모두의 축제</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">
<%@ include file="../include/top_path.jsp" %>
<link href="${pageContext.request.contextPath}/resources/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div id="wrapper">
    <section class="container vertical-align">
      <div>
        <img src="${pageContext.request.contextPath}/resources/images/icons/robot-01.png" style="height: 80px">
      </div>
      <div class="mt-3 mb-4">
        <small>Forbidden</small>
        <h1>403</h1>
        <h5>접근이 거부되었습니다.</h5>
      </div>
      <div>
        <button type="button" class="btn btn-outline-secondary border-pill w-15" onclick="self.location='${pageContext.request.contextPath}'" style="width: 15rem;">
          Back To Home
        </button>
      </div>
    </section>
  </div>

  <%@ include file="../include/bottom_path.jsp" %>
</body>
</html>