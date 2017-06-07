<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <h3 class="page-title"><spring:message code="common.tickets"/></h3>
                <div class="view-box">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <form:form class="form-horizontal" id="filter">
                                        <div class="form-group">
                                            <div class="container">
                                                <label class="control-label col-sm-4"
                                                       for="userEmailCondition"><spring:message code="filter.email"/>:
                                                </label>
                                                <div class="col-sm-3">
                                                    <input class="input-filter form-control"
                                                           name="userEmailCondition" id="userEmailCondition"
                                                           placeholder="user@email.com">
                                                </div>
                                                <a class="btn btn-primary pull-right" type="button" onclick="showOrUpdateTable(false, false)">
                                                    <span aria-hidden="true"><spring:message code="common.search"/></span>
                                                </a>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </sec:authorize>
            <sec:authorize access="!hasRole('ROLE_ADMIN')">
                <button class="tickets-btn show-active active" onclick="showActiveTickets()">
                    <spring:message code="ticket.active"/></button>
                <button class="tickets-btn show-archived" onclick="showArchivedTickets()">
                    <spring:message code="ticket.archivedTickets"/></button>
            </sec:authorize>
            <div class="view-box datatable" hidden="true">
                <table class="table table-striped display table-tickets" id="datatable">
                    <thead>
                    <tr>
                        <th><spring:message code="common.id"/></th>
                        <th><spring:message code="airport.departure"/></th>
                        <th><spring:message code="city.departure"/></th>
                        <th><spring:message code="airport.arrival"/></th>
                        <th><spring:message code="city.arrival"/></th>
                        <th><spring:message code="flight.departureLocalDateTime"/></th>
                        <th><spring:message code="flight.arrivalLocalDateTime"/></th>
                        <th><spring:message code="ticket.passengerFirstName"/></th>
                        <th><spring:message code="ticket.passengerLastName"/></th>
                        <th><spring:message code="ticket.priorityRegistrationAndBoarding"/></th>
                        <th><spring:message code="ticket.baggage"/></th>
                        <th><spring:message code="ticket.seatNumber"/></th>
                        <th><spring:message code="ticket.totalPrice"/></th>
                        <th><spring:message code="common.status"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="modal fade" id="editRow">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" id="modalTitle"></h2>
                </div>
                <div class="modal-body">
                    <form:form class="form-horizontal" method="post" id="detailsForm">
                        <input type="text" hidden="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="departureAirport" class="control-label col-xs-5">
                                <spring:message code="airport.departure"/></label>

                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-airport" id="departureAirport"
                                       name="departureAirport"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="arrivalAirport" class="control-label col-xs-5">
                                <spring:message code="airport.arrival"/></label>

                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-airport" id="arrivalAirport"
                                       name="arrivalAirport"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="departureCity" class="control-label col-xs-5">
                                <spring:message code="city.departure"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-city" id="departureCity"
                                       name="departureCity" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="arrivalCity" class="control-label col-xs-5">
                                <spring:message code="city.arrival"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-city" id="arrivalCity"
                                       name="arrivalCity" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="departureLocalDateTime" class="control-label col-xs-5">
                                <spring:message code="flight.departureLocalDateTime"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="departureLocalDateTime"
                                       name="departureLocalDateTime"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="arrivalLocalDateTime" class="control-label col-xs-5">
                                <spring:message code="flight.arrivalLocalDateTime"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="arrivalLocalDateTime"
                                       name="arrivalLocalDateTime"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="passengerFirstName" class="control-label col-xs-5">
                                <spring:message code="ticket.passengerFirstName"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control" id="passengerFirstName"
                                       name="passengerFirstName"
                                       placeholder="Ivan">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="passengerLastName" class="control-label col-xs-5">
                                <spring:message code="ticket.passengerLastName"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control" id="passengerLastName"
                                       name="passengerLastName"
                                       placeholder="Ivan">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="withBaggage" class="control-label col-xs-5">
                                <spring:message code="ticket.includeBaggage"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control" id="withBaggage"
                                       name="withBaggage" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="withPriorityRegistrationAndBoarding" class="control-label col-xs-5">
                                <spring:message code="ticket.includePriorityRegistration"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control"
                                       id="withPriorityRegistrationAndBoarding"
                                       name="withPriorityRegistrationAndBoarding" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="price" class="control-label col-xs-5">
                                <spring:message code="flight.ticketPrice"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control " id="price" required
                                       name="price" value="<%=session.getAttribute("price")%>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status" class="control-label col-xs-5">
                                <spring:message code="common.status"/></label>
                            <div class="col-xs-7">
                                <input type="text" class="modal-input form-control " id="status"
                                       name="status" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="seatNumber" class="control-label col-xs-5">
                                <spring:message code="ticket.seatNumber"/></label>
                            <div class="col-xs-7">
                                <input type="number" class="modal-input form-control " id="seatNumber"
                                       name="seatNumber" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-7">
                                <button class="btn btn-primary" type="button" onclick="save()">
                                    <spring:message code="common.save"/></button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>



<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    <c:forEach var='key' items='<%=new String[]{
    "ticket.bookingCancel",
    "ticket.buy",
    "ticket.paymentWindowTitle",
    "ticket.yourTicketBookedFor",
    "ticket.paymentProcessing",
    "ticket.yourTicketIsFor",
    "ticket.confirmMoneyWithdrawalFromYouCreditCard",
    "ticket.purchaseSuccess",
    "ticket.purchased",
    "ticket.booked",
    "ticket.activeTickets",
    "ticket.archivedTickets",
    "ticket.discardBooking",
    "ticket.pay",
    "ticket.areYouSureCancelBooking",
    "ticket.confirmCancelBooking",
    "ticket.discardCancelBooking",
    "ticket.areYouSurePay",
    "ticket.confirmPay",
    "ticket.bookedFor",
    "ticket.cancelPay"}%>'>
    i18n['${key}'] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</body>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/ticketAdminDataTables.js"></script>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/ticketUserDataTables.js"></script>
</sec:authorize>
<script type="text/javascript" src="resources/js/dataTablesUtil.js"></script>
</html>
