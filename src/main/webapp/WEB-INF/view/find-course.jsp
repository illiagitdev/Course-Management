<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Course Details</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navig-bar.jsp"/>
<div align="center">
    <form id="form" name="form" method="get" action="findCourse">
        <h2>Enter course title</h2>
        <label for="courseName">Course title
            <span class="small">Title</span>
        </label>
        <input title="courseName" type="text" name="courseName" id="courseName"/>
        <button type="submit" class="button">Find</button>
    </form>
</div>
</body>
</html>

