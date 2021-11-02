<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/11/1
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>List</title>
    </head>
    <body>
        <h4>List Page</h4>

        Welcome:<shiro:principal></shiro:principal>
        <shiro:hasRole name="admin">
            <br><br>
            <a href="admin.jsp">Admin Page</a>
        </shiro:hasRole>

        <shiro:hasRole name="user">
            <br><br>
            <a href="user.jsp">User Page</a>
        </shiro:hasRole>

        <br><br>
        <a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>

        <br><br>
        <a href="shiro/logout">logout</a>
    </body>
</html>
