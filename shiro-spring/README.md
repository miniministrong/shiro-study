### Shiro认证实现的流程

1. 获取当前的Subject，调用SecurityUtils.getSubject()
2. 测试当前的用户是否已经被认证，即是否已经登录。调用Subject的isAuthenticated()
3. 若没有认证，则把用户名和密码封装为UsernamePasswordToken对象
- 创建一个表单页面
- 把请求提交到SpringMVC的Handler
- 获取用户名和密码
4. 执行登录：调用Subject的login(AuthenticationToken)方法
5. 自定义Realm的方法，从数据库中获取到对应的记录，返回给Shiro
- 实际上需要继承 org.apache.shiro.realm.AuthenticatingRealm 类
- 实现 doGetAuthenticationInfo(AuthenticationToken) 方法
6. 由Shiro完成对密码的比对

### 密码的比对
通过 AuthenticatingRealm 的 credentialsMatcher 属性来进行密码的比对！

### 密码的MD5加密
1. 如何把一个字符串加密为MD5
2. 替换当前Realm的credentialsMatcher 属性，直接使用 HashedCredentialsMatcher 对象，并设置加密算法即可。

### 密码的盐值加密
1. 为什么使用MD5盐值加密？

2. 如何做到：

1). 在 doGetAuthenticationInfo 方法返回值创建 SimpleAuthenticationInfo 对象的时候，需要使用 SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName) 构造器

2). 使用ByteSource.Util.bytes() 来计算盐值

3). 盐值需要唯一值：一般使用随机字符串或者user id

4). 使用 new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations); 来计算盐值加密后的密码的值

### 授权
1. 授权需要继承 AuthorizingRealm 类，并实现其 doGetAuthorizationInfo 方法
2. AuthorizingRealm 类继承自 AuthenticatingRealm ，但没有实现 AuthenticatingRealm 中的 doGetAuthorizationInfo，所以认证和授权只需要继承 AuthorizingRealm 就可以了。同时实现他的两个抽象方法。


