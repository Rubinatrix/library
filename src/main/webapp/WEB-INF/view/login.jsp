<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <title>Login</title>
</head>

<body>

    <c:url var="registrationUrl" value="/registration"/>

    <form class="desktop" action="login" method="POST">

        <div class="section">Login</div>

        <div class="menu"><a href="${registrationUrl}">Register new user</a></div>

        <br>

        <div class="form-elements">
            <label>
                Username
                <input type="text" id="username" name="username" placeholder="username" value="${user.username}" required="true"/>
            </label>
            <label>
                Password
                <input type="password" id="password" name="password" placeholder="password" value="${user.password}" required="true"/>
            </label>

        </div>

        <div class="button-section">
            <span class="file">
			<input type="checkbox" name="_spring_security_remember_me">Remember me
            </span>
            <input type="submit" value="Login">
        </div>

        <br/>
        <br/>

    </form>

</body>

</html>
