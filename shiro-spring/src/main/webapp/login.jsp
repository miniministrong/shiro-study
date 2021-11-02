<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/11/1
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Login Page</title>
    </head>
    <body>
        <h4>Login Page</h4>
        <form action="shiro/login" method="post">
            <label>
                username:<input type="text" name="username"/>
            </label>
            <br><br>
            <label>
                password:<input type="password" name="password"/>
            </label>
            <br><br>
            <input type="submit" value="提交">
        </form>
    </body>
</html>
