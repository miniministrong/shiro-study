package com.wenze.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

  private static final transient Logger log = LoggerFactory.getLogger(HelloWorld.class);

  public static void main(String[] args) {
    log.info("My First Apache Shiro Application");
    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // get the currently executing user:
    // 获取当前的Subject，地爱用SecurityUtils.getSubject()方法
    Subject currentUser = SecurityUtils.getSubject();

    // Do some stuff with a Session (no need for a web or EJB container!!!)
    // 测试使用Session
    // 获取Session：调用Subject的getSession()方法
    Session session = currentUser.getSession();
    session.setAttribute("someKey", "aValue");
    String value = (String) session.getAttribute("someKey");
    if (value.equals("aValue")) {
      log.info("----> 成功获取当前值! [" + value + "]");
    }

    // let's login the current user so we can check against roles and permissions:
    // 测试当前的用户是否已经被认证，即是否已经登录，调用Subject的isAuthenticated()方法
    if (!currentUser.isAuthenticated()) {
      // 把用户名和密码封装为UsernamePasswordToken对象
      UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
      // remember me
      token.setRememberMe(true);
      try {
        // 执行登录
        currentUser.login(token);
        // 若没有指定的账户，则Shiro将会抛出UnknownAccountException异常
      } catch (UnknownAccountException uae) {
        log.info("当前用户名不存在： " + token.getPrincipal());
        return;
        // 若账户存在，但密码不匹配，则Shiro会抛出IncorrectCredentialsException 异常
      } catch (IncorrectCredentialsException ice) {
        log.info("当前账户的密码： " + token.getPrincipal() + " 是错误的!");
        return;
        // 用户被锁定的异常
      } catch (LockedAccountException lae) {
        log.info("The account for username " + token.getPrincipal() + " is locked.  " +
            "Please contact your administrator to unlock it.");
      }
      // ... catch more exceptions here (maybe custom ones specific to your application?
      // 所有认证时异常的父类
      catch (AuthenticationException ae) {
        //unexpected condition?  error?
      }
    }

    //say who they are:
    //print their identifying principal (in this case, a username):
    log.info("------------>  用户 [" + currentUser.getPrincipal() + "] 登录成功.");

    //test a role:
    // 测试是否有某一个角色，调用Subject的hasRole()方法
    if (currentUser.hasRole("schwartz")) {
      log.info("存在当前角色!");
    } else {
      log.error("不存在当前角色");
    }

    //test a typed permission (not instance-level)
    // 测试用户是否具备某一个行为，调用Subject的isPermitted()方法
    if (currentUser.isPermitted("lightsaber:wield")) {
      log.info("你可以使用lightsaber权限，使用它是比较明智的");
    } else {
      log.info("对不起， lightsaber 权限只能由 schwartz 来使用.");
      return;
    }

    //a (very powerful) Instance Level permission:
    // 测试用户是否具备某一个行为
    if (currentUser.isPermitted("winnebago:drive:eagle5")) {
      log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
          "Here are the keys - have fun!");
    } else {
      log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
    }

    //all done - log out!
    // 执行登出，调用Subject的logout()方法
    System.out.println("------> " + currentUser.isAuthenticated());
    currentUser.logout();
    System.out.println("------> " + currentUser.isAuthenticated());
    System.exit(0);
  }
}
