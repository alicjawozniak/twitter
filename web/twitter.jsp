<%--
  Created by IntelliJ IDEA.
  User: alicja
  Date: 25.05.16
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <title>Twitter</title>
</head>
<body>
twitter
${user}
${isNew}
${user.name}
${user.id}
${user.password}

<form class="form-signin" action="http://localhost:8080/twitter" method="post">
    <input type="text" class="form-control" placeholder="Text" name="text" required autofocus>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Send</button>
</form>

<%--<c:forEach items="${postList}" var="post" varStatus="postIndex">--%>
<%--${post.userName}: ${post.text}--%>
<%--</c:forEach>--%>
${postList[0].userName}:
${postList[0].text}
</body>
</html>
