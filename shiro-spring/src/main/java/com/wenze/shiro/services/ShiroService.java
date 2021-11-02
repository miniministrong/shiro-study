package com.wenze.shiro.services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

import java.util.Date;

public class ShiroService {

  @RequiresRoles({"admin"})
  public void testMethod(){
    System.out.println("testMethod, time: " + new Date());
    Session session = SecurityUtils.getSubject().getSession();
    String val = (String) session.getAttribute("key");
    System.out.println("Service SessionVal : " + val);
  }
}
