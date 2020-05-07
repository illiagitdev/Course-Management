<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Courses</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navig-bar.jsp"/>
<c:choose>
    <c:when test="${not empty courses}">
        <table class="zui-table myform">
            <thead>
            <tr>
                <th>Title</th>
                <th>Course Status</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courses}" var="course">
                <tr>
                    <td>
                        <c:out value="${course.title}"/>
                    </td>
                    <td>
                        <c:out value="${course.courseStatus}"/>
                    </td>
                    <td>
                        <a href="${contextPath}/course/get?id=${course.id}#" class="button"
                           role="button" tabindex="0">Show details</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>There is no courses, please contact admin.</p>
    </c:otherwise>
</c:choose>
</body>
</html>
