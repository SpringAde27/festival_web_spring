package com.iy.festival.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VoteMapper {

  @Select("select now()")
  public String getTime();
  
  @Insert("insert into votes(user_no, f_no) values (#{userNo}, #{fNo})")
  public int insertLikeByFno(@Param("userNo") int userNo, @Param("fNo") int fNo);
  
  @Delete("delete from votes where f_no=#{fNo}")
  public void removeAllLikeByFno(@Param("fNo") int fNo);

}
