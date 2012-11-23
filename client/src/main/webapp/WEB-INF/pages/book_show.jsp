<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.index.titleAddBook"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
    </head>
    <body> 
        <!-- wrapper -->
        <div id="wrapper">
            <!-- header -->
            <%@include file="/WEB-INF/pages/page_parts/header.jsp" %>	

            <!-- navigation--> 
            <%@include file="/WEB-INF/pages/page_parts/navigation.jsp" %>       

            <!-- Begin Faux Columns -->
            <div id="faux">

                <table id="book_detail">
                    <thead>
                        <tr>
                            <td><spring:message code="label.website.book.add.field.booktitle" /></td>
                            <td><spring:message code="label.website.book.add.field.booktauthor" /></td>
                            <td><spring:message code="label.website.book.add.field.bookdepartment" /></td>
                            <td><spring:message code="label.website.book.edit.field.availability" /></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <c:choose>
                                <c:when test="${book.department == 'ADULT'}">
                                    <td><spring:message code="book.department.adult" /></td>
                                </c:when>
                                <c:when test="${book.department == 'KIDS'}">
                                    <td><spring:message code="book.department.kids" /></td>
                                </c:when>
                                <c:when test="${book.department == 'SCIENTIFIC'}">
                                    <td><spring:message code="book.department.scientific" /></td>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${book.bookStatus == 'AVAILABLE'}">
                                    <td><spring:message code="book.bookstatus.available" /></td>
                                </c:when>
                                <c:when test="${book.bookStatus == 'NOT_AVAILABLE'}">
                                    <td><spring:message code="book.bookstatus.unavailable" /></td>
                                </c:when>
                            </c:choose>
                        </tr>                   
                    </tbody>
                </table>
                <ul id="book_options">
                    <li><a href="${pageContext.request.contextPath}/book/category/title/${book.title}"><spring:message code="label.website.book.show.similartitle" /></a> </li>
                    <li><a href="${pageContext.request.contextPath}/book/category/author/${book.author}"><spring:message code="label.website.book.show.similarauthor" /></a> </li>
                    <li><a href="${pageContext.request.contextPath}/book/category/department/${book.department}"><spring:message code="label.website.book.show.similardepartment" /></a></li>
                </ul>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
