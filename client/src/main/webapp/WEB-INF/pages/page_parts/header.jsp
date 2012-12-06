<%-- 
    Document   : header
    Created on : Oct 20, 2012, 4:34:22 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/form_submit.js" />"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/clickable_div.js" />"></script>

<div id="header"> 

    <img id="main_header_image" src="<%=request.getContextPath()%>/resources/img/header-image.png" />  

    <div class="header-div">
        <c:choose>
            <c:when test="${USER != null}">
                <h3>${USER.realName}</h3>
                <c:choose>
                    <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                         <spring:message code="user.role.admin" /> <br/><br/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h1>&nbsp;</h1> <br />
            </c:otherwise>
        </c:choose>
        <a href="<spring:url value="?lang=en"/>"><img src="<c:url value="/resources/img/us-flag.jpg"/>" /></a> 
        <a href="<spring:url value="?lang=sk_SK"/>"><img src="<c:url value="/resources/img/slovak-flag.png"/>" /></a>
    </div>

    <div class="log_button">
        <c:choose>
            <c:when test="${USER == null}">
                <a href='${pageContext.request.contextPath}/user/login/'><spring:message code="label.website.navigation.login" /></a>
            </c:when>
            <c:otherwise>
                <a href='${pageContext.request.contextPath}/user/logout/'><span><spring:message code="label.website.navigation.logout" /></span></a>
             </c:otherwise>
         </c:choose>
    </div>



</div>

