<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>User Details</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/webapp/WEB-INF/view/navig-bar.jsp"/>
<c:if test="${not empty userDetails}">
    <table class="zui-table myform">
        <h2>User Details</h2>
        <thead>
        <tr>
            <th>Full name</th>
            <th>Email</th>
            <th>User role</th>
            <th>User status</th>
            <th>Course</th>
            <th>Solutions</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                    ${userDetails.firstName} ${userDetails.lastName}
            </td>
            <td>
                    ${userDetails.email}
            </td>
            <td>
                    ${userDetails.userRole}
            </td>
            <td>
                    ${userDetails.status}
            </td>
            <td>
                <a href="${contextPath}/course/get?id=${userDetails.course.id}" class="button"
                   role="button" tabindex="0">${userDetails.course.title}</a>
            </td>
            <td>
                <ul>
                    <c:choose>
                        <c:when test="${not empty userDetails.solutions}">
                            <c:forEach items="${userDetails.solutions}" var="solution">
                                <li><a href="${contextPath}/solution/get?id=${solution.id}" class="button"
                                       role="button"
                                       tabindex="0">${solution.homework.title}</a><br>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>No solutions yet</p>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </td>
        </tr>
        </tbody>
    </table>
    <a href="${contextPath}/user/updateUser?id=${userDetails.id}#" class="button"
       role="button" tabindex="0">Update</a>
</c:if>
</body>
</html>
