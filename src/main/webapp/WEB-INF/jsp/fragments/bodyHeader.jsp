<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="nav-block" role="navigation">
    <div class="container">
        <a href="#" class="navbar-brand"><fmt:message key="app.title"/></a>
        <form:form class="navbar-form navbar-right" action="logout" method="post">
            <sec:authorize access="isAuthenticated()">
                <a class="btn btn-info" role="button" href="projects"><fmt:message key="app.projects"/></a>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-info" role="button" href="clients"><fmt:message key="app.clients"/></a>
                    <a class="btn btn-info" role="button" href="freelancers"><fmt:message key="app.freelancers"/></a>
                    <a class="btn btn-info" role="button" href="admins"><fmt:message key="app.admins"/></a>
                    <a class="btn btn-info" role="button" href="skills"><fmt:message key="app.skills"/></a>
                </sec:authorize>
                <a class="btn btn-info" role="button" href="profile">${baseUserTo.firstName} ${baseUserTo.lastName}
                    <fmt:message key="app.profile"/></a>
                <input type="submit" class="btn btn-primary" value="<fmt:message key="app.logout"/>">
            </sec:authorize>
        </form:form>
    </div>
</div>
<script>
    var authorizedUserId = ${baseUserTo.id};
    var authorizedUserRole = '${baseUserTo.role}';
</script>