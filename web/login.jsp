<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 25.05.16
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:if test="${userName!=null && !userName.equals('')}">
    Zalogowany jako: ${userName}<br/>
</c:if>
<c:if test="${error!=null}">
    Bład: ${error}
</c:if>
<form class="form-signin" action="http://localhost:8080/login" method="post">
    <input type="text" class="form-control" placeholder="Name" name="username" required autofocus>
    <input type="password" class="form-control" placeholder="Password" name="password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
</form>
</body>
</html>
