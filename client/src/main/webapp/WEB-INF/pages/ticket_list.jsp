<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Zoznam kníh</title>
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
            #intro
            {
                font-size:120%;
                color:#404040;
                /*                background-color:transparent;*/
                background-color: #d7e291;
                margin-top:10px;
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
                        <a href="#" class="show">Show</a>
                        <div class="slidingDiv">
                            <table>
                                <tr>
                                    <td>nazov knihy</td>
                                    <td>autor</td>
                                    <td>status</td>
                                </tr>
                                <c:forEach var="ticketItem" items="${ticket.ticketItems}">
                                    <tr>
                                        <td>${ticketItem.book.title}</td>
                                        <td>${ticketItem.book.author}</td>
                                        <td>${ticketItem.ticketItemStatus}</td>
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
