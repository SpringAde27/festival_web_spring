package com.iy.festival.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iy.festival.domain.Criteria;
import com.iy.festival.domain.FestivalAttachVO;
import com.iy.festival.domain.FestivalVO;
import com.iy.festival.domain.PageDTO;
import com.iy.festival.domain.SearchCriteria;
import com.iy.festival.service.FestivalService;
import com.iy.festival.service.VoteService;
import com.iy.festival.util.MediaUtils;
import com.iy.festival.util.UploadFileUtils;

@Controller /* view 제어 & service 호출 */
@RequestMapping("/festival/*")
public class FestivalController {

  private static final Logger logger = LoggerFactory.getLogger(FestivalController.class);

  @Autowired
  private FestivalService service;

  @Autowired
  private VoteService voteServie;

  @Resource(name = "uploadFolder") // servlet-context.xml의 @Resource id로 주입받을 때 사용
  private String uploadFolder;

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String registerGET() throws Exception {
    return "festival/register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String registerPOST(FestivalVO vo, RedirectAttributes rttr, HttpServletRequest request) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();

    MultipartFile attachImg = vo.getAttachImg();

    if (!attachImg.isEmpty()) {
      // \2021\09\05\s_4079f504-e930-47b9-a59a-fd1625a821ee_cat.jpg
      String uploadedFile = UploadFileUtils.uploadFile(uploadFolder, attachImg.getOriginalFilename(), attachImg.getBytes());
      vo.setfImg(uploadedFile);
    }

    String RequestURI = request.getRequestURI();
    map.put("uri", RequestURI);

    service.register(vo);
    map.put("flag", "success");
    map.put("id", vo.getfNo());

    rttr.addFlashAttribute("result", map);

    return "redirect:/festival/list";
  }

  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  public String listGET(Model model) throws Exception {
    model.addAttribute("list", service.getListAll());
    return "festival/listAll";
  }

  // 페이징
  @RequestMapping(value = "/listPage", method = RequestMethod.GET)
  public String listGET(Model model, Criteria cri) throws Exception {
    model.addAttribute("list", service.getListCriteria(cri));

    PageDTO pageMaker = new PageDTO(cri, service.getTotalCount(cri));
    model.addAttribute("pageMaker", pageMaker);

    return "festival/listPage";
  }

  // 페이징 & 검색
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String listGET(Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
    logger.info(cri.toString());

    model.addAttribute("list", service.getListSearch(cri));

    PageDTO pageMaker = new PageDTO(cri, service.listSearchCount(cri));
    model.addAttribute("pageMaker", pageMaker);

    logger.info("pageMaker >> " + pageMaker);

    return "festival/list";
  }

  @RequestMapping(value = { "/read", "/modify" }, method = RequestMethod.GET)
  public void read(@RequestParam("no") int fNo, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
    // @RequestParam 파라미터 이름과 변수 이름이 상이한 경우 @RequestParam 이용
    // @ModelAttribute = model.addAttribute
    // 리턴 타입이 void인 경우, url경로를 view파일 이름으로 사용

    // key값을 지정하지 않으면 모델 객체명으로 저장된다.(festivalVO)
    model.addAttribute(service.read(fNo));

    // 첨부파일 목록을 조회하여 이미지 파일 찾아서 스와이프 이미지로 사용
    List<FestivalAttachVO> attachList = service.getAttachList(fNo);

    List<String> attachments = new ArrayList<>();

    for (int i = 0; i < attachList.size(); i++) {
      String attachImgName = attachList.get(i).getUploadPath();
      String formatName = attachImgName.substring(attachImgName.lastIndexOf(".") + 1);
      MediaType mediaType = MediaUtils.getMediaType(formatName);

      if (mediaType != null) {
        attachments.add(attachImgName);
      }
    }

    model.addAttribute("attachments", attachments);
  }

  /*
   * @RequestMapping(value="/modify", method=RequestMethod.GET) public String modify(int fNo, Model model) throws Exception { model.addAttribute(service.read(fNo)); return "festival/modify"; }
   */

  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public String modify(FestivalVO vo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr, HttpServletRequest request) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();

    String RequestURI = request.getRequestURI();
    map.put("uri", RequestURI);

    MultipartFile attachedFile = vo.getAttachImg();

    if (!attachedFile.isEmpty()) {
      String uploadedFile = UploadFileUtils.uploadFile(uploadFolder, attachedFile.getOriginalFilename(), attachedFile.getBytes());
      vo.setfImg(uploadedFile);
    }

    if (service.modify(vo)) {
      map.put("flag", "success");
      map.put("id", vo.getfNo());
    } else {
      map.put("flag", "fail");
      map.put("id", vo.getfNo());
    }

    rttr.addAttribute("page", cri.getPage());
    rttr.addAttribute("amount", cri.getAmount());
    rttr.addAttribute("searchType", cri.getSearchType());
    rttr.addAttribute("keyword", cri.getKeyword());
    rttr.addAttribute("no", vo.getfNo());

    rttr.addFlashAttribute("result", map);

    return "redirect:/festival/read";
  }

  @RequestMapping(value = "/remove", method = RequestMethod.POST)
  public String remove(String fImg, @RequestParam("no") int fNo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr, HttpServletRequest request) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();

    String RequestURI = request.getRequestURI();
    map.put("uri", RequestURI);

    List<FestivalAttachVO> attachList = service.getAttachList(fNo);

    if (service.remove(fNo)) {
      // 대표 이미지 파일 삭제
      if (fImg != null) {
        try {
          // 원본 삭제
          String originalFileName = fImg.replace("s_", "");
          Path originalFilePath = Paths.get(uploadFolder + originalFileName);
          Files.deleteIfExists(originalFilePath);

          //  썸네일 삭제
          if (Files.probeContentType(originalFilePath).startsWith("image")) {
            Path thumbnailFile = Paths.get(uploadFolder + fImg);
            Files.delete(thumbnailFile);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      // 첨부 파일 삭제
      deleteAttachFiles(attachList);

      map.put("flag", "success");
      map.put("id", fNo);
    } else {
      map.put("flag", "fail");
      map.put("id", fNo);
    }

    if (service.getListSearch(cri).size() <= 0) {
      cri.setPage(cri.getPage() - 1);
    }

    // rttr.addAttribute("page", cri.getPage());
    // rttr.addAttribute("amount", cri.getAmount());
    // rttr.addAttribute("searchType", cri.getSearchType());
    // rttr.addAttribute("keyword", cri.getKeyword());

    rttr.addFlashAttribute("result", map);

    if ((map.get("flag") == "fail")) {
      return "redirect:/festival/read" + cri.getListLink();
    }

    return "redirect:/festival/list" + cri.getListLink();
  }

  // 좋아요 ajax
  @ResponseBody
  @RequestMapping(value = "/vote/new/{userNo}/{fNo}")
  public ResponseEntity<String> addLike(@PathVariable("userNo") int userNo, @PathVariable("fNo") int fNo) throws Exception {
    ResponseEntity<String> entity = null;

    int insertCount = voteServie.addLikeByFno(userNo, fNo);

    if (insertCount == 1) {
      entity = new ResponseEntity<String>("success", HttpStatus.OK);
    } else {
      entity = new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return entity;
  }

  // 첨부파일 업로드 ajax
  @PostMapping("/attachFile")
  @ResponseBody
  public ResponseEntity<List<FestivalAttachVO>> attachFileAjax(List<MultipartFile> attachFile) throws Exception {
    List<FestivalAttachVO> list = new ArrayList<>();

    if (!attachFile.isEmpty()) {
      for (MultipartFile multipartFile : attachFile) {
        String uploadedFileName = UploadFileUtils.uploadFile(uploadFolder, multipartFile.getOriginalFilename(), multipartFile.getBytes());
        String fileName = uploadedFileName.substring(uploadedFileName.indexOf("s_") + 2);
        String uuid = fileName.substring(0, fileName.indexOf("_"));

        String formatName = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1);
        MediaType mediaType = MediaUtils.getMediaType(formatName);

        if (mediaType == null) {
          uuid = uploadedFileName.substring(uploadedFileName.lastIndexOf("/") + 1);
          uuid = uuid.substring(0, uuid.indexOf("_"));
        }

        FestivalAttachVO attachVO = new FestivalAttachVO();
        attachVO.setUuid(uuid);
        attachVO.setUploadPath(uploadedFileName);
        attachVO.setFileName(fileName);

        list.add(attachVO);
      }
    }

    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  // 첨부파일 삭제 ajax
  @PostMapping("/deleteAttachFile")
  @ResponseBody
  public ResponseEntity<String> deleteAttachFile(String fileName) throws Exception {
    ResponseEntity<String> entity = null;

    try {
      String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
      MediaType mediaType = MediaUtils.getMediaType(formatName);

      // DB 삭제
      service.removeByFileName(fileName);

      // 썸네일 삭제
      if (mediaType != null) {
        File thumbnailFile = new File(uploadFolder + fileName);
        thumbnailFile.delete();
      }

      // 원본 삭제
      String originalFileName = fileName.replace("s_", "");
      File originalFile = new File(uploadFolder + originalFileName);
      originalFile.delete();

      entity = new ResponseEntity<String>("success", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

    return entity;
  }

  // 첨부파일 출력_<img src="displayFile?fileName=${ festivalVO.fImg }"/>
  @RequestMapping(value = "/displayFile", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<byte[]> getFile(String fileName) throws Exception {
    ResponseEntity<byte[]> entity = null;
    File file = new File(uploadFolder, fileName);

    try {
      HttpHeaders header = new HttpHeaders();
      header.add("Content-Type", Files.probeContentType(file.toPath())); // MIME Type을 헤더에 포함
      entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.CREATED); // 파일 출력
    } catch (IOException e) {
      entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
      e.printStackTrace();
    }
    return entity;
  }

  // 첨부파일 다운로드 (produces : 서버가 요청에 대한 응답에 대해서 보내야 할 MIME를 명시)
  @GetMapping(value = "/downloadFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  @ResponseBody
  public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileName, @RequestHeader("User-Agent") String userAgent) throws Exception {
    // byte[]을 사용할 수 있으나, Resource타입을 이용하여 간단하게 처리한 예제
    org.springframework.core.io.Resource resource = new FileSystemResource(uploadFolder + fileName);

    if (resource.exists() == false) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    String resourceName = resource.getFilename().substring(resource.getFilename().indexOf("_") + 1);

    HttpHeaders headers = new HttpHeaders();

    try {
      String downloadName = null;

      // IE, Edge, Chrome
      if (userAgent.contains("Trident")) {
        downloadName = URLEncoder.encode(resourceName, "UTF-8").replace("\\+", " ");
      } else if (userAgent.contains("Edge")) {
        downloadName = URLEncoder.encode(resourceName, "UTF-8");
      } else {
        downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
      }

      // 다운로드 시 저장되는 이름을 명시
      headers.add("Content-Disposition", "attachment; filename=" + downloadName);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<org.springframework.core.io.Resource>(resource, headers, HttpStatus.OK);
  }

  // 첨부파일 목록 조회 ajax
  @GetMapping(value = "/getAttachList")
  @ResponseBody // @RestController로 작성되지 않았기 때문에 @ResponseBody를 적용해 JSON 데이터를 반환
  public ResponseEntity<List<FestivalAttachVO>> getAttachList(int fNo) throws Exception {
    return new ResponseEntity<>(service.getAttachList(fNo), HttpStatus.OK);
  }

  // 파일 삭제
  private void deleteAttachFiles(List<FestivalAttachVO> attachList) throws Exception {
    if (attachList == null || attachList.size() == 0) {
      return;
    }

    for (int i = 0; i < attachList.size(); i++) {
      try {
        // 파일 원본 삭제
        String originalFileName = attachList.get(i).getUploadPath().replace("s_", "");
        Path originalFilePath = Paths.get(uploadFolder + originalFileName);
        Files.deleteIfExists(originalFilePath);

        logger.info("remove attach file :: " + attachList.get(i).getUploadPath());

        // 이미지 썸네일 삭제
        if (Files.probeContentType(originalFilePath).startsWith("image")) {
          Path thumbnailFile = Paths.get(uploadFolder + attachList.get(i).getUploadPath());
          Files.delete(thumbnailFile);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
