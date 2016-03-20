<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <title>Registration</title>
</head>
<body>

<c:url var="loginUrl" value="/login"/>

<form:form class="desktop" modelAttribute="user" method="POST" enctype="utf8">

    <div class="section">Registration Form</div>

    <div class="menu"><a href="${loginUrl}">Back to login</a></div>

    <br>

    <div class="form-elements">
        <form:label path="username">
            Username
            <form:input type="text" path="username" value=""/>
        </form:label>
        <form:label path="password">
            Password
            <form:input type="password" path="password" value=""/>
        </form:label>
        <form:label path="matchingPassword">
            Matching password
            <form:input type="password" path="matchingPassword" value=""/>
        </form:label>
    </div>

    <div>
        <form:errors element="div" class="error"/>
        <input type="submit" value="Submit">

    </div>

    <br/>
    <br/>

</form:form>

</body>
</html>