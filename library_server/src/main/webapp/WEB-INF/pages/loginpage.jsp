<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><spring:message code="label.website.title.user.login"/></title>
        <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"><!-- type="text/css"  nepovinne v html 5 -->
        <link rel="stylesheet" href="<c:url value="/resources/css/menu.css" />" />

        <script src="<c:url value="/resources/javascript/jQuery.1.8.2.js" />"></script>

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

                <!--<form action="${pageContext.request.contextPath}/user/login/" method="post" >           									
                    <fieldset>
                        <label><spring:message code="label.website.user.register.field.username"/></label><input type="text" id="register_input" name="username" /><br/>
                        <label><spring:message code="label.website.user.register.field.password"/></label><input type="password" id="register_input" name="password" /><br/>
                        <label></label><input type="submit" id="register_button" value="<spring:message code="label.website.user.login.loginbutton"/>" />    
                    </fieldset>

                </form> -->
                
                <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                    <fieldset>
                        <label><spring:message code="label.website.user.register.field.username"/></label><input id="j_username" name="j_username" type="text"/>
                        <label><spring:message code="label.website.user.register.field.password"/></label><input id="j_password" name="j_password" type="password"/>
                        <label></label><input type="submit"  id="register_button" value="<spring:message code="label.website.user.login.loginbutton"/>"/>
                    </fieldset>
                </form>

            </div>	

            <!-- footer -->
            <%@include file="/WEB-INF/pages/page_parts/footer.jsp" %>      
        </div>
    </body>
</html>
