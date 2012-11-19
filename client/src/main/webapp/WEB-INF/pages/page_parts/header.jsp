<%-- 
    Document   : header
    Created on : Oct 20, 2012, 4:34:22 PM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

    $(function() {
        $('form').each(function() {
            $('input').keypress(function(e) {
                // Enter pressed?
                if (e.which == 10 || e.which == 13) {
                    this.form.submit();
                }
            });

            //$('input[type=submit]').hide();
        });
    });
</script>  
<div id="header"> 
    <!--            <div align="left">
                    <h1><a><span><spring:message code="label.website.header.headerLibrary" /></span></a></h1>
                </div>      -->

    <div align="right" style="padding: 10px;">
        <a href="${pageContext.request.contextPath}?lang=en"><img src="<c:url value="/resources/img/us-flag.jpg"/>" /></a> 
        <a href="${pageContext.request.contextPath}?lang=sk_SK"><img src="<c:url value="/resources/img/slovak-flag.png"/>" /></a>
    </div>
</div>

