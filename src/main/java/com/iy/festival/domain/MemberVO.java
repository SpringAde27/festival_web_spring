package com.iy.festival.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

  private int user_no;
  private String user_id;
  private String user_pw;
  private Boolean enabled;
  private String name;
  private String email;
  private String phone;
  private Boolean gender;
  private Integer postcode;
  private String addr1;
  private String addr2;
  private String addr3;
  private Date created_at;
  private Date updated_at;
  
  private List<AuthVO> authList;

}