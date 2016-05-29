<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 28.05.16
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Change password</title>
</head>
<body>
Zalogowany jako: ${userName}<br/>
<c:if test="${error!=null}">
    Bład: ${error}
</c:if>
<c:if test="${result!=null}">
    Wynik: ${result}
</c:if>
<form class="form-signin" action="http://localhost:8080/password" method="post">
    <input type="password" class="form-control" placeholder="Current Password" name="currentPassword" required
           autofocus>
    <input type="password" class="form-control" placeholder="New Password" name="newPassword" minlength="8" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Change Password</button>
</form>
</body>
</html>
