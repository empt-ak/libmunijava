<%-- 
    Document   : navigation
    Created on : Oct 20, 2012, 4:35:47 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<div class='cssmenu'>
    <ul> 
        <li class='active'><a href='${pageContext.request.contextPath}/'><span><spring:message code="label.website.navigation.index" /></span></a></li>   
        <li><a href='${pageContext.request.contextPath}/book/'><span><spring:message code="label.website.navigation.books" /></span></a>            
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <ul>
                    <li><a href='${pageContext.request.contextPath}/book/save'><span><spring:message code="label.website.navigation.books.add" /></span></a></li>                       
                </ul>
            </sec:authorize>            
        </li> 

        <sec:authorize access="hasRole('ROLE_USER')">
            <li><a href='${pageContext.request.contextPath}/ticket/show/mytickets/'><span><spring:message code="label.website.navigation.tickets" /></span></a>
<!--                <ul>
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <li><a href='${pageContext.request.contextPath}/ticket/editforuser/'><span><spring:message code="label.website.navigation.ticket.edit" /></span></a></li>
                    </sec:authorize>
                </ul>-->
            </li> 
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
            <li class="active"><a href="${pageContext.request.contextPath}/user/"><span><spring:message code="label.website.navigation.users" /></span></a></li>
        </sec:authorize>


        <sec:authorize access="!hasRole('ROLE_USER')">
            <li class='active'><a href='${pageContext.request.contextPath}/user/register/'><span><spring:message code="label.website.navigation.register" /></span></a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
            <li class='active'><a href="${pageContext.request.contextPath}/user/editprofile/"><span><spring:message code="label.website.sidebar.editProfile" /></span></a></li>
        </sec:authorize>               

        <li class='active'><a href="<spring:url value="/service/"/>"><span><spring:message code="label.website.navigation.services" /></span></a></li>
    </ul> 
</div>
