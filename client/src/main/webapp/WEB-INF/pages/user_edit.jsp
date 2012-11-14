<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Úprava profilu užívateľa</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
    <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/demo_table.css" />" />
    
    <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js" />"></script>
  
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
                <form:form method="POST" action="${pageContext.request.contextPath}/user/edit" commandName="userDTO">                    
                    <fieldset>
                        <legend>Registracia uzivatela</legend>                    
                        <table>
                            <tr>
                                <td><form:label path="username">Username</form:label></td>
                                <td><form:input path="username" readonly="true"/> <form:errors path="username"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="realName">Real Name</form:label></td>
                                <td><form:input path="realName" /><form:errors path="realName"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="password">Password</form:label></td>
                                <td><form:password path="password" /><form:errors path="password"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="upravit"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </form:form>
               
                
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
