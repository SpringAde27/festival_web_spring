package com.iy.festival.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.iy.festival.domain.MemberVO;
import com.iy.festival.domain.SecurityUser;
import com.iy.festival.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {

  @Setter(onMethod_ = { @Autowired } )
  private MemberMapper memberMapper;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 내부적으로 MemberMapper를 이용해서 MemberVO를 조회하고,
    // 인스턴스를 얻으면  CustomUser 타입의 객체로 변환해서 반환한다.
    log.warn("loadUserByUsername() >> " + username);
    
    // username means user_id
    MemberVO vo = memberMapper.selectMember(username);
    
    log.warn("queried by member mapper >> " + vo);
    
    return vo == null ? null : new SecurityUser(vo);
  }

}
