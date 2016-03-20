<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <title>Library</title>
</head>
<body>

<c:url var="loginUrl" value="/login"/>
<c:url var="registrationUrl" value="/registration"/>
<c:url var="logoutUrl" value="/logout"/>
<c:url var="homeUrl" value="/"/>
<c:url var="addUrl" value="/book/add"/>
<c:url var="listUrl" value="/book/list"/>
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
        Library
        <p><a href="${homeUrl}">Go home</a></p>
    </div>

    <table>

        <td class="navigation-panel">

            <ul class="navigation">
                <li><b><a href="${listUrl}"><c:out value="ALL GENRES"/></a></b></li>
                <br>
                <c:forEach items="${genres}" var="genre">
                    <c:url var="genreUrl" value="/book/list?gid=${genre.id}"/>
                    <li <c:if test="${genre.id==param.gid}">class="selected" </c:if>>
                        <a href="${genreUrl}">
                            <c:out value="${genre.name}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>

        </td>

        <td class="search-panel">

            <form name="search_form" method="GET" enctype="utf8">
                <input type="text" name="search" value="" placeholder="Enter book title on author name" size="110"/>
                <input type="submit" value="Search"/>
            </form>

            <div class="info">

                <c:if test="${!empty param.search}">
                    <p>Search : ${param.search}</p>
                </c:if>

                <p>Books founded: ${booksQuantity}</p>

            </div>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="small-menu">
                    <a href="${addUrl}">Add book to library</a>
                </div>
            </sec:authorize>

            <br/><br/>

            <table class="book-list">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Year</th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <th></th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${books}" var="book">
                    <c:url var="deleteUrl" value="/book/delete?id=${book.id}"/>
                    <c:url var="editUrl" value="/book/edit?id=${book.id}"/>
                    <tr>
                        <td><a href="${editUrl}"><c:out value="${book.title}"/></a></td>
                        <td><c:out value="${book.author}"/></td>
                        <td><c:out value="${book.year}"/></td>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td class="button"><a href="${deleteUrl}"><img src="${deleteImgUrl}"/></a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </td>

    </table>

</div>

</body>
</html>