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
            <h3 class="page-title"><spring:message code="common.airports"/></h3>
            <div class="view-box">
                <div class="row">
                    <div class="col-sm-7">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form:form class="form-horizontal" id="filter">
                                    <div class="form-group">
                                        <label class="control-label col-sm-3"
                                               for="nameCondition"><spring:message code="airport.name"/>:</label>
                                        <div class="col-sm-3">
                                            <input class="input-filter form-control valid"
                                                   name="nameCondition" id="nameCondition"
                                                   value="Boryspil International Airport"
                                                   placeholder="Heathrow Airport">
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="panel-footer text-right">
                                <a class="btn btn-primary" type="button" onclick="showOrUpdateTable(false, false)">
                                    <span aria-hidden="true">Search</span>
                                </a>
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
                        <th><spring:message code="airport.name"/></th>
                        <th><spring:message code="common.city"/></th>
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
                        <label for="name" class="control-label col-xs-3">
                            <spring:message code="airport.name"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control" id="name"
                                   name="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cityName" class="control-label col-xs-3">
                            <spring:message code="city.name"/></label>
                        <div class="col-xs-9">
                            <input type="text" class="modal-input form-control" id="cityName"
                                   name="cityName" readonly="readonly">
                        </div>
                    </div>
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
</body>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript" src="resources/js/dataTablesUtil.js"></script>
<script type="text/javascript" src="resources/js/airports.js"></script>
</html>
