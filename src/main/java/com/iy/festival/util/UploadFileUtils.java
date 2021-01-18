package com.iy.festival.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

  private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

  public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
    UUID uuid = UUID.randomUUID();
    String onlyOriginalName = originalName.substring(originalName.lastIndexOf(File.separator) + 1);
    String savedName = uuid.toString() + "_" + onlyOriginalName;
    String savedPath = calcPath(uploadPath);

    // D:\\uploadFiles\\uploadPath\2021\09\05\s_4079f504-e930-47b9-a59a-fd1625a821ee_cat.jpg
    File target = new File(uploadPath + savedPath, savedName);
    FileCopyUtils.copy(fileData, target); // 원본 파일 저장

    // MIME Type
    String uploadedFileName = null;
    String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);

    if (MediaUtils.getMediaType(formatName) != null) {
      uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
    } else {
      uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
    }
    logger.info("uploadedFileName >> " + uploadedFileName);
    return uploadedFileName; // \2021\09\05\s_4079f504-e930-47b9-a59a-fd1625a821ee_cat.jpg
  }

  /* [/년/월/일] 정보 생성 */
  private static String calcPath(String uploadPath) {
    Calendar cal = Calendar.getInstance();

    String yearPath = File.separator + cal.get(Calendar.YEAR);
    String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
    String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

    makeDir(uploadPath, yearPath, monthPath, datePath);

    return datePath; // \2021\09\05
  }

  /* [/년/월/일] 폴더 생성 */
  private static void makeDir(String uploadPath, String... paths) {
    if (new File(paths[paths.length - 1]).exists()) {
      return;
    }

    for (String path : paths) {
      File dirPath = new File(uploadPath + path);
      if (!dirPath.exists()) {
        dirPath.mkdirs();
      }
    }
  }

  /* 썸네일 생성 */
  private static String makeThumbnail(String uploadPath, String datePath, String fileName) throws IOException {
    String originalfileName = uploadPath + datePath + File.separator + fileName; // D:\\uploadFiles\\uploadPath\2021\09\05\4079f504-e930-47b9-a59a-fd1625a821ee_cat.jpg

    BufferedImage sourceImg = ImageIO.read(new File(originalfileName));

    // height기준으로 썸네일 생성
    BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200);

    String thumbnailName = uploadPath + datePath + File.separator + "s_" + fileName; // D:\\uploadFiles\\uploadPath\2021\09\05\s_4079f504-e930-47b9-a59a-fd1625a821ee_cat.jpg
    File newFile = new File(thumbnailName);

    // 확장자
    String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

    // Thumbnail 경로/파일 이름에 resizing된 이미지를 넣는다.
    ImageIO.write(destImg, formatName.toUpperCase(), newFile);

    logger.info("경로 문자 치환 >>> " + thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/'));

    return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
  }

  /* 이미지가 아닌 파일에 대한 아이콘 생성 */
  private static String makeIcon(String uploadPath, String datePath, String fileName) {
    String iconName = uploadPath + datePath + File.separator + fileName;

    return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
  }

}
