package com.iy.festival.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class SecurityUser extends User {

  private static final long serialVersionUID = 1L;
  
  private MemberVO member;
  
  public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
  
  public SecurityUser(MemberVO vo) {
    super(vo.getUser_id(),
          vo.getUser_pw(),
          vo.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
    
    this.member = vo;
  }

}
