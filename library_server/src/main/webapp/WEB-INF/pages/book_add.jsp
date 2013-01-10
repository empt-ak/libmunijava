<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.book.add"/></title>
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
            <div id="faux"> <br />
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <form:form method="POST" action="${pageContext.request.contextPath}/book/save" commandName="bookDTO">                    
                        <fieldset>
                            <%-- todo formatovanie errov :] --%>
                            <legend><spring:message code="label.website.book.add.formheader" /></legend> 

                            <form:label path="title"><spring:message code="label.website.book.add.field.booktitle"/></form:label>
                            <form:input path="title" id="register_input"/> <form:errors path="title" id="error" element="div"/><br />

                            <form:label path="author"><spring:message code="label.website.book.add.field.booktauthor"/></form:label>
                            <form:input path="author" id="register_input"/><form:errors path="author" id="error" element="div"/><br/>


                            <form:label path="department"><spring:message code="label.website.book.add.field.bookdepartment"/></form:label>
                            <form:select path="department" id="register_input">
                                <form:option value="ADULT"><spring:message code="book.department.adult"/></form:option>
                                <form:option value="KIDS"><spring:message code="book.department.kids"/></form:option>
                                <form:option value="SCIENTIFIC"><spring:message code="book.department.scientific"/></form:option>
                            </form:select> <br/><br />

                            <label></label><input type="submit" value="<spring:message code="label.website.book.add.createbutton"/>" id="register_button"/>
                        </fieldset>
                    </form:form>
                </sec:authorize>
                <sec:authorize access="!hasRole('ROLE_ADMINISTRATOR')">
                    <h3><spring:message code="error.website.accessdenied"/></h3>
                </sec:authorize>
            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
