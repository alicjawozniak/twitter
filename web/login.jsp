<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 25.05.16
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
login
${isNew}
<form class="form-signin" action="http://localhost:8080/login" method="post">
    <input type="text" class="form-control" placeholder="Email" name="email" required autofocus>
    <input type="password" class="form-control" placeholder="Hasło" name="password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
</form>
</body>
</html>
