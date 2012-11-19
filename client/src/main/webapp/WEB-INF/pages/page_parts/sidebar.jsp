<%-- 
    Document   : sidebar
    Created on : Oct 20, 2012, 4:36:31 PM
    Author     : emptak
--%>

<%@page import="library.entity.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <div id="rightcolumn">
                <a><span><spring:message code="label.website.sidebar.languague" /></span></a> 
                <a href="${pageContext.request.contextPath}?lang=en"><img src="<c:url value="/resources/img/us-flag.jpg"/>" /></a> 
                | <a href="${pageContext.request.contextPath}?lang=sk_SK"><img src="<c:url value="/resources/img/slovak-flag.png"/>" /></a><br /><br />
                <!-- sidebar -->
<!--            	<div class="searchBox">
                <form action="${pageContext.request.contextPath}/search/" method="post">
                	<input type="text" placeholder="Zadajte text k hladaniu" autofocus />
                </form> <br />
                    <form action="${pageContext.request.contextPath}/user/login" method="post">
                        Login: <input type="text" name="username" size="15"/><br />
                        Heslo: <input type="password" name="password" size="15"/><br />
                        <input type="submit" />
                    </form>
                </div>-->
               <!-- <c:out value="${USER}" /> -->
               <a href="${pageContext.request.contextPath}/user/editprofile/${USER.username}"><span><spring:message code="label.website.sidebar.editProfile" /></span></a>
<!--                      prihlaseny ako:
                      obsah poslednej pozicky:
                      <a href="${pageContext.request.contextPath}/user/logout/">odhlasit sa </a>-->
                      
                    
     
                
                <div class="clear"></div>
                
            </div>