<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <link href='http://fonts.googleapis.com/css?family=Bitter' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<c:url var="addToFavoritesUrl" value="/favorites/add?id=${book.id}"/>
<c:url var="downloadUrl" value="/book/content/download?id=${book.id}"/>
<c:url var="readUrl" value="/book/content/read?id=${book.id}"/>

<sec:authorize access="!hasRole('ROLE_ADMIN')">

    <form:form class="desktop" modelAttribute="book">

        <div class="section">Book</div>

        <c:if test="${type=='edit'}">
            <div class="menu">
                <c:if test="${isFavorite==false}">
                    <p><a href="${addToFavoritesUrl}">Add to Favorites</a></p>
                </c:if>

                <p><a href="${downloadUrl}">Download</a></p>
                <p><a href="${readUrl}">Read</a></p>
                <p class="error">${param.contentError}</p>
            </div>
        </c:if>

        <div class="form-elements">
            <form:label path="title">
                Title
                <form:input type="text" path="title" readonly="true"/>
            </form:label>
            <form:label path="author">
                Author
                <form:input type="text" path="author" readonly="true"/>
            </form:label>
            <form:label path="year">
                Year
                <form:input type="number" path="year" readonly="true"/>
            </form:label>
            <form:label path="genre.name">
                Genre
                <form:input type="text" path="genre.name" readonly="true"/>
            </form:label>
            <form:label path="description">
                Description
                <form:textarea path="description" readonly="true"/>
            </form:label>
        </div>

    </form:form>

</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">

    <form:form class="desktop" modelAttribute="book" method="POST" action="${saveUrl}"
               enctype="multipart/form-data">

        <div class="section">
            <c:choose>
                <c:when test="${type=='edit'}">
                    <c:url var="saveUrl" value="/book/edit?id=${book.id}"/>
                    Book
                </c:when>
                <c:when test="${type=='add'}">
                    <c:url var="saveUrl" value="/book/add"/>
                    new Book
                </c:when>
            </c:choose>
        </div>

        <c:if test="${type=='edit'}">
            <div class="menu">
                <c:if test="${isFavorite==false}">
                    <p><a href="${addToFavoritesUrl}">Add to Favorites</a></p>
                </c:if>

                <p><a href="${downloadUrl}">Download</a></p>
                <p><a href="${readUrl}">Read</a></p>
                <p class="error">${param.contentError}</p>
            </div>
        </c:if>

        <div class="form-elements">
            <form:label path="title">
                Title
                <form:input type="text" path="title"/>
            </form:label>
            <form:label path="author">
                Author
                <form:input type="text" path="author"/>
            </form:label>
            <form:label path="year">
                Year
                <form:input type="number" path="year" min="0" max="2050" step="1"/>
            </form:label>
            <form:label path="genre">
                Genre
                <form:select path="genre" required="required">
                    <c:forEach items="${genres}" var="genre">
                        <option value="${genre.id}"
                                <c:if test="${genre.id==book.genre.id}">selected</c:if>>${genre.name}</option>
                    </c:forEach>
                </form:select>
            </form:label>
            <form:label path="description">
                Description
                <form:textarea path="description"/>
            </form:label>
        </div>

        <div>
            <div class="file">
                <input type="file" name="file" accept=".pdf"><br/>
            </div>
            <input type="submit" value="Save"/>
        </div>

        <br/>
        <br/>

    </form:form>

</sec:authorize>

</body>
</html>