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
    "common.validationFailed",
    "common.inputCorrectFirstName",
    "common.inputCorrectLastName",
    "common.inputCorrectPhoneNumber",
    "common.phoneNumberFormat",
    "common.selectUserLastNameFromDropdown",
    "common.selectUserEmailFromDropdown",
    "common.no",
    "common.minutes"}%>'>
    i18n['${key}'] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>