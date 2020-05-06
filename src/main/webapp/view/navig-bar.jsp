<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authentication var="username" property="principal.username"/>
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
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.request.contextPath}/course/createCourses">Create Course</a>
            </security:authorize>
            <a href="${pageContext.request.contextPath}/course/findCourseView">Find Course</a>
        </div>
    </div>
    <div class="dropdown">
        <button class="dropbtn">User
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.request.contextPath}/user/create">Create User</a>
            </security:authorize>
            <a href="${pageContext.request.contextPath}/user/showUsers">Show users</a>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.request.contextPath}/user/findUsers">Find users</a>
            </security:authorize>
        </div>
    </div>

    <div class="dropdown" style="float: right">
        <button class="dropbtn">${username}
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
            <a href="${pageContext.request.contextPath}/user/userDetails/${username}">Users details</a>
        </div>
    </div>
    <div>

    </div>
</div>
</body>
</html>

