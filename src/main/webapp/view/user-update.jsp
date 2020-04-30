<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Update User</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>

<div id="stylized" class="myform">
    <h1 style="alignment: center">Update user form</h1>
    <c:if test="${not empty userDetails}">
        <form:form id="form" modelAttribute="user" name="form" method="post" action="updateUser?id=${userDetails.id}">

            <form:label path="firstName" for="firstName">User first name
                <span class="small">First name</span>
            </form:label>
            <input type="text" name="firstName" id="firstName" value="${userDetails.firstName}"/>
            <form:errors path="firstName" cssClass="error"/>

            <form:label path="lastName" for="lastName">User last name
                <span class="small">Last name</span>
            </form:label>
            <input type="text" name="lastName" id="lastName" value="${userDetails.lastName}"/>
            <form:errors path="lastName" cssClass="error"/>

            <form:label path="email" for="email">User email
                <span class="small">Email</span>
            </form:label>
            <input type="email" name="email" id="email" value="${userDetails.email}"/>
            <form:errors path="email" cssClass="error"/>

            <label for="courseName">Change course
                <span class="small" style="color: red">${userDetails.course.title}</span>
            </label>
            <select name="courseName" id="courseName" title="courseName">
                <c:forEach items="${courses}" var="course">
                    <option>${course}</option>
                </c:forEach>
            </select>

            <button type="submit" class="button">Update</button>
            <div class="spacer"></div>
        </form:form>
    </c:if>
</div>

</body>
</html>
