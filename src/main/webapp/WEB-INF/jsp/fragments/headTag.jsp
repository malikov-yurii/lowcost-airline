<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<title><spring:message code="app.title"/></title>
<base href="${pageContext.request.contextPath}/"/>

<link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<link rel="stylesheet" href="webjars/datatables/1.10.13/css/dataTables.bootstrap.min.css">
<link rel="stylesheet"   href="webjars/jquery-ui/1.11.4/jquery-ui.css" >
<link rel="stylesheet" href="webjars/datetimepicker/2.5.4/jquery.datetimepicker.css">
<link rel="stylesheet" type="text/css" href="webjars/sweetalert/1.1.3/dist/sweetalert.css">
<link rel="shortcut icon" href="resources/images/icon-plane.png">

<script type="text/javascript" src="webjars/sweetalert/1.1.3/dist/sweetalert.min.js"></script>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>
<script type="text/javascript" src="webjars/jquery-ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.13/js/jquery.dataTables.min.js" defer></script>
<script type="text/javascript" src="webjars/datatables/1.10.13/js/dataTables.bootstrap.min.js" defer></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js" defer></script>
<script type="text/javascript" src="webjars/datetimepicker/2.5.4/build/jquery.datetimepicker.full.min.js" defer></script>

<link rel="stylesheet" href="resources/css/style.css">


