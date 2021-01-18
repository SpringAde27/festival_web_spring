package com.iy.festival.domain;

public class Criteria {

  private int page; // 페이지 번호
  private int amount; // 페이지 당 데이터 수

  public Criteria() {
    // this(1, 10);
    this.page = 1;
    this.amount = 10;
  }

  public Criteria(int page, int amount) {
    this.page = page;
    this.amount = amount;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    if (page <= 0) {
      this.page = 1;
      return;
    }

    this.page = page;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    if (amount <= 0 || amount > 100) {
      this.amount = 10;
      return;
    }
    this.amount = amount;
  }

  /* 페이지 시작 번호 */
  public int getPageStart() {
    return (this.page - 1) * amount;
  }

  @Override
  public String toString() {
    return String.format("Criteria [page=%s, amount=%s]", page, amount);
  }

}
