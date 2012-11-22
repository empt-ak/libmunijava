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
//                alert(recordID+bookTitle+author);                
                var text = '<a href="${pageContext.request.contextPath}/book/show/' + recordID + '"><img src="<c:url value="/resources/img/icon_more.jpg" />" alt="<spring:message code="label.website.navigation.viewdetails" />" /></a> &nbsp;';
            <c:choose>
                <c:when test="${USER != null}">
                    text += '<a href="${pageContext.request.contextPath}/ticket/add/book/' + recordID + '"><img src="<c:url value="/resources/img/icon_plus.png" />" alt="<spring:message code="label.website.navigation.addbooktoticket" />" /></a> &nbsp;'
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                    text += '<a href="${pageContext.request.contextPath}/book/edit/' + recordID + '"><img src="<c:url value="/resources/img/icon_edit.gif" />" alt="<spring:message code="label.website.book.add.updatebutton" />" /></a> &nbsp;'
                    text += '<a href="#" onClick="confirmDelete(' + recordID + ',\'' + bookTitle + '\',\'' + author + '\')"><img src="<c:url value="/resources/img/icon_delete.png" />" alt="<spring:message code="label.website.book.list.deletebook" />" /></a>';
                </c:when>
            </c:choose>

                    return text;
                }
            <c:choose>
                <c:when test="${USER.systemRole == 'ADMINISTRATOR'}">
                function confirmDelete(id, bookTitle, author)
                {
                    if (confirm("<spring:message code="confirm.delete.book" /> " + id + ", \n<spring:message code="book.book" />: " + bookTitle + " <spring:message code="book.writtenby" /> \"" + author + "\" ?"))
                    {
                        document.location = "${pageContext.request.contextPath}/book/delete/" + id;
                    }
                    else
                    {
                        //document.location = "/meh";
                    }
                }
                </c:when>
            </c:choose>



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
                            <th title="ID knihy"><spring:message code="label.website.book.bookID" /></th>
                            <th><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th><spring:message code="label.website.book.edit.field.availability" /></th>
                            <th><spring:message code="label.website.action" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    

                    <tfoot>
                        <tr>
                            <th title="ID knihy"><spring:message code="label.website.book.bookID" /></th>
                            <th><spring:message code="label.website.book.add.field.booktitle" /></th>
                            <th><spring:message code="label.website.book.add.field.booktauthor" /></th>
                            <th><spring:message code="label.website.book.add.field.bookdepartment" /></th>
                            <th><spring:message code="label.website.book.edit.field.availability" /></th>
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
