<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course Details</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
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

