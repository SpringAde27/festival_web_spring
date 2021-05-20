package com.iy.festival.mapper;

import com.iy.festival.domain.AuthVO;
import com.iy.festival.domain.MemberVO;

public interface MemberMapper {

  public MemberVO selectMember(String user_id);
  
  public void insertMember(MemberVO vo);
  
  public void insertAuth(AuthVO vo);

}