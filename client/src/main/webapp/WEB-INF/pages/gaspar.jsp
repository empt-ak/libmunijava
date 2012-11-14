<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.index.title"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />
        <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />
        <style>

            #header 
            {
                color: red;
                width: 902px;
                padding: 10px;
                height: 150px;
                margin: 10px 0px 5px 0px;
                background: #FF00FF;
                /* background: #d7e291 url(images/header.gif); */
            }
            .cssmenu li a{
                background:#FF00FF url('images/seperator.gif') bottom right no-repeat;
                color:red;
                display:block;
                font-weight:normal;
                line-height:35px;
                margin:0px;
                padding:0px 25px;
                text-align:center;
                text-decoration:none;
            }
            .cssmenu ul{
                background:#FF00FF;
                height:35px;
                list-style:none;
                margin:0;
                padding:0;
            }
        </style>

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>



        <script>
            $(document).ready(function() {
                $('#example').dataTable({
                "bProcessing": true,
                    "sAjaxSource": '${pageContext.request.contextPath}/book/getJSONList'
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
                    <a href="${pageContext.request.contextPath}/auth/">
                        <h1>chcem sa dostat medzi tajne sluzby</h1></a>
                    <br/>

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
