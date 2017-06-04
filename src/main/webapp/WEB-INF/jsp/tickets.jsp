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
            <h3 class="page-title"><fmt:message key="ticket.active"/></h3>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="view-box">
                    <div class="row">
                        <div class="col-sm-7">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <form:form class="form-horizontal" id="filter">
                                        <div class="form-group">
                                            <label class="control-label col-sm-3"
                                                   for="userEmailCondition"><spring:message
                                                    code="filter.email"/>:</label>
                                            <div class="col-sm-3">
                                                <input class="input-filter form-control"
                                                       name="userEmailCondition" id="userEmailCondition"
                                                       placeholder="user@email.com">
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                                <div class="panel-footer text-right">
                                    <a class="btn btn-primary" type="button" onclick="showOrUpdateTable(false, false)">
                                            <%--<span class="glyphicon glyphicon-filter" aria-hidden="true"></span>--%>
                                        <span aria-hidden="true">Search</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <sec:authorize access="!hasRole('ROLE_ADMIN')">
                <a class="btn btn-sm btn-info show-archived" onclick="showArchivedTickets()">
                    <fmt:message key="ticket.showArchived"/></a>
                <a class="btn btn-sm btn-info show-active" style="display: none" onclick="showActiveTickets()">
                    <fmt:message key="ticket.showActive"/></a>
            </sec:authorize>

            <div class="view-box datatable" hidden="true">

                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="app.id"/></th>
                        <th><fmt:message key="airport.departure"/></th>
                        <th><fmt:message key="city.departure"/></th>
                        <th><fmt:message key="airport.arrival"/></th>
                        <th><fmt:message key="city.arrival"/></th>
                        <th><fmt:message key="flight.departureLocalDateTime"/></th>
                        <th><fmt:message key="flight.arrivalLocalDateTime"/></th>
                        <th><fmt:message key="ticket.passengerFirstName"/></th>
                        <th><fmt:message key="ticket.passengerLastName"/></th>
                        <th><fmt:message key="ticket.priorityRegistrationAndBoarding"/></th>
                        <th><fmt:message key="ticket.baggage"/></th>
                        <th><fmt:message key="ticket.seatNumber"/></th>
                        <th><fmt:message key="ticket.totalPrice"/></th>
                        <th><fmt:message key="app.status"/></th>
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
                                       name="departureAirport"
                                       readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="arrivalAirport" class="control-label col-xs-3"><fmt:message
                                    key="airport.arrival"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-airport" id="arrivalAirport"
                                       name="arrivalAirport"
                                       readonly="readonly">
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="departureCity" class="control-label col-xs-3"><fmt:message
                                    key="city.departure"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-city" id="departureCity"
                                       name="departureCity" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="arrivalCity" class="control-label col-xs-3"><fmt:message
                                    key="city.arrival"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-city" id="arrivalCity"
                                       name="arrivalCity" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="departureLocalDateTime" class="control-label col-xs-3"><fmt:message
                                    key="flight.departureLocalDateTime"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="departureLocalDateTime"
                                       name="departureLocalDateTime"
                                       readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="arrivalLocalDateTime" class="control-label col-xs-3"><fmt:message
                                    key="flight.arrivalLocalDateTime"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control input-datetime active-input"
                                       id="arrivalLocalDateTime"
                                       name="arrivalLocalDateTime"
                                       readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="passengerFirstName" class="control-label col-xs-3"><fmt:message
                                    key="ticket.passengerFirstName"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control" id="passengerFirstName"
                                       name="passengerFirstName"
                                       placeholder="Ivan">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="passengerLastName" class="control-label col-xs-3"><fmt:message
                                    key="ticket.passengerLastName"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control" id="passengerLastName"
                                       name="passengerLastName"
                                       placeholder="Ivan">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="withBaggage" class="control-label col-xs-3"><fmt:message
                                    key="ticket.includeBaggage"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control" id="withBaggage"
                                       name="withBaggage" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="withPriorityRegistrationAndBoarding" class="control-label col-xs-3"><fmt:message
                                    key="ticket.includePriorityRegistration"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control"
                                       id="withPriorityRegistrationAndBoarding"
                                       name="withPriorityRegistrationAndBoarding" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="price" class="control-label col-xs-3"><fmt:message
                                    key="flight.ticketPrice"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control " id="price"
                                       name="price" value="<%=session.getAttribute("price")%>">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="status" class="control-label col-xs-3"><fmt:message
                                    key="app.status"/></label>

                            <div class="col-xs-9">
                                <input type="text" class="modal-input form-control " id="status"
                                       name="status" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="seatNumber" class="control-label col-xs-3"><fmt:message
                                    key="ticket.seatNumber"/></label>

                            <div class="col-xs-9">
                                <input type="number" class="modal-input form-control " id="seatNumber"
                                       name="seatNumber" readonly="readonly">
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
</sec:authorize>
</body>

<jsp:include page="fragments/footer.jsp"/>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/ticketAdminDataTables.js"></script>
</sec:authorize>

<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/ticketUserDataTables.js"></script>
</sec:authorize>

<script type="text/javascript" src="resources/js/dataTablesUtil.js"></script>
<%--TODO CONSIDER REFACTORING--%>

<%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
<%--<script type="text/javascript" src="resources/js/ticketAdminDataTables.js"></script>--%>
<%--</sec:authorize>--%>

<%--<sec:authorize access="!hasRole('ROLE_ADMIN') && hasRole('ROLE_USER')">--%>
<%--<script type="text/javascript" src="resources/js/flightUserDataTables.js"></script>--%>
<%--</sec:authorize>--%>

<%--<sec:authorize access="!hasRole('ROLE_USER')">--%>
<%--<script type="text/javascript" src="resources/js/flightAnonymousDataTables.js"></script>--%>
<%--</sec:authorize>--%>

</html>
