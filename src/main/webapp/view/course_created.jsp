<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course Created</title>
    <style>
        <%@include file="/view/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<p>Course with title ${course_title} created!</p>
</body>
</html>
