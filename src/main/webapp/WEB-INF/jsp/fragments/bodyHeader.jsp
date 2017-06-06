<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<fmt:setBundle basename="messages.app"/>
<div class="nav-block" role="navigation">
    <div class="container">
        <a class="navbar-brand"><spring:message code="app.title"/></a>
        <sec:authorize access="!isAuthenticated()">
            <a class="btn btn-info" role="button" href="register"><spring:message code="app.register"/></a>
        </sec:authorize>
        <a class="btn btn-info" role="button" href="flights"><spring:message code="common.flights"/></a>
        <jsp:include page="lang.jsp"/>
        <sec:authorize access="isAuthenticated()">
            <a class="btn btn-info" role="button" href="tickets"><spring:message code="common.tickets"/></a>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a class="btn btn-info" role="button" href="users"><spring:message code="common.users"/></a>
                <a class="btn btn-info" role="button" href="tariffs"><spring:message code="common.tariffs"/></a>
            </sec:authorize>
            <form:form class="navbar-form navbar-right" action="logout" method="post">
                <a class="btn btn-info" role="button" href="profile">${userFullName}
                    <spring:message code="app.profile"/></a>
                <input type="submit" class="btn btn-primary" value="<spring:message code="app.logout"/>">
            </form:form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <div class="header-login-form">
                <form:form class="navbar-form" role="form" action="spring_security_check" method="post">
                    <div class="form-group">
                        <input type="text" placeholder="Email" class="form-control" name="username">
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="Password" class="form-control" name="password">
                    </div>
                    <button type="submit" class="btn btn-success">
                        <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                    </button>
                </form:form>
            </div>
        </sec:authorize>
    </div>
</div>
