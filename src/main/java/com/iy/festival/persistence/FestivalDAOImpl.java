package com.iy.festival.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.SearchCriteria;

@Repository /* DAO(VO를 가지고 DB관련 처리) 인식 */
public class FestivalDAOImpl implements FestivalDAO {

  /* 맵퍼에 있는 네임스페이스와 일치시킨다 */
  private static final String namespace = "com.iy.festival.mappers.FestivalMapper";

  @Autowired
  private SqlSession session;

  @Override
  public void create(FestivalVO vo) throws Exception {
    session.insert(namespace + ".create", vo);
  }

  @Override
  public List<FestivalVO> getListAll() throws Exception {
    return session.selectList(namespace + ".getListAll");
  }

  @Override
  public List<FestivalVO> getListPage(int page) throws Exception {
    if (page <= 0) {
      page = 1;
    }

    page = (page - 1) * 10;

    return session.selectList(namespace + ".getListPage", page);
  }

  @Override
  public List<FestivalVO> getListCriteria(Criteria cri) throws Exception {
    return session.selectList(namespace + ".getListCriteria", cri);
  }

  @Override
  public int getTotalCount(Criteria cri) throws Exception {
    return session.selectOne(namespace + ".getTotalCount", cri);
  }

  @Override
  public List<FestivalVO> getListSearch(SearchCriteria cri) throws Exception {
    return session.selectList(namespace + ".getListSearch", cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {
    return session.selectOne(namespace + ".listSearchCount", cri);
  }

  @Override
  public FestivalVO read(int fNo) throws Exception {
    FestivalVO vo = session.selectOne(namespace + ".read", fNo);
    return vo;
  }

  @Override
  public int delete(int fNo) throws Exception {
    return session.delete(namespace + ".delete", fNo);
  }

  @Override
  public int modify(FestivalVO vo) throws Exception {
    return session.update(namespace + ".update", vo);
  }

  @Override
  public void updateLikeCount(int fNo, int amount) throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("fNo", fNo);
    map.put("amount", amount);
    session.update(namespace + ".updateLikeCount", map);
  }

}
