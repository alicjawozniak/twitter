<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 24.05.16
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
    hello <br/>
    ${isNew}
    <form class="form-signin" action="http://localhost:8080/" method="post">
        <input type="text" class="form-control" placeholder="Email" name="email" required autofocus>
        <input type="password" class="form-control" placeholder="Hasło" name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>
</body>
</html>
