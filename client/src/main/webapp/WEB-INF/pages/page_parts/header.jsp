<%-- 
    Document   : header
    Created on : Oct 20, 2012, 4:34:22 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/form_submit.js" />"></script>

<div id="header"> 
    <!--            <div align="left">
                    <h1><a><span><spring:message code="label.website.header.headerLibrary" /></span></a></h1>
                </div>      -->
    
    <img id="main_header_image" src="<%=request.getContextPath()%>/resources/img/header-image.png" />  

    <div class="header-div">
        <c:choose>
            <c:when test="${USER != null}">
                <h3>${USER.realName}</h3> <br />
            </c:when>
        </c:choose>
        <a href="${pageContext.request.contextPath}?lang=en"><img src="<c:url value="/resources/img/us-flag.jpg"/>" /></a> 
        <a href="${pageContext.request.contextPath}?lang=sk_SK"><img src="<c:url value="/resources/img/slovak-flag.png"/>" /></a>
    </div>
</div>

