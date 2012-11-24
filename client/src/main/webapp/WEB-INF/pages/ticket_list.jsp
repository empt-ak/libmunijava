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
                                                <td><spring:message code="label.website.action" /></td>
                                            </c:if>

                                        </tr>
                                        <c:choose>
                                            <c:when test="${ticket.ticketItems.size() == 0}">
                                                <tr>
                                                    <td colspan="4"><spring:message code="label.website.ticket.list.nobook" /> <a href="${pageContext.request.contextPath}/book/"><spring:message code="label.website.byclick" /></a></td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="ticketItem" items="${ticket.ticketItems}">
                                                    <tr>
                                                        <td>${ticketItem.book.title}</td>
                                                        <td>${ticketItem.book.author}</td>
                                                        <td>${ticketItem.ticketItemStatus}</td>
                                                        <c:choose>                                                            
                                                            <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                                                                <c:choose>
                                                                    <c:when test="${ticketItem.ticketItemStatus == 'RETURNED'}">
                                                                        <td><a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}/ticketitem/${ticketItem.ticketItemID}/damaged/true/"><spring:message code="label.website.ticket.return.book.damaged" /></a></td>
                                                                    </c:when>
                                                                    <c:when test="${ticketItem.ticketItemStatus == 'RETURNED_DAMAGED'}">
                                                                        <td></td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td><a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}/ticketitem/${ticketItem.ticketItemID}/damaged/false/"><spring:message code="label.website.ticket.return.book" /></a></td>
                                                                    </c:otherwise>
                                                                </c:choose>                                                                
                                                            </c:when>
                                                            <%--
                                                                c:when test="${ticketItem.ticketItemStatus == 'RETURNED' || ticketItem.ticketItemStatus == 'RETURNED_DAMAGED'} 
                                                            --%>
                                                            <c:otherwise>
                                                                <td></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>                                    
                                    </table><br />   
                                    <c:choose>
                                        <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                                            <a href="${pageContext.request.contextPath}/ticket/delete/${ticket.ticketID}"><spring:message code="label.website.ticket.delete" /></a> | 
                                            <a href="${pageContext.request.contextPath}/ticket/borrow/${ticket.ticketID}"><spring:message code="label.website.ticket.borrow" /></a> | 
                                            <a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}"><spring:message code="label.website.ticket.return" /></a> | 
                                        </c:when>
                                    </c:choose>
                                    <a href="${pageContext.request.contextPath}/ticket/cancel/${ticket.ticketID}" ><spring:message code="label.website.ticket.cancel" /></a> | <a href="#" class="hide"><spring:message code="label.website.ticket.hide" /> <img src="<c:url value="/resources/img/arrow_up.gif" />" /></a>
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
