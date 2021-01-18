package com.iy.festival.service;

public interface VoteService {

  public int addLikeByFno(int userNo, int fNo) throws Exception;
  
  public void removeAllLikeByFno(int fNo) throws Exception;

}
