<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="lowcost" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>adsf;lkjasdklfj;alskdfj удалить

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2>${userTo.firstName} <spring:message code="${register ? 'app.register' : 'app.profile'}"/></h2>

            <div class="view-box">
                <form:form modelAttribute="userTo" class="form-horizontal" method="post" action="${register ? 'register' : 'profile'}"
                           charset="utf-8" accept-charset="UTF-8">

                    <spring:message code="users.firstName" var="firstName"/>
                    <lowcost:inputField label='${firstName}' name="firstName"/>

                    <spring:message code="users.LastName" var="LastName"/>
                    <lowcost:inputField label='${LastName}' name="LastName"/>

                    <spring:message code="users.email" var="userEmail"/>
                    <lowcost:inputField label='${userEmail}' name="email"/>

                    <spring:message code="users.password" var="userPassword"/>
                    <lowcost:inputField label='${userPassword}' name="password" inputType="password"/>

                    <spring:message code="users.phoneNumber" var="phoneNumber"/>
                    <lowcost:inputField label='${phoneNumber}' name="phoneNumber"/>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10">
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
</body>
</html>