package com.wenze.shiro.handlers;

import com.wenze.shiro.services.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

  @Autowired
  private ShiroService shiroService;

  @RequestMapping("/testShiroAnnotation")
  public String testShiroAnnotation(HttpServletSession session){
    session.setAttribute("key", "value12345");
    shiroService.testMethod();
    return "redirect:/list.jsp";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestParam("username")String username, @RequestParam("password")String password){
    Subject currentUser = SecurityUtils.getSubject();
    if (!currentUser.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
      token.setRememberMe(true);
      try {
        currentUser.login(token);
      } catch (AuthenticationException e) {
        System.out.println("登录失败：" + e.getMessage());
      }
    }
    return "redirect:/list.jsp";
  }
}
