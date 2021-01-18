package com.iy.festival.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalAttachVO;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.SearchCriteria;
import com.iy.festival.mapper.FestivalAttachMapper;
import com.iy.festival.mapper.VoteMapper;
import com.iy.festival.persistence.FestivalDAO;

@Service /* DAO(persistence)호출 & 비지니스 로직 처리 */
public class FestivalServiceImpl implements FestivalService {

  @Autowired
  private VoteMapper voteMapper;

  @Autowired
  private FestivalAttachMapper attachMapper;

  @Autowired
  private FestivalDAO dao;

  @Transactional
  @Override
  public void register(FestivalVO vo) throws Exception {
    dao.create(vo);

    if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
      return;
    }

    vo.getAttachList().forEach(attach -> {
      attach.setFNo(vo.getfNo());
      attachMapper.insert(attach);
    });
  }

  @Override
  public List<FestivalVO> getListAll() throws Exception {
    return dao.getListAll();
  }

  @Override
  public List<FestivalVO> getListPage(int page) throws Exception {
    return dao.getListPage(page);
  }

  @Override
  public List<FestivalVO> getListCriteria(Criteria cri) throws Exception {
    return dao.getListCriteria(cri);
  }

  @Override
  public int getTotalCount(Criteria cri) throws Exception {
    return dao.getTotalCount(cri);
  }

  @Override
  public List<FestivalVO> getListSearch(SearchCriteria cri) throws Exception {
    return dao.getListSearch(cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {
    return dao.listSearchCount(cri);
  }

  @Override
  public FestivalVO read(int fNo) throws Exception {
    FestivalVO vo = dao.read(fNo);
    return vo;
  }

  @Transactional
  @Override
  public boolean remove(int fNo) throws Exception {
    voteMapper.removeAllLikeByFno(fNo);
    attachMapper.deleteByAll(fNo);
    return dao.delete(fNo) == 1;
  }

  @Transactional
  @Override
  public boolean modify(FestivalVO vo) throws Exception {
    if (vo.getAttachList() != null && vo.getAttachList().size() > 0) {
      attachMapper.deleteByAll(vo.getfNo());
    }

    boolean modifyResult = false;

    if (dao.modify(vo) > 0) {
      modifyResult = true;
    } else {
      modifyResult = false;
    }

    if (modifyResult && vo.getAttachList() != null && vo.getAttachList().size() > 0) {
      vo.getAttachList().forEach(attach -> {
        attach.setFNo(vo.getfNo());
        attachMapper.insert(attach);
      });
    }

    return modifyResult;
  }

  @Override
  public List<FestivalAttachVO> getAttachList(int fNo) throws Exception {
    return attachMapper.findAttachFileByFno(fNo);
  }

  @Override
  public void removeByFileName(String fileName) throws Exception {
    attachMapper.delete(fileName);
  }

}
