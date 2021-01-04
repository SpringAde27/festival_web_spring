package com.iy.festival;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class MyBatisTest {

  @Autowired
  private SqlSessionFactory sqlFactory;

  @Test
  public void testMyBatisTest() {
    System.out.println("SessionFactory Test >> " + sqlFactory);
  }

  @Test
  public void testSession() {
    try (SqlSession session = sqlFactory.openSession()) {
      System.out.println("Session Test >> " + session);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}