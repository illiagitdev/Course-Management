<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<security:authentication var="username" property="principal.username"/>
<html>
<head>
    <title>User Details</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navig-bar.jsp"/>
<c:if test="${not empty userDetails}">
    <table class="zui-table myform">
        <h2>User Details</h2>
        <thead>
        <tr>
            <th>Full name</th>
            <th>Email</th>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <th>User role</th>
                <th>User status</th>
            </security:authorize>
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
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <td>
                        ${userDetails.userRole}
                </td>
                <td>
                        ${userDetails.status}
                </td>
            </security:authorize>
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
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <a href="${contextPath}/user/delete?id=${userDetails.id}#" class="button"
           role="button" tabindex="0">Delete</a>
    </security:authorize>
</c:if>
</body>
</html>
