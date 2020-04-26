<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>User Details</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<c:if test="${not empty userDetails}">
    <table class="zui-table myform">
        <h2>User Details</h2>
        <tbody>

        <tr>
            <td>First name</td>
            <td>${userDetails.firstName}</td>
        </tr>
        <tr>
            <td>Last name</td>
            <td>${userDetails.lastName}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${userDetails.email}</td>
        </tr>
        <tr>
            <td>Role</td>
            <td>${userDetails.userRole}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>${userDetails.status}</td>
        </tr>
        <tr>
            <td>Course</td>
            <td>${userDetails.course.title}</td>
        </tr>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/user/updateUser?id=${userDetails.id}#" class="button"
       role="button" tabindex="0">Update</a>
</c:if>
</body>
</html>
