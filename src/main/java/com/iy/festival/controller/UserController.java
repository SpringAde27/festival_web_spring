package com.iy.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/user/*")
public class UserController {

  @GetMapping("/guest")
  public void doGuest() {
    log.info("hello everybody");
  }
  
  @GetMapping("/member")
  public void doMember() {
    log.info("logined member");
  }
  
  @GetMapping("/admin")
  public void doAdmin() {
    log.info("only admin");
  }

}