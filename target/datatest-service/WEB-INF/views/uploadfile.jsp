<%--
  Created by IntelliJ IDEA.
  User: zhangxiusheng
  Date: 17/3/21
  Time: 下午8:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jaon文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<form name="Form2" action="/testdata/springUpload" method="post"  enctype="multipart/form-data">
    <h1>使用spring mvc提供的类的方法上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>

</body>
</html>
