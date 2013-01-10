<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.user.list"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />
        <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>

        <script>
            $(document).ready(function() {
                $('#users').dataTable({
                    "bProcessing": true,
                    "sAjaxSource": '${pageContext.request.contextPath}/user/getUsersJSON',
                    "aoColumns": [
                        null,
                        null,
                        null,
                        null,
                        null, {"bSortable": false,
                            "fnRender": function(oObj) {
                                return output(oObj.aData[0], oObj.aData[2]);
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

            function output(userID, realName)
            {
                var output = '';
 
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        output += '<a href="${pageContext.request.contextPath}/user/edit/' + userID + '" title="<spring:message code="label.website.user.list.tooltip.edit" />"><img src="<c:url value="/resources/img/icons20x20/96.png" />" /></a>';
                        output += '<a href="${pageContext.request.contextPath}/ticket/show/user/' + userID + '" title="<spring:message code="label.website.user.list.tooltip.ticketshow" />"> <img src="<c:url value="/resources/img/icons20x20/52.png" />" /> </a>';
                        output += '<a href="#" onClick="confirmDelete(' + userID + ',\'' + realName + '\')" title="<spring:message code="label.website.user.list.tooltip.delete" />"> <img src="<c:url value="/resources/img/icons20x20/33.png" />" /> </a>';
                        </sec:authorize>


                return output;
            }


                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    function confirmDelete(id, userRname)
                    {
                        if (confirm("chcet zmazat zaznam" + id + "\nUzivatela : " + userRname))
                        {
                            document.location = "${pageContext.request.contextPath}/user/delete/" + id;
                        }
                        else
                        {
                            //kk
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

            <!-- Begin Faux Columns -->
            <div id="faux">

                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <table cellpadding="0" cellspacing="0" border="0" class="display" id="users">
                            <thead>
                                <tr>
                                    <th title="ID knihy"><spring:message code ="label.website.user.add.field.IDus"/></th>
                                    <th><spring:message code ="label.website.user.add.field.username"/></th>
                                    <th><spring:message code ="label.website.user.add.field.rname"/></th>
                                    <th><spring:message code ="label.website.user.add.field.pass"/></th>
                                    <th><spring:message code ="label.website.user.add.field.role"/></th>  
                                    <th><spring:message code ="label.website.user.add.field.action"/></th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                                <tr>
                                    <th><spring:message code ="label.website.user.add.field.IDus"/></th>
                                    <th><spring:message code ="label.website.user.add.field.username"/></th>
                                    <th><spring:message code ="label.website.user.add.field.rname"/></th>
                                    <th><spring:message code ="label.website.user.add.field.pass"/></th>
                                    <th><spring:message code ="label.website.user.add.field.role"/></th>  
                                    <th><spring:message code ="label.website.user.add.field.action"/></th>
                                </tr>
                            </tfoot>
                        </table>
                    </sec:authorize>
                    <sec:authorize access="!hasRole('ROLE_ADMINISTRATOR')">
                        <spring:message code="error.website.accessdenied"/>
                    </sec:authorize>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
