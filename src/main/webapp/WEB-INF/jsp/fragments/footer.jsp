<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="footer">
    <div class="container">
    </div>
</div>

<script type="text/javascript">
    var i18n = [];
    <c:forEach var='key' items='<%=new String[]{
    "common.update",
    "common.addNew",
    "common.cancel",
    "common.discardCancelling",
    "common.delete",
    "common.areYouSureWantToDelete",
    "common.areYouSureWantToChangeCancellingStatus",
    "common.submit",
    "common.deleted",
    "common.saved",
    "common.enabled",
    "common.disabled",
    "common.failed",
    "common.finalDelete",
    "common.yesSure",
    "common.noCancelIt",
    "ticket.paymentProcessing",
    "ticket.yourTicketIsFor",
    "ticket.paymentWindowTitle",
    "ticket.confirmMoneyWithdrawalFromYouCreditCard",
    "flight.ticketPrice",
    "ticket.bookingCancel",
    "ticket.purchaseSuccess",
    "flight.selectDepartureAirportForFilter",
    "flight.selectArrivalAirportForFilter",
    "flight.airportsCantBeSame",
    "flight.fromShouldBeEarlier",
    "common.validationFailed",
    "flight.selectAircraft",
    "flight.selectDepartureAirport",
    "flight.selectArrivalAirport",
    "flight.selectDepartureTime",
    "flight.departureTimeCantBeEarlierThan",
    "flight.setArrivalTime",
    "flight.arrivalTimeCantBeEarlierThan",
    "flight.initialPriceCantBeGreater",
    "flight.initialPriceCantBeLess",
    "ticket.buy",
    "common.inputCorrectFirstName",
    "common.inputCorrectLastName",
    "common.inputCorrectPhoneNumber",
    "common.phoneNumberFormat",
    "flight.pickYourSeat",
    "flight.pickYourSeatLegally",
    "ticket.purchased",
    "ticket.booked",
    "common.selectUserLastNameFromDropdown",
    "common.selectUserEmailFromDropdown",
    "ticket.activeTickets",
    "ticket.archivedTickets",
    "ticket.discardBooking",
    "ticket.pay",
    "ticket.areYouSureCancelBooking",
    "ticket.confirmCancelBooking",
    "ticket.discardCancelBooking",
    "ticket.areYouSurePay",
    "ticket.confirmPay",
    "common.no",
    "ticket.bookedFor",
    "ticket.yourTicketBookedFor",
    "common.minutes",
    "ticket.cancelPay"}%>'>
    i18n['${key}'] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
