<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.index.bookList"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />
        <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/loan_slider.js" />"></script>
       
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
                
                <a class="pridat" href='${pageContext.request.contextPath}/ticket/create/'><span><spring:message code="label.website.navigation.tickets.create" /></span></a>
                
                <div id="tickets">
                <c:choose>
                    <c:when test="${tickets.size() > 0}">
                        <c:forEach var="ticket" items="${tickets}">
                            <!-- hlavicka pozicky -->
                            <div class="show" style="background-color: #d7e291; border-style: dotted; border-width: 1px;">
                                <table width="100%">
                                    <tr>
                                        <td><spring:message code="label.website.ticket.list.field.ticketNumber"/> ${ticket.ticketID}</td>
                                        <td><spring:message code="label.website.ticket.list.field.borrowTime"/> ${ticket.borrowTime.dayOfMonth}.${ticket.borrowTime.monthOfYear}.${ticket.borrowTime.year}</td>
                                        <td><spring:message code="label.website.ticket.list.field.returnDate"/> ${ticket.dueTime.dayOfMonth}.${ticket.dueTime.monthOfYear}.${ticket.dueTime.year}</td>  
                                        <td><img src="<c:url value="/resources/img/arrow_down.gif" />" /></td>
                                    </tr>
                                </table>
                            </div>

                            <!-- telo pozicky -->
                            <div class="slidingDiv">
                                <table>
                                    <tr>
                                        <td><spring:message code ="label.website.ticket.add.field.bookTitle"/></td>
                                        <td><spring:message code ="label.website.ticket.add.field.author"/></td>
                                        <td><spring:message code ="label.website.ticket.add.field.status"/></td>
                                         <c:if test="${USER.systemRole == 'ADMINISTRATOR'}"> 
                                            <td></td>
                                         </c:if>
                                        
                                    </tr>
                                    <c:forEach var="ticketItem" items="${ticket.ticketItems}">
                                        <tr>
                                            <td>${ticketItem.book.title}</td>
                                            <td>${ticketItem.book.author}</td>
                                            <td>${ticketItem.ticketItemStatus}</td>
                                            <c:choose>
                                                <c:when test="${ticketItem.ticketItemStatus == 'RETURNED' || ticketItem.ticketItemStatus == 'RETURNED_DAMAGED'}">
                                                    <td></td>
                                                </c:when>
                                                <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                                                    <td><a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}/ticketitem/${ticketItem.ticketItemID}">Vratit knihu</a></td>
                                                </c:when>
                                            </c:choose>

                                        </tr>
                                    </c:forEach>
                                </table><br />   
                                <c:choose>
                                    <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                                        <a href="${pageContext.request.contextPath}/ticket/delete/${ticket.ticketID}">Zmazat pozicku</a> | 
                                        <a href="${pageContext.request.contextPath}/ticket/borrow/${ticket.ticketID}">Poziciat knihy</a> | 
                                        <a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}">Vratit ticket</a> | 
                                    </c:when>
                                </c:choose>
                                <a href="${pageContext.request.contextPath}/ticket/cancel/${ticket.ticketID}" >Zrusit rezervaciu</a> | <a href="#" class="hide">Schovat <img src="<c:url value="/resources/img/arrow_up.gif" />" /></a>
                                <!--                            <table style="border-style: dashed; border-width: 2px; padding: 2px;">
                                                                <tr>
                                                                    <td>Zrusit rezervaciu</td>
                                                                    <td><a href="#" class="hide">Hide</a></td>    
                                                                </tr>
                                                            </table>-->
                                
                            </div> 
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h3><spring:message code="label.website.ticket.notickets" /> <a href="${pageContext.request.contextPath}/ticket/create/"><spring:message code="label.website.byclick" /></a>.</h3>
                    </c:otherwise>
                </c:choose>     
                </div>
            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
