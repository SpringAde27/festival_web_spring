package com.iy.festival.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class CommonController {

  @GetMapping("/error/error_403")
  public void accessDenied(Authentication auth) {
    log.info("Access Denied >>> " + auth);
  }
  
  @GetMapping("/user/login")
  public void getLogin(String error, String logout, Model model) {
    log.info("error >> " + error);
    log.info("logout >> " + logout);
    
    if(error != null) {
      model.addAttribute("error", "Login Error Check Your Account.");
    }
    
    if(logout != null) {
      model.addAttribute("logout", "Logout Success.");
    }
  }
  
  @GetMapping("/user/logout")
  public void getLogout() {
    log.info("Logout");
  }

}
