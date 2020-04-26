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
        <c:forEach items="${users}" var="user">
            <table class="zui-table">
                <tbody>
                <tr>
                    <td>
                            ${user.firstName}
                    </td>
                    <td>
                            ${user.lastName}
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/get?id=${user.id}#" class="button"
                           role="button" tabindex="0">Details</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
