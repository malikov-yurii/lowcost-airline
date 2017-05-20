<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3 class="page-title"><fmt:message key="app.flights"/></h3>

            <div class="view-box">
                <a class="btn btn-sm btn-info show-add-new-modal" onclick="showAddFlightModal()"></a>

                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="app.id"/></th>
                        <th><fmt:message key="flight.from"/></th>
                        <th><fmt:message key="flight.to"/></th>
                        <th><fmt:message key="flight.departureLocalDateTime"/></th>
                        <th><fmt:message key="flight.arrivalLocalDateTime"/></th>
                        <th><fmt:message key="aircraft.name"/></th>
                        <th><fmt:message key="flight.initialTicketBasePrice"/></th>
                        <th><fmt:message key="flight.maxTicketBasePrice"/></th>
                        <th></th>
                        <th></th>
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
                        <label for="departureAirport" class="control-label col-xs-3"><fmt:message key="flight.from"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="departureAirport" name="departureAirport" placeholder="Boryspil International Airport">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="arrivalAirport" class="control-label col-xs-3"><fmt:message key="flight.to"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="arrivalAirport" name="arrivalAirport" placeholder="London Luton Airport">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="departureLocalDateTime" class="control-label col-xs-3"><fmt:message key="flight.departureLocalDateTime"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="departureLocalDateTime" name="departureLocalDateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="arrivalLocalDateTime" class="control-label col-xs-3"><fmt:message key="flight.arrivalLocalDateTime"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="arrivalLocalDateTime" name="arrivalLocalDateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="aircraftName" class="control-label col-xs-3"><fmt:message key="aircraft.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="aircraftName" name="aircraftName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="initialBaseTicketPrice" class="control-label col-xs-3"><fmt:message key="flight.initialTicketBasePrice"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="initialBaseTicketPrice" name="initialBaseTicketPrice" placeholder="100.00">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="maxBaseTicketPrice" class="control-label col-xs-3"><fmt:message key="flight.maxTicketBasePrice"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="maxBaseTicketPrice" name="maxBaseTicketPrice" placeholder="200.00">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="save()"><fmt:message key="common.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>


<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    $(".show-add-new-modal").html('<fmt:message key="flight.addNewFlight"/>');
    var entityName = 'flight';
    var ajaxUrl = 'ajax/profile/flights/';
</script>

<script type="text/javascript" src="resources/js/flightDatatables.js"></script>

</html>