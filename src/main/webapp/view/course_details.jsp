<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <table class="zui-table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Course Status</th>
            <th>Students</th>
            <th>Homeworks</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="${course.title}"/>
                </td>
                <td>
                    <c:out value="${course.courseStatus}"/>
                </td>
                <td>
                    <ul>
                        <c:choose>
                            <c:when test="${not empty course.users}">
                                <c:forEach items="${course.users}" var="user">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/user/get?id=${user.id}#" class="button"
                                           role="button" tabindex="0">${user.firstName} ${user.lastName}</a>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>No students yet!</p>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </td>
                <td>
                    <ul>
                        <c:choose>
                            <c:when test="${not empty course.homework}">
                                <c:forEach items="${course.homework}" var="homework">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/homework/get?id=${homework.id}#" class="button"
                                           role="button" tabindex="0">${homework.title}</a>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>No homeworks yet!</p>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>
