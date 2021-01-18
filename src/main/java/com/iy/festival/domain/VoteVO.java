package com.iy.festival.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class VoteVO {

  private int vNo;
  private int userNo;
  private int fNo;
  private int sNo;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date voted_at;

}
