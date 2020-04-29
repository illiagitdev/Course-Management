<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style><%@include file="/view/css/style.css"%></style>
</head>
<body>
<c:import url="/view/navig-bar.jsp"/>
<br><h2>Oops... something going wrong! ${error}</h2>
</body>
</html>
