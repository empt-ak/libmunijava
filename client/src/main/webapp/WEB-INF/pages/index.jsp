<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.index.title"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />
        <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>
        
        <script>
            var oTable;
            $(document).ready(function() {
                oTable = $('#example').dataTable({
                    "bProcessing": true, 
                    "bFilter":false,
                    "bPaginate":false,
                    "bInfo":false,
                    "sAjaxSource": '${pageContext.request.contextPath}/book/getJSONlastbooks',
//                    "sPaginationType": "full_numbers",
                    "aoColumns": [
                        null,
                        null,
                        {"sWidth": 80},
                        {"sWidth": 80}
                        ],
                    "oLanguage":
                            {
                                "sLengthMenu": "<spring:message code="label.datatable.sLengthMenu" />",
                                "sZeroRecords": "<spring:message code="label.datatable.sZeroRecords" />",
                                "sInfo": "<spring:message code="label.datatable.sInfo" />",
                                "sInfoEmpty": "<spring:message code="label.datatable.sInfoEmpty" />",
                                "sInfoFiltered": "<spring:message code="label.datatable.sInfoFiltered" />",
                                "sSearch": "<spring:message code="label.datatable.sSearch" />",
                                "oPaginate":
                                        {
                                            "sFirst": "<spring:message code="label.datatable.sFirst" />",
                                            "sLast": "<spring:message code="label.datatable.sLast" />",
                                            "sNext": "<spring:message code="label.datatable.sNext" />",
                                            "sPrevious": "<spring:message code="label.datatable.sPrevious" />"
                                        }
                            }
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

                <h1>Library  &mdash; projekt z PA165</h1>
                
                - ak sa zaregsitrujete a chcete mat admin prava zadajte url adresu <b>/pa165/makeadmin/vaslogin</b>
                
                <br/>
                
                <h2 id="last_books"> Posledných 5 pridaných kníh do knižnice:</h2>
                
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
                    <thead>
                        <tr>

                            <th><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th><spring:message code="label.website.book.edit.field.availability" /></th>

                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                            </table>
                            
                            <div  class="all_books">
                   <a href='${pageContext.request.contextPath}/book/'><span><spring:message code="label.website.otherbooks.books" /></span></a>
                            </div>
            </div>
            

            

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
