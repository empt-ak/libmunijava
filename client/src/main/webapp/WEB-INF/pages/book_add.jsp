<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Pridanie knihy do systému</title>
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
                <!-- content -->
                <div id="leftcolumn">
                    <c:choose>
                        <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                            <form:form method="POST" action="${pageContext.request.contextPath}/book/save" commandName="bookDTO">                    
                                <fieldset>
                                    <%-- todo formatovanie errov :] --%>
                                    <legend><spring:message code="label.website.book.add.formheader" /></legend>                    
                                    
                                    <spring:message code="label.website.book.add.field.booktitle"/>
                                    <form:label path="title"><spring:message code="label.website.book.add.field.booktitle"/></form:label>
                                    <form:input path="title" id="register_input"/> <form:errors path="title"/><br />

                                    <form:label path="author"><spring:message code="label.website.book.add.field.booktauthor"/></form:label>
                                    <form:input path="author" id="register_input"/><form:errors path="author"/><br/>


                                    <form:label path="department"><spring:message code="label.website.book.add.field.bookdepartment"/></form:label>
                                    <form:select path="department" id="register_input">
                                        <form:option value="ADULT"><spring:message code="book.department.adult"/></form:option>
                                        <form:option value="KIDS"><spring:message code="book.department.kids"/></form:option>
                                        <form:option value="SCIENTIFIC"><spring:message code="book.department.scientific"/></form:option>
                                    </form:select> <br/><br />

                                    <label></label><input type="submit" value="<spring:message code="label.website.book.add.createbutton"/>" id="register_button"/>
                                </fieldset>
                            </form:form>
                         </c:when>
                        <c:otherwise>
                            <h3><spring:message code="error.website.accessdenied"/></h3>
                        </c:otherwise>
                    </c:choose>



                    <div class="clear"></div>
                </div>           	 

                <!-- sidebar -->
                <%@include file="/WEB-INF/pages/page_parts/sidebar.jsp" %>           
            </div>	

            <!-- footer -->z
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
