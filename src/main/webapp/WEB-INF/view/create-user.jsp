<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <form:form id="form" modelAttribute="userForm" name="form" method="post" action="create">
        <h1 style="alignment: center" >Create user form</h1>
        <spring:bind path="firstName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:label path="firstName">User first name
                <span class="small">First name</span>
            </form:label>
            <form:input path="firstName" type="text" name="firstName" id="firstName"  placeholder="first name"
                        autofocus="true"/>
            <form:errors path="firstName" cssClass="error"/>
            </div>
        </spring:bind>

        <spring:bind path="lastName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="lastName">User last name
                    <span class="small">Last name</span>
                </form:label>
                <form:input path="lastName" type="text" name="lastName" id="lastName" placeholder="last name"
                            autofocus="true"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
        </spring:bind>

        <span>${message}</span>
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="email">User email
                    <span class="small">Email</span>
                </form:label>
                <form:input path="email" type="email" name="email" id="email" placeholder="Username(use email as user name)"
                            autofocus="true"/>
                <form:errors path="email" cssClass="error"/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="password">Password
                    <span class="small">Password</span>
                </form:label>
                <form:input type="password" path="password" class="form-control" placeholder="Password" />
                <form:errors path="password" />
            </div>
        </spring:bind>

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
