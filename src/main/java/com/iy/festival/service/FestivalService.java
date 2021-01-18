package com.iy.festival.service;

import java.util.List;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalAttachVO;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.SearchCriteria;

public interface FestivalService {

  public void register(FestivalVO vo) throws Exception;

  public List<FestivalVO> getListAll() throws Exception;

  public List<FestivalVO> getListPage(int page) throws Exception;

  public List<FestivalVO> getListCriteria(Criteria cri) throws Exception;

  public int getTotalCount(Criteria cri) throws Exception;

  public List<FestivalVO> getListSearch(SearchCriteria cri) throws Exception;

  public int listSearchCount(SearchCriteria cri) throws Exception;

  public FestivalVO read(int fNo) throws Exception;

  public boolean remove(int fNo) throws Exception;

  public boolean modify(FestivalVO vo) throws Exception;

  public List<FestivalAttachVO> getAttachList(int fNo) throws Exception;

  public void removeByFileName(String fileName) throws Exception;

}
