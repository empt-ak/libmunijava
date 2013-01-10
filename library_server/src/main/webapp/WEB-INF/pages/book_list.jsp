<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.book.list"/></title>
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
                    "sAjaxSource": '${pageContext.request.contextPath}/book${jsonURL}',
//                    "sPaginationType": "full_numbers",
                    "aoColumns": [
                        {"sWidth": 51},
                        null,
                        null,
                        {"sWidth": 80},
                        {"sWidth": 80}, { "bSortable": false, "sWidth": 50,
                            "fnRender": function(oObj) {
                                return output(oObj.aData[0], oObj.aData[1], oObj.aData[2]);
                            }
                        }],
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

            function output(recordID, bookTitle, author)
            {              
                var text = '<a href="${pageContext.request.contextPath}/book/show/' + recordID + '" title="<spring:message code="label.website.book.list.tooltip.details" />"><img src="<c:url value="/resources/img/icons20x20/65.png" />" alt="<spring:message code="label.website.navigation.viewdetails" />" /></a> &nbsp;';

                <sec:authorize access="hasRole('ROLE_USER')">
                    text += '<a href="${pageContext.request.contextPath}/ticket/add/book/' + recordID + '" title="<spring:message code="label.website.book.list.tooltip.create" />"><img src="<c:url value="/resources/img/icons20x20/22.png" />" alt="<spring:message code="label.website.navigation.addbooktoticket" />" /></a> &nbsp;'
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    text += '<a href="${pageContext.request.contextPath}/book/edit/' + recordID + '" title="<spring:message code="label.website.book.list.tooltip.edit" />"><img src="<c:url value="/resources/img/icons20x20/96.png" />" alt="<spring:message code="label.website.book.add.updatebutton" />" /></a> &nbsp;'
                    text += '<a href="#" onClick="confirmDelete(' + recordID + ',\'' + bookTitle + '\',\'' + author + '\')" title="<spring:message code="label.website.book.list.tooltip.delete" />"><img src="<c:url value="/resources/img/icons20x20/33.png" />" alt="<spring:message code="label.website.book.list.deletebook" />" /></a>';
                </sec:authorize>


                    return text;
                }

               <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                function confirmDelete(id, bookTitle, author)
                {
                    if (confirm("<spring:message code="confirm.delete.entry" /> " + id + ", \n<spring:message code="book.book" />: " + bookTitle + " <spring:message code="book.writtenby" /> \"" + author + "\" ?"))
                    {
                        document.location = "${pageContext.request.contextPath}/book/delete/" + id;
                    }
                    else
                    {
                        //document.location = "/meh";
                    }
                }
               </sec:authorize>



        </script>     
    </head>
    <body> 
        <!-- wrapper -->
        <div id="wrapper">
            <!-- header -->
            <%@include file="/WEB-INF/pages/page_parts/header.jsp" %>	

            <!-- navigation--> 
            <%@include file="/WEB-INF/pages/page_parts/navigation.jsp" %>       


            <div id="faux">                
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
                    <thead>
                        <tr>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.id" />"><spring:message code="label.website.book.bookID" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.title" />"><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.author" />"><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.department" />"><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.availability" />"><spring:message code="label.website.book.edit.field.availability" /></th>
                            <th><spring:message code="label.website.action" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    

                    <tfoot>
                        <tr>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.id" />"><spring:message code="label.website.book.bookID" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.title" />"><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.author" />"><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.department" />"><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th title="<spring:message code="label.website.book.list.tooltip.sort.availability" />"><spring:message code="label.website.book.edit.field.availability" /></th>
                            <th><spring:message code="label.website.action" /></th>
                        </tr>
                    </tfoot>
                    
                    
                </table>

            </div>
                
            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
