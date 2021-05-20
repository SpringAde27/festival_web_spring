package com.iy.festival.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.iy.festival.domain.AuthVO;
import com.iy.festival.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
  "file:src/main/webapp/WEB-INF/spring/root-context.xml",
  "file:src/main/webapp/WEB-INF/spring/security-context.xml",
})
@Log4j
public class MemberMapperTest {

  @Autowired
  BCryptPasswordEncoder pwEncoder;

  @Setter(onMethod_ = @Autowired)
  private MemberMapper mapper;
  
//  @Test
  public void testInsertMember() {
    MemberVO vo = new MemberVO();
    vo.setUser_id("member02");
    vo.setName("2번 유저");
    String encodedPassword = pwEncoder.encode("1234");
    vo.setUser_pw(encodedPassword);
    mapper.insertMember(vo);
  }
  
//  @Test
  public void testInsertAuth() {
    AuthVO vo = new AuthVO();
    vo.setUser_id("member02");
    vo.setAuth("ROLE_MEMBER");
    mapper.insertAuth(vo);
  }
  
  @Test
  public void testReadMember() {
    MemberVO vo = mapper.selectMember("member02");
    log.info(vo);
    vo.getAuthList().forEach(authVO -> log.info(authVO));
  }

}
