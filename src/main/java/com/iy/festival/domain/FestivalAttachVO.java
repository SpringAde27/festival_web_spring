package com.iy.festival.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FestivalAttachVO {

  private Integer fNo;
  private String uuid;
  private String uploadPath;
  private String fileName;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdAt;

}
