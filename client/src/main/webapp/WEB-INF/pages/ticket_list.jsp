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

        <style>            
            .slidingDiv {                
                background-color: #d7e291;
                padding:20px;
                margin-top:10px;
                border-bottom:5px solid #333333;
                display: none;
            }​​

            #ffs
            {
                background-color: #d7e291;
                padding:20px;
                margin-top:10px;
                border-bottom:5px solid #333333;
            }
        </style>


        <script>
            $(document).ready(function() {
                $('.show').click(function() {
                    $(this).next('div').slideDown();
                    $(this).hide();
                });

                $('.hide').click(function() {
                    $(this).parent().slideUp();
                    $(this).parent().prev('.show').show();
                });
            });
        </script>     
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
                <!-- content -->
                <div id="leftcolumn">
                    <c:forEach var="ticket" items="${tickets}">
                        <div class="show" style="background-color: #d7e291; border-style: dotted; border-width: 1px;">
                            <table width="100%">
                                <tr>
                                    <td>Pozicka c: ${ticket.ticketID}</td>
                                    <td>Datum pozicania: ${ticket.borrowTime.dayOfMonth}.${ticket.borrowTime.monthOfYear}.${ticket.borrowTime.year}</td>
                                    <td>Vratit do : ${ticket.dueTime.dayOfMonth}.${ticket.dueTime.monthOfYear}.${ticket.dueTime.year}</td>  
                                    <td><img src="<c:url value="/resources/img/arrow_down.gif" />" /></td>
                                </tr>
                            </table>
                        </div>                        
                        <div class="slidingDiv">
                            <table>
                                <tr>
                                    <td>nazov knihy</td>
                                    <td>autor</td>
                                    <td>status</td>
                                    <td></td>
                                </tr>
                                <c:forEach var="ticketItem" items="${ticket.ticketItems}">
                                    <tr>
                                        <td>${ticketItem.book.title}</td>
                                        <td>${ticketItem.book.author}</td>
                                        <td>${ticketItem.ticketItemStatus}</td>
                                        <td><a href="${pageContext.request.contextPath}/ticket/return/${ticket.ticketID}/ticketitem/${ticketItem.book.bookID}">Vratit knihu</a></td>
                                    </tr>
                                </c:forEach>
                            </table> <a href="#" class="hide">Hide</a>
                        </div> <br/>  
                    </c:forEach>
                        
                    <div class="clear"></div>
                </div> 
                <!-- sidebar -->
                <%@include file="/WEB-INF/pages/page_parts/sidebar.jsp" %>           
            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
