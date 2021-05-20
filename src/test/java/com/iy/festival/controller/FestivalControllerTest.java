package com.iy.festival.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.SearchCriteria;
import com.iy.festival.persistence.FestivalDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
  "file:src/main/webapp/WEB-INF/spring/root-context.xml",
  "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
})
public class FestivalControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(FestivalControllerTest.class);

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Autowired
  private FestivalDAO dao;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  //  @Test
  public void testRegister() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/festival/register")
        .param("fTitle", "제목 테스트")
        .param("fTheme", "주제 테스트"))
        .andReturn()
        .getModelAndView()
        .getViewName();

    logger.info(result);
  }

  //  @Test
  public void testGetListAll() throws Exception {
    logger.info("testGetListAll --------- ", mockMvc.perform(MockMvcRequestBuilders.get("/festival/list"))
        .andReturn().getModelAndView().getModelMap());
  }

  //  @Test
  public void testRead() throws Exception {
    logger.info("testRead ---------- ", mockMvc.perform(MockMvcRequestBuilders.get("/festival/read")
        .param("no", "11"))
        .andReturn().getModelAndView().getModelMap());
  }

  //  @Test
  public void testModify() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/festival/modify")
        .param("fNo", "11")
        .param("fTitle", "제목 수정")
        .param("fTheme", "주제 수정")
        .param("fHost", "주관 수정")
        .param("fPhone", "01099998888")
        .param("fLatitude", "35.7923023161251")
        .param("fLongitude", "129.331725391321"))
        .andReturn().getModelAndView().getViewName();

    logger.info(result);
  }

  //  @Test
  public void testRemove() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/festival/remove")
        .param("fNo", "11"))
        .andReturn().getModelAndView().getViewName();

    logger.info(result);
  }

  //  @Test
  public void testListPage() throws Exception {
    int page = 2;

    List<FestivalVO> list = dao.getListPage(page);

    for (FestivalVO vo : list) {
      logger.info(vo.getfNo() + " : " + vo.getfTitle());
    }
  }

  //  @Test
  public void testListCriteria() throws Exception {
    Criteria cri = new Criteria();
    cri.setPage(2);
    cri.setAmount(10);

    List<FestivalVO> list = dao.getListCriteria(cri);
    list.forEach(vo -> logger.info(vo.getfNo() + " : " + vo.getfTitle()));

    //  dao.getListCriteria(new Criteria(2, 10)).forEach(vo -> logger.info(vo.getfNo() + " : " + vo.getfTitle()));
  }

  //  @Test
  public void testListCritetia() throws Exception {
    logger.info("testListCritetia ---------- ", mockMvc.perform(MockMvcRequestBuilders.get("/festival/list")
        .param("page", "2")
        .param("amount", "10"))
        .andReturn().getModelAndView().getModelMap());
  }

  //  @Test
  public void testURIComponent() throws Exception {
    UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/festival/list")
        .queryParam("page", 2)
        .queryParam("amount", 10)
        .build();

    logger.info(uriComponents.toString());
  }

  @Test
  public void testSQL1() throws Exception {
    SearchCriteria cri = new SearchCriteria();
    cri.setPage(1);
    cri.setSearchType("t");
    cri.setKeyword("title");

    logger.info("============================== START");

    List<FestivalVO> list = dao.getListSearch(cri);

    for (FestivalVO vo : list) {
      logger.info(vo.getfNo() + " : " + vo.getfTitle());
    }

    logger.info("============================== END");

    logger.info("count >> " + dao.listSearchCount(cri));
  }

}
