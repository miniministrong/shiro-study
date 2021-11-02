package com.wenze.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
  // ShiroFilterFactoryBean 3
  @Bean(name = "shiroFilterFactoryBean")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    // 设置安全管理器
    bean.setSecurityManager(defaultWebSecurityManager);
    // 添加shiro的内置过滤器
    /*
        anon：无需认证就可以访问
        authc：必须认证了才能访问
        user：必须拥有记住我功能才能用
        perms：拥有对某个资源的权限才能访问
        roles：拥有某个角色权限才能访问
    */
    // filterChainDefinitionMap.put("/user/add", "authc");
    // filterChainDefinitionMap.put("/user/update", "authc");
    // 拦截
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    // 授权，正常的情况下，没有授权会跳转到未授权页面
    filterChainDefinitionMap.put("/user/add", "perms[user:add]");
    filterChainDefinitionMap.put("/user/update", "perms[user:update]");

    filterChainDefinitionMap.put("/user/*", "authc");

    filterChainDefinitionMap.put("/logout", "logout");
    bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    // 设置登录的请求
    bean.setLoginUrl("/toLogin");
    // 未授权的页面
    bean.setUnauthorizedUrl("/unauthorized");
    return bean;
  }

  // DefaultWebSecurityManager 2
  @Bean(name = "defaultWebSecurityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 关联UserRealm
    securityManager.setRealm(userRealm);
    return securityManager;
  }

  // 创建Realm对象，需要自定义 1
  @Bean(name = "userRealm")
  public UserRealm userRealm(){
    return new UserRealm();
  }

  // 整合ShiroDialect：用来整合Shiro thymeleaf
  @Bean
  public ShiroDialect getShiroDialect(){
    return new ShiroDialect();
  }
}
