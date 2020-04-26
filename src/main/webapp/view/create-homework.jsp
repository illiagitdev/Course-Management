<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Create Homework</title>
    <style>
        <%@include file="/view/css/style.css"%>
    </style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<form:form if="form" action="upload?course_id=${course_id}" modelAttribute="fileBucket" method="post" enctype="multipart/form-data">
    <form:input type="file" id="file" path="file"/>
    <div class="has-error">
        <form:errors path="file" class="help-inline"/>
    </div>
    <br />
    <input type="submit" class="button" value="Upload" />
</form:form>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
</body>
</html>