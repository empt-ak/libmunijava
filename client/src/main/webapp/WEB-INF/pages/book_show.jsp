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
        <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>

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
                
                ${book}
                    ${error}
                <table>
                    <tr>
                        <td>nazov knihy</td>
                        <td>${book.title}</td>
                        <td><a href="${pageContext.request.contextPath}/book/category/title/${book.title}">zobrazit tituly s podobnym nazvom</a></td>
                    </tr>
                    <tr>
                        <td>autoor</td>
                        <td>${book.author}</td>
                        <td><a href="${pageContext.request.contextPath}/book/category/author/${book.author}">zobrazit knihy od tohto autora</a></td>
                    </tr>
                    <tr>
                        <td>oddelenie</td>
                        <td>${book.department}</td>
                        <td><a href="${pageContext.request.contextPath}/book/category/department/${book.department}"> knihy z tohto oddelenia</a></td>
                    </tr>
                    <tr>
                        <td>dostupnost</td>
                        <td colspan="2">ee</td>
                    </tr>
                </table>

                <div class="clear"></div>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
