<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>User</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<div id="stylized" class="myform">
    <h1 style="alignment: center">Show users</h1>
    <c:if test="${not empty users}">
            <table class="zui-table">
                <thead>
                <tr>
                    <th>Full name</th>
                    <th>Email</th>
                    <th>Course</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
        <c:forEach items="${users}" var="user">
                <tr>
                    <td>
                            ${user.firstName} ${user.lastName}
                    </td>
                    <td>
                            ${user.email}
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/course/get?id=${user.course.id}" class="button"
                           role="button" tabindex="0">${user.course.title}</a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/get?id=${user.id}#" class="button"
                           role="button" tabindex="0">Details</a>
                    </td>
                </tr>
        </c:forEach>
                </tbody>
            </table>
    </c:if>
</div>

</body>
</html>
