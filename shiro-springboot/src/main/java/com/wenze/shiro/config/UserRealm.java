package com.wenze.shiro.config;

import com.wenze.shiro.model.User;
import com.wenze.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义的Realm
public class UserRealm extends AuthorizingRealm {
  @Autowired
  private UserService userService;
  // 授权
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    System.out.println("执行了=>授权doGetAuthorizationInfo");
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    // info.addStringPermission("user:add");
    // 拿到当前登录的这个对象
    Subject subject = SecurityUtils.getSubject();
    // 拿到User对象
    User currentUser = (User) subject.getPrincipal();
    // 设置当前用户的权限
    info.addStringPermission(currentUser.getPerms());
    return info;
  }

  // 认证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    System.out.println("执行了=>认证doGetAuthorizationInfo");
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    // 连接真实的数据库
    User user = userService.queryUserByName(token.getUsername());
    if (user == null) { // 没有这个人
      return null; // 抛出异常 UnknownAccountException
    }
    Subject currentSubject = SecurityUtils.getSubject();
    Session session = currentSubject.getSession();
    session.setAttribute("loginUser", user);
    // 可以加密： MD5： MD5盐值加密：
    // 密码认证，Shiro做
    return new SimpleAuthenticationInfo(user, user.getPassword(), "");
  }
}
