<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="lowcost" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<div class="nav-block" role="navigation">
    <div class="container">
        <a href="#" class="navbar-brand"><fmt:message key="app.title"/></a>
        <a class="btn btn-info" role="button" href="flights"><spring:message code="common.flights"/></a>
        <sec:authorize access="isAuthenticated()">
            <form:form class="navbar-form navbar-right" action="logout" method="post">
                <a class="btn btn-info" role="button" href="profile">${userFullName}
                    <fmt:message key="app.profile"/></a>
                <input type="submit" class="btn btn-primary" value="<fmt:message key="app.logout"/>">
            </form:form>
        </sec:authorize>
    </div>
</div>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2>${userDTO.firstName} <spring:message code="${register ? 'app.register' : 'app.profile'}"/></h2>
            <div class="view-box">
                <form:form modelAttribute="userDTO" class="form-horizontal" method="post" action="${register ? 'register' : 'profile'}"
                           charset="utf-8" accept-charset="UTF-8" onsubmit="return validateProfileForm()">

                    <spring:message code="user.firstName" var="firstName"/>
                    <lowcost:inputField label='${firstName}' name="firstName"/>

                    <spring:message code="user.lastName" var="LastName"/>
                    <lowcost:inputField label='${LastName}' name="LastName"/>

                    <spring:message code="user.email" var="userEmail"/>
                    <lowcost:inputField label='${userEmail}' name="email"/>

                    <spring:message code="user.password" var="userPassword"/>
                    <lowcost:inputField label='${userPassword}' name="password" inputType="password"/>

                    <spring:message code="user.phoneNumber" var="phoneNumber"/>
                    <lowcost:inputField label='${phoneNumber}' name="phoneNumber"/>

                    <div class="form-group">
                        <div class="col-xs-offset-6 col-xs-2">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript" src="resources/js/profile.js"></script>
</body>
</html>