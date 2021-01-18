package com.iy.festival.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iy.festival.mapper.VoteMapper;
import com.iy.festival.persistence.FestivalDAO;

@Service
public class VoteServiceImpl implements VoteService {

  @Autowired
  private VoteMapper mapper;

  @Autowired
  private FestivalDAO festivalDao;

  @Transactional
  @Override
  public int addLikeByFno(int userNo, int fNo) throws Exception {
    festivalDao.updateLikeCount(fNo, 1);
    return mapper.insertLikeByFno(userNo, fNo);
  }

  @Override
  public void removeAllLikeByFno(int fNo) throws Exception {
    mapper.removeAllLikeByFno(fNo);
  }

}
