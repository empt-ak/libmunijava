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
        $(document).ready(function() {
				$('#example').dataTable( {
					"bProcessing": true,
					"sAjaxSource": '${pageContext.request.contextPath}/book/getJSONList'
				} );
			} );
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
                <h3>Ak sa zaregsitrujete a chcete mat admin prava zavolame url s adresou /client/makeadmin/vaslogin</h3>
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