<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Preview Homework</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navig-bar.jsp"/>
<div align="center">
    <h3>${pageContext.request.contextPath}/homework/getFile?homeworkId=${homeworkId}"</h3>
    <iframe src="${contextPath}/homework/getFile?homeworkId=${homeworkId}" width="1000" height="600" allowfullscreen webkitallowfullscreen></iframe>
</div>
</body>
</html>