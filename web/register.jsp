<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 26.05.16
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
Register <br/>
Zalogowany jako: ${userName}<br/>
<c:if test="${error!=null}">
    Bład: ${error}
</c:if>
<c:if test="${result!=null}">
    Wynik: ${result}
</c:if>
<form class="form-signin" action="http://localhost:8080/register" method="post">
    <input type="text" class="form-control" placeholder="Name" name="username" required autofocus>
    <input type="password" class="form-control" placeholder="Password" name="password" minlength="8" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
</form>

</body>
</html>
