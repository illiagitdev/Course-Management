<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Course Management</title>
</head>
<body>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/">Home</a>
    <div class="dropdown">
        <button class="dropbtn">Course
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/course/showCourses">Show Courses</a>
            <a href="${pageContext.request.contextPath}/course/createCourses">Create Course</a>
            <a href="${pageContext.request.contextPath}/course/findCourseView">Find Course</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">User
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/user/create">Create User</a>
            <a href="${pageContext.request.contextPath}/user/showUsers">Show users</a>
            <a href="${pageContext.request.contextPath}/user/findUsers">Find users</a>
        </div>
    </div>
</div></body>
</html>

