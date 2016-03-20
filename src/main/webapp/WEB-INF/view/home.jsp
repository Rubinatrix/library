<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>

<head>
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <title>Home</title>
</head>

<c:url var="loginUrl" value="/login"/>
<c:url var="registrationUrl" value="/registration"/>
<c:url var="logoutUrl" value="/logout"/>
<c:url var="libraryUrl" value="/book/list"/>
<c:url var="deleteImgUrl" value="/resources/img/delete.png"/>

<div class="desktop">

    <div class="user-info">
        Logged as <b>${username}</b>
        <sec:authorize access="isAuthenticated()">
            <p><a href="${logoutUrl}">Logout</a></p>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <p><a href="${loginUrl}">Login</a> or <a href="${registrationUrl}">Register new user</a></p>
        </sec:authorize>
    </div>

    <div class="header">
        Favorites
        <p><a href="${libraryUrl}">Go to library</a></p>
    </div>

    <div>

        <table class="book-list">
            <thead>
            <tr>
                <td>Title</td>
                <td>Author</td>
                <td>Genre</td>
                <td>Year</td>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userBooks}" var="book">
                <c:url var="deleteUrl" value="/favorites/delete?id=${book.id}"/>
                <c:url var="editUrl" value="/book/edit?id=${book.id}"/>
                <tr>
                    <td><a href="${editUrl}"><c:out value="${book.title}"/></a></td>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.genre.name}"/></td>
                    <td><c:out value="${book.year}"/></td>
                    <td class="button"><a href="${deleteUrl}"><img src="${deleteImgUrl}"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty userBooks}">
            <sec:authorize access="isAuthenticated()">
                No books available
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                To create your own book list, you have to login first
            </sec:authorize>
        </c:if>

    </div>

</div>

</body>

</html>
