<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Find user</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navig-bar.jsp"/>
<div align="center">
    <form id="form" name="form" action="find">
        <h2>Find user by email</h2>
        <label for="email">User email
            <span class="small">Email</span>
        </label>
        <input title="email" type="text" name="email" id="email"/>
        <button type="submit" class="button">Find</button>
    </form>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
</div>
</body>
</html>
