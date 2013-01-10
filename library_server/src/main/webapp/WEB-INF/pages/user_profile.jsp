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

            <!-- Begin Faux Columns -->
            <div id="faux"> 
                <br />
                <sec:authorize access="hasRole('ROLE_USER')">
                    <form:form method="POST" action="${pageContext.request.contextPath}/user/editprofile/" commandName="userDTO">                    
                        <fieldset>
                            <%-- todo formatovanie errov :] --%>
                            <legend><spring:message code="label.website.user.editprofile.formheader" /></legend>                    
                            <form:label path="username"><spring:message code="label.website.user.register.field.username"/></form:label>
                            <form:input path="username" id="register_input" readonly="true"/> <form:errors path="username"/><br />

                            <form:label path="realName"><spring:message code="label.website.user.register.field.realname"/></form:label>
                            <form:input path="realName" id="register_input"/><form:errors path="realName"/><br/>

                            <form:label path="password"><spring:message code="label.website.user.register.field.password"/></form:label>
                            <form:password path="password" id="register_input"/><form:errors path="password"/><br />
                            <form:hidden path="userID" />
                            <form:hidden path="systemRole"/>
                            <label></label><input type="submit" value="<spring:message code="label.website.user.editprofile.editbutton"/>" id="register_button"/>
                        </fieldset>
                    </form:form>
                </sec:authorize>
                <sec:authorize access="!hasRole('ROLE_USER')">
                    <spring:message code="error.website.accessdenied"/>
                </sec:authorize>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
