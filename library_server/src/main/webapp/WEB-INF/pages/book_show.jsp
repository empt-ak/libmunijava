<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.book.show"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script>

                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                function confirmDelete(id, bookTitle, author)
                {
                    if (confirm("<spring:message code="confirm.delete.entry" /> " + id + ", \n<spring:message code="book.book" />: " + bookTitle + " <spring:message code="book.writtenby" /> \"" + author + "\" ?"))
                    {
                        document.location = "${pageContext.request.contextPath}/book/delete/" + id;
                    }
                    else
                    {
                        //document.location = "/meh";
                    }
                }
                </sec:authorize>

        </script>
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
                            <th><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th><spring:message code="label.website.book.edit.field.availability" /></th>
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
                    <tfoot>
                        <tr>
                            <td> <a href="${pageContext.request.contextPath}/book/category/title/${book.title}"><spring:message code="label.website.book.show.similartitle" /></a></td>
                            <td><a href="${pageContext.request.contextPath}/book/category/author/${book.author}"><spring:message code="label.website.book.show.similarauthor" /></a></td>
                            <td><a href="${pageContext.request.contextPath}/book/category/department/${book.department}"><spring:message code="label.website.book.show.similardepartment" /></a></td>

                                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                    <td style="padding-left: 20px"><a href="${pageContext.request.contextPath}/book/edit/${book.bookID}" title="<spring:message code="label.website.book.list.tooltip.edit" />"><img src="<c:url value="/resources/img/icons20x20/96.png" />" alt="<spring:message code="label.website.book.add.updatebutton" />" /></a>
                                    <a href="#" onClick="confirmDelete(${book.bookID},'${book.title}','${book.author}')" title="<spring:message code="label.website.book.list.tooltip.delete" />"><img src="<c:url value="/resources/img/icons20x20/33.png" />" alt="<spring:message code="label.website.book.list.deletebook" />" /></a></td>
                                    </sec:authorize>
                                <sec:authorize access="!hasRole('ROLE_ADMINISTRATOR')">
                                    <td></td>
                                </sec:authorize>

                        </tr>
                    </tfoot>
                </table>
                <!--                <ul id="book_options">
                                    <li><a href="${pageContext.request.contextPath}/book/category/title/${book.title}"><spring:message code="label.website.book.show.similartitle" /></a> </li>
                                    <li><a href="${pageContext.request.contextPath}/book/category/author/${book.author}"><spring:message code="label.website.book.show.similarauthor" /></a> </li>
                                    <li><a href="${pageContext.request.contextPath}/book/category/department/${book.department}"><spring:message code="label.website.book.show.similardepartment" /></a></li>
                                </ul>-->

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
