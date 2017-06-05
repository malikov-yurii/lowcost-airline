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
            <h3 class="page-title"><spring:message code="common.flights"/></h3>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-7">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form:form class="form-horizontal" id="filter">
                                    <div class="form-group">
                                        <label class="control-label col-sm-3"
                                               for="departureAirportCondition"><spring:message
                                                code="airport.departure"/>:</label>
                                        <div class="col-sm-3">
                                            <input class="input-filter form-control input-airport valid"
                                                   name="departureAirportCondition" id="departureAirportCondition">
                                        </div>
                                        <label class="control-label col-sm-4"
                                               for="arrivalAirportCondition"><spring:message
                                                code="airport.arrival"/>:</label>
                                        <div class="col-sm-2">
                                            <input class="input-filter form-control input-airport valid"
                                                   name="arrivalAirportCondition" id="arrivalAirportCondition">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="fromDepartureDateTimeCondition">
                                            <spring:message code="flight.fromDepartureDateTime"/>:</label>
                                        <div class="col-sm-3">
                                            <input class="input-filter form-control input-datetime active-input"
                                                   name="fromDepartureDateTimeCondition"
                                                   id="fromDepartureDateTimeCondition">
                                        </div>
                                        <label class="control-label col-sm-4" for="toDepartureDateTimeCondition">
                                            <spring:message code="flight.toDepartureDateTime"/>:</label>
                                        <div class="col-sm-2">
                                            <input class="input-filter form-control input-datetime departure-datetime active-input"
                                                   name="toDepartureDateTimeCondition"
                                                   id="toDepartureDateTimeCondition">
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="panel-footer text-right">
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a class="btn btn-danger" type="button" onclick="clearFilter()">
                                        <span aria-hidden="true"><spring:message code="filter.clear"/></span>
                                    </a>
                                </sec:authorize>
                                <a class="btn btn-primary" type="button" onclick="showOrUpdateTable(false, false)">
                                    <span aria-hidden="true">Search</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a class="btn btn-sm btn-info show-add-new-modal" onclick="showAddModal()">
                    <spring:message code="flight.addNewFlight"/></a>
            </sec:authorize>
            <div class="view-box datatable" hidden="true">
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><spring:message code="common.id"/></th>
                        <th><spring:message code="airport.departure"/></th>
                        <th><spring:message code="airport.arrival"/></th>
                        <th><spring:message code="flight.departureLocalDateTime"/></th>
                        <th><spring:message code="flight.arrivalLocalDateTime"/></th>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <th><spring:message code="aircraft.name"/></th>
                            <th><spring:message code="flight.initialTicketBasePrice"/></th>
                            <th><spring:message code="flight.maxTicketBasePrice"/></th>
                            <th></th>
                            <th></th>
                        </sec:authorize>
                        <sec:authorize access="!hasRole('ROLE_ADMIN')">
                            <th><spring:message code="flight.ticketPrice"/></th>
                        </sec:authorize>
                        <sec:authorize access="!hasRole('ROLE_ADMIN')  && hasRole('ROLE_USER')">
                            <th></th>
                        </sec:authorize>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<sec:authorize access="hasRole('ROLE_USER')">
    <div class="modal fade" id="editRow">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h2 class="modal-title" id="modalTitle"></h2>
                </div>
                <div class="modal-body">
                    <form:form class="form-horizontal" method="post" id="detailsForm">
                        <div class="seat-picker__title">
                            <spring:message code="ticket.pleaseSelectYourSeat"/>
                        </div>
                        <div class="seat-picker"></div>
                        <input type="text" hidden="hidden" id="id" name="id"  value="0">
                        <div class="form-group">
                            <label for="departureAirport" class="control-label col-xs-3">
                                <spring:message code="airport.departure"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-airport" id="departureAirport"
                                       name="departureAirport"
                                       placeholder="please choose arrival airport from drop down list">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="arrivalAirport" class="control-label col-xs-3">
                                <spring:message code="airport.arrival"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-airport" id="arrivalAirport"
                                       name="arrivalAirport"
                                       placeholder="please choose arrival airport from drop down list">
                            </div>
                        </div>
                        <sec:authorize access="!hasRole('ROLE_ADMIN')">
                            <div class="form-group">
                                <label for="departureCity" class="control-label col-xs-3">
                                    <spring:message code="city.departure"/></label>
                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control input-city" id="departureCity"
                                           name="departureCity" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="arrivalCity" class="control-label col-xs-3">
                                    <spring:message code="city.arrival"/></label>

                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control input-city" id="arrivalCity"
                                           name="arrivalCity" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="price" class="control-label col-xs-3">
                                    <spring:message code="flight.ticketPrice"/></label>

                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control " id="price"
                                           name="price" readonly="readonly" value="<%=session.getAttribute("price")%>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="seatNumber" class="control-label col-xs-3">
                                    <spring:message code="ticket.seatNumber"/></label>

                                <div class="col-xs-9">
                                    <input type="number" class="modal-input form-control " id="seatNumber"
                                           name="seatNumber" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="passengerFirstName" class="control-label col-xs-3">
                                    <spring:message code="ticket.passengerFirstName"/></label>

                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control" id="passengerFirstName"
                                           name="passengerFirstName"
                                           placeholder="Ivan">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="passengerLastName" class="control-label col-xs-3">
                                    <spring:message code="ticket.passengerLastName"/></label>

                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control" id="passengerLastName"
                                           name="passengerLastName"
                                           placeholder="Ivan">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="withBaggage" class="control-label col-xs-3">
                                    <spring:message code="ticket.includeBaggage"/></label>

                                <div class="col-xs-9">
                                    <input type="checkbox" class="modal-input form-control" id="withBaggage"
                                           name="withBaggage">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="withPriorityRegistrationAndBoarding"
                                       class="control-label col-xs-3">
                                    <spring:message code="ticket.includePriorityRegistration"/></label>

                                <div class="col-xs-9">
                                    <input type="checkbox" class="modal-input form-control"
                                           id="withPriorityRegistrationAndBoarding"
                                           name="withPriorityRegistrationAndBoarding">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="baggagePrice" class="control-label col-xs-3">
                                    <spring:message code="ticket.baggagePrice"/></label>

                                <div class="col-xs-9">
                                    <input type="number" class="modal-input form-control" id="baggagePrice"
                                           name="baggagePrice" readonly="readonly">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="priorityRegistrationAndBoardingPrice"
                                       class="control-label col-xs-3">
                                    <spring:message code="ticket.priorityRegistrationAndBoardingPrice"/></label>
                                <div class="col-xs-9">
                                    <input type="number" class="modal-input form-control"
                                           id="priorityRegistrationAndBoardingPrice"
                                           name="priorityRegistrationAndBoardingPrice" readonly="readonly">
                                </div>
                            </div>
                        </sec:authorize>
                        <div class="form-group">
                            <label for="departureLocalDateTime" class="control-label col-xs-3">
                                <spring:message code="flight.departureLocalDateTime"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="departureLocalDateTime"
                                       name="departureLocalDateTime"
                                       placeholder="please set arrival local date">
                            </div>
                        </div>
                        <div class="form-group">
                            0 <label for="arrivalLocalDateTime" class="control-label col-xs-3">
                            <spring:message code="flight.arrivalLocalDateTime"/></label>
                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="arrivalLocalDateTime"
                                       name="arrivalLocalDateTime"
                                       placeholder="please set arrival local date">
                            </div>
                        </div>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <div class="form-group">
                                <label for="aircraftName" class="control-label col-xs-3">
                                    <spring:message code="aircraft.name"/></label>
                                <div class="col-xs-9">
                                    <input type="text" class="modal-input form-control input-aircraft" id="aircraftName"
                                           name="aircraftName"
                                           placeholder="please choose aircraft from drop down list">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="initialBaseTicketPrice" class="control-label col-xs-3">
                                    <spring:message code="flight.initialTicketBasePrice"/></label>

                                <div class="col-xs-9">
                                    <input type="number" class="modal-input form-control" id="initialBaseTicketPrice"
                                           name="initialBaseTicketPrice" placeholder="10.00">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="maxBaseTicketPrice" class="control-label col-xs-3">
                                    <spring:message code="flight.maxTicketBasePrice"/></label>

                                <div class="col-xs-9">
                                    <input type="number" class="modal-input form-control" id="maxBaseTicketPrice"
                                           name="maxBaseTicketPrice" placeholder="20.00">
                                </div>
                            </div>
                        </sec:authorize>
                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
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
</body>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript" src="resources/js/dataTablesUtil.js"></script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/flightAdminDataTables.js"></script>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_ADMIN') && hasRole('ROLE_USER')">
    <script type="text/javascript" src="resources/js/flightUserDataTables.js"></script>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_USER')">
    <script type="text/javascript" src="resources/js/flightAnonymousDataTables.js"></script>
</sec:authorize>
</html>
