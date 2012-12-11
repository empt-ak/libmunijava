<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.user.profile"/></title>
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
            
            <div id="faux">
                
                </br>
                
                <h2><spring:message code="label.website.service.available.wsdl"/></h2> </br></br>

                <a href="<spring:url value="http://localhost:8080/pa165/services/wsdl/BookWebService?wsdl" />">BookWebService</a></br></br>
                <a href="<spring:url value="http://localhost:8080/pa165/services/wsdl/UserWebService?wsdl" />">UserWebService</a></br></br>
            
            </div>

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>