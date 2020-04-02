<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Course</title>
    <style>
        <%@include file="/view/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<form method="post" action="createCourse">
    <table>
        <tbody>
        <tr>
            <td>
                <p>Title</p>
            </td>
            <td>
                <input type="text" name="title">
            </td>
        </tr>
        <tr>
            <td>
                <p>Course Status</p>
            </td>
            <td>
                <select name="Course_status">
                    <c:forEach items="${courseStatuses}" var="courseStatus">
                        <option>${courseStatus}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
    <button type="submit" class="button">Create</button>
</form>
<c:if test="${not empty errorMassage}">
    <c:forEach items="${errorMessage.errors}" var="error">
        <p style="color: red">${error}</p>
    </c:forEach>
</c:if>
</body>
</html>
