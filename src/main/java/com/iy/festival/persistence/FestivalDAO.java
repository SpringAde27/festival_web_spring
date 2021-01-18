package com.iy.festival.persistence;

import java.util.List;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.SearchCriteria;

public interface FestivalDAO {

  public void create(FestivalVO vo) throws Exception;

  public List<FestivalVO> getListAll() throws Exception;

  public List<FestivalVO> getListPage(int page) throws Exception;

  public List<FestivalVO> getListCriteria(Criteria cri) throws Exception;

  public int getTotalCount(Criteria cri) throws Exception;

  public List<FestivalVO> getListSearch(SearchCriteria cri) throws Exception;

  public int listSearchCount(SearchCriteria cri) throws Exception;

  public FestivalVO read(int fNo) throws Exception;

  public int delete(int fNo) throws Exception;

  public int modify(FestivalVO vo) throws Exception;

  public void updateLikeCount(int fNo, int amount) throws Exception;

}
