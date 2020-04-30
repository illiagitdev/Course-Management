<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find user</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
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
