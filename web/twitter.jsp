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
    <title>Twitter</title>
</head>
<body>
<h2>twitter</h2>
Zalogowany jako: ${userName}<br/>
<form class="form-signin" action="http://localhost:8080/password" method="get">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Change password</button>
</form>
<form class="form-signin" action="http://localhost:8080/twitter/logout" method="get">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log out</button>
</form>

<%--<button type="submit" formaction="http://localhost:8080/password" formmethod="get">Change password</button>--%>
<%--<button type="submit" formaction="http://localhost:8080/twitter" formmethod="delete">Log out</button>--%>

<form class="form-signin" action="http://localhost:8080/twitter" method="post">
    <input type="text" class="form-control" placeholder="Text" name="text" required autofocus>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Send</button>
</form>
<h2>Posty</h2>
<c:forEach items="${postList}" var="post" varStatus="postIndex">
   <b>${post.userName}</b>: ${post.text}<br/>
</c:forEach>
</body>
</html>
