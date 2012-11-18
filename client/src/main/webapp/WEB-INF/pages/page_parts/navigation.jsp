<%-- 
    Document   : navigation
    Created on : Oct 20, 2012, 4:35:47 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class='cssmenu'>
    <ul> 
        <li class='active'><a href='${pageContext.request.contextPath}/'><span><spring:message code="label.website.navigation.index" /></span></a></li>   
        <li><a href='${pageContext.request.contextPath}/book/'><span><spring:message code="label.website.navigation.books" /></span></a>
            <c:choose>
                <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                    <ul>
                        <li><a href='${pageContext.request.contextPath}/book/save'><span><spring:message code="label.website.navigation.books.add" /></span></a></li>                       
                    </ul>
                </c:when>
            </c:choose>
        </li> 
        <c:choose>
            <c:when test="${USER != null}">
                <li><a href='#'><span><spring:message code="label.website.navigation.tickets" /></span></a>
                    <ul>
                        <li><a href='${pageContext.request.contextPath}/ticket/create/user/${USER.userID}'><span><spring:message code="label.website.navigation.tickets.create" /></span></a></li>
                            <li><a href='${pageContext.request.contextPath}/ticket/show/mytickets/'><span><spring:message code="label.website.navigation.displayBorrowings" /></span></a></li>
                    </ul>
                </li> 
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                <li class="active"><a href="${pageContext.request.contextPath}/user/"><span><spring:message code="label.website.navigation.users" /></span></a></li>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${USER == null}">
                <li class='active'><a href='${pageContext.request.contextPath}/user/register/'><span><spring:message code="label.website.navigation.register" /></span></a></li>
                <li class='active'><a href='${pageContext.request.contextPath}/user/login/'><span><spring:message code="label.website.navigation.login" /></span></a></li>
            </c:when>
            <c:otherwise>
                <li class='active'><a href="${pageContext.request.contextPath}/user/editprofile/${USER.username}"><span><spring:message code="label.website.sidebar.editProfile" /></span></a></li>
                <li class='active'><a href='${pageContext.request.contextPath}/user/logout/'><span><spring:message code="label.website.navigation.logout" /></span></a></li>
            </c:otherwise>                
        </c:choose>                
    </ul> 
</div>
