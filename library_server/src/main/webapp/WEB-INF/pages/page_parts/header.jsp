<%-- 
    Document   : header
    Created on : Oct 20, 2012, 4:34:22 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/form_submit.js" />"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/clickable_div.js" />"></script>

<div id="header"> 

    <img id="main_header_image" src="<%=request.getContextPath()%>/resources/img/header-image.png" />  

    <div class="header-div">
        
                <sec:authorize access="hasRole('ROLE_USER')">
                    <h2><%=SecurityContextHolder.getContext().getAuthentication().getName()%></h2>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <spring:message code="user.role.admin" /> <br/><br/>
                </sec:authorize>                   
       
        <a href="<spring:url value="?lang=en"/>"><img src="<c:url value="/resources/img/us-flag.jpg"/>" /></a> 
        <a href="<spring:url value="?lang=sk_SK"/>"><img src="<c:url value="/resources/img/slovak-flag.png"/>" /></a>
    </div>

    <div class="log_button">
        <sec:authorize access="hasRole('ROLE_USER')">
            <a href='${pageContext.request.contextPath}/user/logout/'><span><spring:message code="label.website.navigation.logout" /></span></a>
        </sec:authorize>
        <sec:authorize access="!hasRole('ROLE_USER')">
            <a href='${pageContext.request.contextPath}/user/login/'><spring:message code="label.website.navigation.login" /></a>
        </sec:authorize>        
    </div>



</div>

