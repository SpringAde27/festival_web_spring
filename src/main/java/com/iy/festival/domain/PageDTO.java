package com.iy.festival.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageDTO {

  private int totalCount;
  private int startPage;
  private int endPage;
  private int realEndPage;
  private boolean prev, next;
  private int displayPageNumber = 10;
  private Criteria cri;

  public PageDTO(Criteria cri, int totalCount) {
    this.cri = cri;
    this.totalCount = totalCount;

    this.endPage = (int) (Math.ceil(cri.getPage() / (double) this.displayPageNumber) * this.displayPageNumber);

    this.startPage = (this.endPage - this.displayPageNumber) + 1;

    this.realEndPage = (int) (Math.ceil((this.totalCount * (double) 1.0) / cri.getAmount()));

    if (this.endPage > this.realEndPage) {
      this.endPage = this.realEndPage;
    }

    this.prev = this.startPage > 1;

    this.next = this.endPage < this.realEndPage;

    // this.next = this.endPage * cri.getAmount() >= totalCount ? false : true;
  }

  public String makeQuery(int page) {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .queryParam("page", page)
        .queryParam("amount", cri.getAmount())
        .build();
    
    return uriComponents.toUriString();
  }

  // path, query에 해당하는 매개변수(커맨드)를 만들어준다.
  public String makeSearch(int page) {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .queryParam("page", page)
        .queryParam("amount", cri.getAmount())
        .queryParam("searchType", ((SearchCriteria) cri).getSearchType())
        .queryParam("keyword", encoding(((SearchCriteria) cri).getKeyword()))
        .build(); // ?page=1&amount=10&searchType=t&keyword=title 형식으로 매개변수(커맨드)를 만들어준다.
    
    return uriComponents.toUriString();
  }

  private String encoding(String keyword) {
    if (keyword == null || keyword.trim().length() == 0) {
      return "";
    }

    try {
      return URLEncoder.encode(keyword, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getStartPage() {
    return startPage;
  }

  public void setStartPage(int startPage) {
    this.startPage = startPage;
  }

  public int getEndPage() {
    return endPage;
  }

  public void setEndPage(int endPage) {
    this.endPage = endPage;
  }

  public boolean isPrev() {
    return prev;
  }

  public void setPrev(boolean prev) {
    this.prev = prev;
  }

  public boolean isNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }

  public int getDisplayPageNumber() {
    return displayPageNumber;
  }

  public void setDisplayPageNumber(int displayPageNumber) {
    this.displayPageNumber = displayPageNumber;
  }

  public Criteria getCri() {
    return cri;
  }

  public void setCri(Criteria cri) {
    this.cri = cri;
  }

  @Override
  public String toString() {
    return String.format(
        "PageDTO [totalCount=%s, startPage=%s, endPage=%s, realEndPage=%s, prev=%s, next=%s, displayPageNumber=%s, cri=%s]",
        totalCount, startPage, endPage, realEndPage, prev, next, displayPageNumber, cri);
  }

}
