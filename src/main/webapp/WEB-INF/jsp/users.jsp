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
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3 class="page-title"><spring:message code="common.users"/></h3>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-7">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form:form class="form-horizontal" id="filter">
                                    <div class="form-group">
                                        <div class="container">
                                            <label class="control-label col-sm-4"
                                                   for="lastNameCondition"><spring:message code="user.lastName"/>:</label>
                                            <div class="col-sm-3">
                                                <input class="input-filter form-control"
                                                       name="lastNameCondition" id="lastNameCondition"
                                                       placeholder="Eduardov">
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
            <div class="view-box datatable" hidden="true">
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><spring:message code="common.id"/></th>
                        <th><spring:message code="user.firstName"/></th>
                        <th><spring:message code="user.lastName"/></th>
                        <th><spring:message code="user.email"/></th>
                        <th><spring:message code="user.phoneNumber"/></th>
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
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <div class="form-group">
                        <label for="firstName" class="control-label col-xs-3">
                            <spring:message code="user.firstName"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control" id="firstName" name="firstName" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastName" class="control-label col-xs-3">
                            <spring:message code="user.lastName"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control" id="lastName" name="lastName" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="control-label col-xs-3">
                            <spring:message code="user.email"/></label>
                        <div class="col-xs-9">
                            <input type="email" class="modal-input form-control" id="email"
                                   name="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber" class="control-label col-xs-3">
                            <spring:message code="user.phoneNumber"/></label>
                        <div class="col-xs-9">
                            <input type="tel" class="modal-input form-control" id="phoneNumber"
                                   name="phoneNumber" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="submit">
                                <spring:message code="common.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript" src="resources/js/dataTablesUtil.js"></script>
<script type="text/javascript" src="resources/js/userDataTables.js"></script>
</html>
