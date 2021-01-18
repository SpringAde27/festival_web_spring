<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="sticky-top">
  <nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <div class="container">
      <a class="navbar-brand" href="${pageContext.request.contextPath}">모두의 축제</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse flex-row-reverse" id="collapsibleNavbar">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/notice/list">공지사항</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/festival/list">축제</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/gallery/list">갤러리</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/market/list">상점</a></li>
          <li class="nav-item"><a class="nav-link" href="#">로그인</a></li>
          <li class="nav-item"><a class="nav-link" href="#">회원가입</a></li>
        </ul>
      </div>
    </div>
  </nav>
</div>
