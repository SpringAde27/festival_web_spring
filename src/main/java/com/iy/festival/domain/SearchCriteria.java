package com.iy.festival.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCriteria extends Criteria {

  private String searchType;
  private String keyword;

  public String getSearchType() {
    return searchType;
  }

  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  /* 여러개의 파라미터들을 연결하여 URL형태로 만들기 */
  public String getListLink() {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("")
        .queryParam("page", this.getPage())
        .queryParam("amount", this.getAmount())
        .queryParam("searchType", this.getSearchType())
        .queryParam("keyword", this.getKeyword());
    
    return uriBuilder.toUriString();
  }

  @Override
  public String toString() {
    return super.toString() + String.format(" / SearchCriteria [searchType=%s, keyword=%s]", searchType, keyword);
  }

}
