<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="lowcost" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body class="flights">
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2><spring:message code="common.tariffs"/></h2>
            <div class="col-md-6 view-box">
                <form:form modelAttribute="tariffsDetails" class="form-horizontal" method="post"
                           charset="utf-8" accept-charset="UTF-8">
                    <spring:message code="tariff.daysCountBeforeTicketPriceStartsToGrow"
                                    var="daysCountBeforeTicketPriceStartsToGrow"/>
                    <lowcost:inputField label='${daysCountBeforeTicketPriceStartsToGrow}'
                                        name="daysCountBeforeTicketPriceStartsToGrow"/>

                    <spring:message code="tariff.weightOfTimeGrowthFactor" var="weightOfTimeGrowthFactor"/>
                    <lowcost:inputField label='${weightOfTimeGrowthFactor}' name="weightOfTimeGrowthFactor"/>

                    <spring:message code="tariff.baggageSurchargeOverMaxBaseTicketPrice"
                                    var="baggageSurchargeOverMaxBaseTicketPrice"/>
                    <lowcost:inputField label='${baggageSurchargeOverMaxBaseTicketPrice}'
                                        name="baggageSurchargeOverMaxBaseTicketPrice"/>

                    <spring:message code="tariff.priorityRegistrationAndBoardingTariff"
                                    var="priorityRegistrationAndBoardingTariff"/>
                    <lowcost:inputField label='${priorityRegistrationAndBoardingTariff}'
                                        name="priorityRegistrationAndBoardingTariff"/>

                    <spring:message code="common.active" var="active"/>
                    <lowcost:inputField label='${active}' name="active"/>

                    <div class="form-group">
                        <div class="col-xs-offset-6 col-xs-2">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="col-md-6 tariffs-desc">
                <spring:message code="tariff.description"/>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>