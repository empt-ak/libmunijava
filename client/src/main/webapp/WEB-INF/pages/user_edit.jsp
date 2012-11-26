<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.user.details"/></title>
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
                <c:choose>
                    <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                        <form:form method="POST" action="${pageContext.request.contextPath}/user/edit/" commandName="userDTO">                    
                            <fieldset>
                                <%-- todo formatovanie errov :] --%>
                                <legend><spring:message code="label.website.user.editprofile.formheader" /></legend>                    
                                <form:label path="username"><spring:message code="label.website.user.register.field.username"/></form:label>
                                <form:input path="username" id="register_input" readonly="true"/> <form:errors path="username" id="error" element="div"/><br />

                                <form:label path="realName"><spring:message code="label.website.user.register.field.realname"/></form:label>
                                <form:input path="realName" id="register_input"/><form:errors path="realName" id="error" element="div"/><br/>

                                <form:label path="password"><spring:message code="label.website.user.register.field.password"/></form:label>
                                <form:password path="password" id="register_input"/><form:errors path="password" id ="error" element="div"/><br />

                                <form:label path="systemRole"><spring:message code="label.website.user.register.field.systemRole"/></form:label>
                                <form:select path="systemRole" id="register_input">
                                    <form:option value="ADMINISTRATOR"><spring:message code="user.role.admin"/></form:option>
                                    <form:option value="USER"><spring:message code="user.role.user"/></form:option>                                        
                                </form:select><form:errors path="systemRole"/><br/>

                                <form:hidden path="userID" />

                                <label></label><input type="submit" value="<spring:message code="label.website.user.editprofile.editbutton"/>" id="register_button"/>
                            </fieldset>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="error.website.accessdenied"/>
                    </c:otherwise>
                </c:choose>
                    
            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
