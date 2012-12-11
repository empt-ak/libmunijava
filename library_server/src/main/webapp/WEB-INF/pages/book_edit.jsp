<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.book.edit"/></title>
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
                <c:choose>
                    <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                        <form:form method="POST" action="${pageContext.request.contextPath}/book/edit/" commandName="bookDTO">                    
                            <fieldset>
                                <%-- todo formatovanie errov :] --%>
                                <legend><spring:message code="label.website.book.add.formheader" /></legend>                    
                                <form:label path="title"><spring:message code="label.website.book.add.field.booktitle"/></form:label>
                                <form:input path="title" id="register_input"/> <form:errors path="title" id="error" element="div"/><br />
                                <form:hidden path="bookID" />

                                <form:label path="author"><spring:message code="label.website.book.add.field.booktauthor"/></form:label>
                                <form:input path="author" id="register_input"/><form:errors path="author" id="error" element="div"/><br/>


                                <form:label path="department"><spring:message code="label.website.book.add.field.bookdepartment"/></form:label>
                                <form:select path="department" id="register_input">
                                    <form:option value="ADULT"><spring:message code="book.department.adult"/></form:option>
                                    <form:option value="KIDS"><spring:message code="book.department.kids"/></form:option>
                                    <form:option value="SCIENTIFIC"><spring:message code="book.department.scientific"/></form:option>
                                </form:select> <br/>

                                <form:label path="bookStatus"><spring:message code="label.website.book.add.field.bookstatus"/></form:label>
                                <form:select path="bookStatus" id="register_input">
                                    <form:option value="AVAILABLE"><spring:message code="book.bookstatus.available"/></form:option>
                                    <form:option value="NOT_AVAILABLE"><spring:message code="book.bookstatus.unavailable"/></form:option>                                        
                                </form:select><br /><br />

                                <label></label><input type="submit" value="<spring:message code="label.website.book.add.updatebutton"/>" id="register_button"/>
                            </fieldset>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <h3><spring:message code="error.website.accessdenied"/></h3>
                    </c:otherwise>
                </c:choose>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
