package com.iy.festival.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iy.festival.domain.FestivalVO;
import com.iy.festival.mapper.FestivalAttachMapper;
import com.iy.festival.util.MediaUtils;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {

  @Autowired
  private FestivalAttachMapper attachMapper;

  @Resource(name = "uploadFolder")
  private String uploadFolder;

  @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시 동작 _ 초 분 시 일 월 주 (년)
  public void checkFiles() throws Exception {
    log.warn("========== Task Start ==========");

    // 어제 날짜로 보관되는 첨부파일 목록
    List<FestivalVO> fileList = attachMapper.getOldFiles();

    // 객체 타입인 파일 목록을 비교를 위해 Path로 변환
    List<Path> fileListPaths = new ArrayList<Path>();

    // 원본 파일 경로 저장
    for (int i = 0; i < fileList.size(); i++) {
      fileListPaths.add(Paths.get(uploadFolder, fileList.get(i).getfImg().replace("s_", ""))); // 대표 이미지 원본 경로
      for (int j = 0; j < fileList.get(i).getAttachList().size(); j++) {
        fileListPaths.add(Paths.get(uploadFolder, fileList.get(i).getAttachList().get(j).getUploadPath().replace("s_", ""))); // 첨부파일 원본 경로
      }
    }

    // 이미지 파일이면 썸네일 파일 경로 추가 저장
    fileList.forEach(attach -> {
      // 대표 이미지
      if (MediaUtils.getMediaType(attach.getfImg().substring(attach.getfImg().lastIndexOf(".") + 1)) != null) {
        fileListPaths.add(Paths.get(uploadFolder, attach.getfImg()));
      }
      // 첨부파일
      attach.getAttachList().forEach(attachList -> {
        String formatName = attachList.getUploadPath().substring(attachList.getUploadPath().lastIndexOf(".") + 1);
        if (MediaUtils.getMediaType(formatName) != null) {
          fileListPaths.add(Paths.get(uploadFolder, attachList.getUploadPath()));
        }
      });
    });

    fileListPaths.forEach(path -> log.warn("fileListPaths >> " + path));

    // 실제 하루 전 폴더에서 데이터베이스와 비교하여 없는 파일 목록 준비
    File targetDir = Paths.get(uploadFolder, getYesterdayFolder()).toFile();

    if (targetDir.isDirectory()) {
      File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);

      // 대상 파일 삭제
      for (File file : removeFiles) {
        log.warn("removeFile >> " + file.getAbsoluteFile());
        
        file.delete();
      }
    }

    log.warn("========== Task Finish ==========");
  }

  private String getYesterdayFolder() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -1);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String str = sdf.format(calendar.getTime());

    return str.replace("-", File.separator);
  }

}
