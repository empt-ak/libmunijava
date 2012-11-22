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
                        <td>nazov knihy</td>
                        <td>autor</td>
                        <td>oddelenie</td>
                        <td>dostupnost</td>
                        </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.department}</td>
                        <td>ee</td>
                    </tr>
                   
                    </tbody>
                </table>
                    <ul id="book_options">
                       <li> <a href="${pageContext.request.contextPath}/book/category/title/${book.title}">zobrazit tituly s podobnym nazvom</a> </li>
                       <li>  <a href="${pageContext.request.contextPath}/book/category/author/${book.author}">zobrazit knihy od tohto autora</a> </li>
                       <li>  <a href="${pageContext.request.contextPath}/book/category/department/${book.department}"> knihy z tohto oddelenia</a></li>
                    </ul>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
