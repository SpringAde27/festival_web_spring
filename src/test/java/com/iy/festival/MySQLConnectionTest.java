package com.iy.festival;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MySQLConnectionTest {

  private static final String DRIVER = "com.mysql.jdbc.Driver";
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/festival";
  private static final String ID = "root";
  private static final String PW = "rootroot";

  @Test
  public void testMySQLConnectionTest() throws ClassNotFoundException {
    Class.forName(DRIVER);

    try (Connection conn = DriverManager.getConnection(URL, ID, PW)) {
      System.out.println("MySQL-Conncetion 객체 >> " + conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}