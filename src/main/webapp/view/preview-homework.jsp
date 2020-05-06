<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Preview Homework</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<div align="center">
    <h3>${pageContext.request.contextPath}/homework/getFile?homeworkId=${homeworkId}"</h3>
    <iframe src="${pageContext.request.contextPath}/homework/getFile?homeworkId=${homeworkId}" width="1000" height="600" allowfullscreen webkitallowfullscreen></iframe>
</div>
</body>
</html>