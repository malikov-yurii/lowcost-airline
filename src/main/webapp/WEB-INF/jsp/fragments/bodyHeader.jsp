<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="nav-block" role="navigation">
    <div class="container">
        <a href="#" class="navbar-brand"><fmt:message key="app.title"/></a>
        <%--<sec:authorize access="!isAuthenticated() && !${register}">--%>
        <sec:authorize access="!isAuthenticated()">
            <li>
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
            </li>
        </sec:authorize>

        <sec:authorize access="!isAuthenticated()">
            <a class="btn btn-info" role="button" href="register"><fmt:message key="app.register"/></a>
        </sec:authorize>


        <sec:authorize access="isAuthenticated()">
            <form:form class="navbar-form navbar-right" action="logout" method="post">

                <a class="btn btn-info" role="button" href="purchase"><fmt:message key="app.purchaseTicket"/></a>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <%--<a class="btn btn-info" role="button" href="flights"><fmt:message key="app.flights"/></a>--%>
                    <%--<a class="btn btn-info" role="button" href="freelancers"><fmt:message key="app.freelancers"/></a>--%>
                    <%--<a class="btn btn-info" role="button" href="admins"><fmt:message key="app.admins"/></a>--%>
                    <%--<a class="btn btn-info" role="button" href="skills"><fmt:message key="app.skills"/></a>--%>
                </sec:authorize>
                <a class="btn btn-info" role="button" href="profile">${userFullName}
                    <fmt:message key="app.profile"/></a>
                <input type="submit" class="btn btn-primary" value="<fmt:message key="app.logout"/>">
            </form:form>
        </sec:authorize>

    </div>
</div>
