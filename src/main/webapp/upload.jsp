<%--
  Created by IntelliJ IDEA.
  User: Ilya
  Date: 01.03.2015
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
</head>
<body>
<form action="http://104.131.184.188:8080/restoserver/upload" method="post" enctype="multipart/form-data">
    <input name="id" type="text">1<br>
    <input name="data" type="file"><br>
    <input type="submit"><br>
</form>
</body>
</html>