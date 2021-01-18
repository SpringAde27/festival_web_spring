package com.iy.festival.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class FestivalVO {

  private int fNo;
  private String fTitle;
  private String fTheme;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fStartdate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fEnddate;
  private String fHost;
  private String fPhone;
  private Integer fPostcode; // null 필요 시, Integer
  private String fAddr1;
  private String fAddr2;
  private String fAddr3;
  private Double fLatitude;
  private Double fLongitude;
  private String fContent;
  private String fImg; // 대표 이미지
  private MultipartFile attachImg;
  private int fLike;
  private String isGood;
  private int rank;

  private List<FestivalAttachVO> attachList; // FestivalAttachVO 첨부파일 처리

  public int getfNo() {
    return fNo;
  }

  public void setfNo(int fNo) {
    this.fNo = fNo;
  }

  public String getfTitle() {
    return fTitle;
  }

  public void setfTitle(String fTitle) {
    this.fTitle = fTitle;
  }

  public String getfTheme() {
    return fTheme;
  }

  public void setfTheme(String fTheme) {
    this.fTheme = fTheme;
  }

  public Date getfStartdate() {
    return fStartdate;
  }

  public void setfStartdate(Date fStartdate) {
    this.fStartdate = fStartdate;
  }

  public Date getfEnddate() {
    return fEnddate;
  }

  public void setfEnddate(Date fEnddate) {
    this.fEnddate = fEnddate;
  }

  public String getfHost() {
    return fHost;
  }

  public void setfHost(String fHost) {
    this.fHost = fHost;
  }

  public String getfPhone() {
    return fPhone;
  }

  public void setfPhone(String fPhone) {
    this.fPhone = fPhone;
  }

  public Integer getfPostcode() {
    return fPostcode;
  }

  public void setfPostcode(Integer fPostcode) {
    this.fPostcode = fPostcode;
  }

  public String getfAddr1() {
    return fAddr1;
  }

  public void setfAddr1(String fAddr1) {
    this.fAddr1 = fAddr1;
  }

  public String getfAddr2() {
    return fAddr2;
  }

  public void setfAddr2(String fAddr2) {
    this.fAddr2 = fAddr2;
  }

  public String getfAddr3() {
    return fAddr3;
  }

  public void setfAddr3(String fAddr3) {
    this.fAddr3 = fAddr3;
  }

  public Double getfLatitude() {
    return fLatitude;
  }

  public void setfLatitude(Double fLatitude) {
    this.fLatitude = fLatitude;
  }

  public Double getfLongitude() {
    return fLongitude;
  }

  public void setfLongitude(Double fLongitude) {
    this.fLongitude = fLongitude;
  }

  public String getfContent() {
    return fContent;
  }

  public void setfContent(String fContent) {
    this.fContent = fContent;
  }

  public String getfImg() {
    return fImg;
  }

  public void setfImg(String fImg) {
    this.fImg = fImg;
  }

  public MultipartFile getAttachImg() {
    return attachImg;
  }

  public void setAttachImg(MultipartFile attachImg) {
    this.attachImg = attachImg;
  }

  public int getfLike() {
    return fLike;
  }

  public void setfLike(int fLike) {
    this.fLike = fLike;
  }

  public String getIsGood() {
    return isGood;
  }

  public void setIsGood(String isGood) {
    this.isGood = isGood;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public List<FestivalAttachVO> getAttachList() {
    return attachList;
  }

  public void setAttachList(List<FestivalAttachVO> attachList) {
    this.attachList = attachList;
  }

  @Override
  public String toString() {
    return "FestivalVO [fNo=" + fNo + ", fTitle=" + fTitle + ", fTheme=" + fTheme + ", fStartdate=" + fStartdate + ", fEnddate=" + fEnddate + ", "
        + "fHost=" + fHost + ", fPhone=" + fPhone + ", fPostcode=" + fPostcode + ", fAddr1=" + fAddr1+ ", fAddr2=" + fAddr2 + ", fAddr3=" + fAddr3 + ", "
        + "fLatitude=" + fLatitude + ", fLongitude=" + fLongitude + ", fContent=" + fContent + ", fImg=" + fImg + ", "
        + "fLike=" + fLike + ", isGood=" + isGood + ", rank=" + rank + attachList + "]";
  }

}
