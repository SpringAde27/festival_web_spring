package com.iy.festival.mapper;

import java.util.List;

import com.iy.festival.domain.FestivalAttachVO;

public interface FestivalAttachMapper {

  public void insert(FestivalAttachVO vo);

  public void delete(String fileName);

  public List<FestivalAttachVO> findAttachFileByFno(int fNo);

  public void deleteByAll(int fNo);

}