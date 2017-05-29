<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>

<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>

<body class="flights">
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3 class="page-title"><fmt:message key="app.flights"/></h3>
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
                                                   name="departureAirportCondition" id="departureAirportCondition" value="Boryspil International Airport">
                                        </div>

                                        <label class="control-label col-sm-4"
                                               for="arrivalAirportCondition"><spring:message
                                                code="airport.arrival"/>:</label>

                                        <div class="col-sm-2">
                                            <input class="input-filter form-control input-airport valid"
                                                   name="arrivalAirportCondition" id="arrivalAirportCondition" value="Heathrow Airport">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3" for="fromDepartureDateTimeCondition">
                                            <spring:message code="flight.fromDepartureDateTime"/>:</label>
                                        <div class="col-sm-3">
                                            <input class="input-filter form-control input-datetime"
                                                   name="fromDepartureDateTimeCondition"
                                                   id="fromDepartureDateTimeCondition" value="2016-05-01 10:30">
                                        </div>

                                        <label class="control-label col-sm-4" for="toDepartureDateTimeCondition">
                                            <spring:message code="flight.toDepartureDateTime"/>:</label>
                                        <div class="col-sm-2">
                                            <input class="input-filter form-control input-datetime"
                                                   name="toDepartureDateTimeCondition"
                                                   id="toDepartureDateTimeCondition" readonly="readonly" value="2017-08-23 10:30">
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="panel-footer text-right">
                                <a class="btn btn-primary" type="button" onclick="updateTable(false, false)">
                                    <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="view-box">
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="app.id"/></th>
                        <th><fmt:message key="airport.departure"/></th>
                        <th><fmt:message key="airport.arrival"/></th>
                        <th><fmt:message key="flight.departureLocalDateTime"/></th>
                        <th><fmt:message key="flight.arrivalLocalDateTime"/></th>
                        <th><fmt:message key="flight.ticketPrice"/></th>
                        <th>Btn</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <%--todo class and id???--%>
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="departureAirport" class="control-label col-xs-3"><fmt:message
                                key="airport.departure"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control input-airport" id="departureAirport"
                                   name="departureAirport" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="arrivalAirport" class="control-label col-xs-3"><fmt:message
                                key="airport.arrival"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control input-airport" id="arrivalAirport"
                                   name="arrivalAirport" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="departureLocalDateTime" class="control-label col-xs-3"><fmt:message
                                key="flight.departureLocalDateTime"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control input-datetime"
                                   id="departureLocalDateTime"
                                   name="departureLocalDateTime" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="arrivalLocalDateTime" class="control-label col-xs-3"><fmt:message
                                key="flight.arrivalLocalDateTime"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control input-datetime" id="arrivalLocalDateTime"
                                   name="arrivalLocalDateTime" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="ticketPrice" class="control-label col-xs-3"><fmt:message
                                key="flight.ticketPrice"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control " id="ticketPrice"
                                   name="ticketPrice" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="withBaggage" class="control-label col-xs-3"><fmt:message
                                key="ticket.includeBaggage"/></label>

                        <div class="col-xs-9">
                            <input type="checkbox" class="modal-input form-control" id="withBaggage"
                                   name="withBaggage">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="withPriorityRegistration" class="control-label col-xs-3"><fmt:message
                                key="ticket.includePriorityRegistration"/></label>

                        <div class="col-xs-9">
                            <input type="checkbox" class="modal-input form-control" id="withPriorityRegistration"
                                   name="withPriorityRegistration">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="save()"><fmt:message
                                    key="common.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>


<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript" src="resources/js/purchaseDatatables.js"></script>

</html>