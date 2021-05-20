package com.iy.festival.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
    log.warn("Login Success");
    
    List<String> roleName = new ArrayList<>();
    // Authentication객체를 이용해서 로그인한 사용자의 권한을 문자열로 체크
    auth.getAuthorities().forEach(authority -> {
      roleName.add(authority.getAuthority());
    });
    
    log.warn("ROLE NAMES >> " + roleName);
    
    if(roleName.contains("ROLE_ADMIN")) {
      response.sendRedirect(request.getContextPath() + "/user/admin");
      return;
    }
    
    if(roleName.contains("ROLE_MEMBER")) {
      response.sendRedirect(request.getContextPath() + "/user/member");
      return;
    }
    
    response.sendRedirect("/");
  }

}
