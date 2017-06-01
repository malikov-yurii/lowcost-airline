<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="footer">
    <div class="container">
        <fmt:message key="app.footer"/>
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
    "common.addComment",
    "common.updateComment",
    "common.deleteComment",
    "common.approveFreelancerForProject",
    "common.areYouSureYouWantApproveThisFreelancer",
    "common.applyForProject",
    "common.discardApplication",
    "common.lackOfSkills",
    "common.portfolio",

    "common.deleted",
    "common.saved",
    "common.enabled",
    "common.disabled",
    "common.failed",
    "ticket.paymentProcessing",
    "ticket.yourTicketIsFor",
    "ticket.paymentWindowTitle",
    "ticket.confirMoneyWithdrawalFromYouCreditCard",
    "flight.ticketPrice"}%>'>
        i18n['${key}'] = '<fmt:message key="${key}"/>';
    </c:forEach>
</script>

<%--<script src="https://cdn.jsdelivr.net/lodash/4.17.4/lodash.min.js" integrity="sha256-IyWBFJYclFY8Pn32bwWdSHmV4B9M5mby5bhPHEmeY8w=" crossorigin="anonymous"></script>--%>