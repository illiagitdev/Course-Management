<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Create User</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>

<div id="stylized" class="myform">
    <form:form id="form" modelAttribute="user" name="form" method="post" action="create">
        <h1 style="alignment: center" >Create user form</h1>

        <label>User first name
            <span class="small">First name</span>
        </label>
        <form:input path="firstName" type="text" name="firstName" id="firstName" />
        <form:errors path="firstName" cssClass="error"/>

        <label>User last name
            <span class="small">Last name</span>
        </label>
        <form:input path="lastName" type="text" name="lastName" id="lastName" />
        <form:errors path="lastName" cssClass="error"/>

        <label>User email
            <span class="small">Email</span>
        </label>
        <form:input path="email" type="email" name="email" id="email" />
        <form:errors path="email" cssClass="error"/>

        <label for="courseName">Assign course
            <span class="small">Select course</span>
        </label>
        <select name="courseName" id="courseName" title="courseName">
            <c:forEach items="${courses}" var="course">
                <option>${course}</option>
            </c:forEach>
        </select>

        <button type="submit" class="button">Create</button>
        <div class="spacer"></div>
    </form:form>
</div>

</body>
</html>
