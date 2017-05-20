var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "searching": false,
        "pagingType": "full_numbers",
        "paging": true,
        "info": true,
        "columns": [
            {"data": "id", "orderable": false},
            {"data": "departureAirport", "orderable": false},
            {"data": "arrivalAirport", "orderable": false},
            {"data": "departureLocalDateTime", "orderable": false},
            {"data": "arrivalLocalDateTime", "orderable": false},
            {"data": "aircraftName", "orderable": false},
            {"data": "initialBaseTicketPrice", "orderable": false},
            {"data": "maxBaseTicketPrice", "orderable": false},
            {"orderable": false, "render": renderUpdateFlightBtn},
            {"orderable": false, "render": renderDeleteBtn}
        ],
        "initComplete": onProjectTableReady,
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "autoWidth": false
    });
});

function showAddFlightModal() {
    $('#modalTitle').html('Add new ' + entityName);
    $('#id').val(0);
    $('#departureAirport').val('');
    $('#arrivalAirport').val('');
    $('#departureLocalDateTime').val('');
    $('#arrivalLocalDateTime').val('');
    $('#aircraftName').val('');
    $('#initialBaseTicketPrice').val('');
    $('#maxBaseTicketPrice').val('');
    $('#editRow').modal();
}

function renderUpdateFlightBtn(data, type, row) {
    return '<a class="btn btn-xs btn-primary" onclick="showUpdateFlightModal(' +
        row.id + ', \'' +
        row.departureAirport + ', \'' +
        row.arrivalAirport + ', \'' +
        row.departureLocalDateTime + ', \'' +
        row.arrivalLocalDateTime + ', \'' +
        row.aircraftName + ', \'' +
        row.initialBaseTicketPrice + ', \'' +
        row.maxBaseTicketPrice +
        '\')">' + i18n['common.update'] + '</a>';
}

function showUpdateFlightModal(id, departureAirport, arrivalAirport, departureLocalDateTime, arrivalLocalDateTime, aircraftName, initialBaseTicketPrice, maxBaseTicketprice) {
    $('#modalTitle').html(i18n['common.update'] + entityName);
    $('#id').val(id);
    $('#departureAirport').val(departureAirport);
    $('#arrivalAirport').val(arrivalAirport);
    $('#departureLocalDateTime').val(departureLocalDateTime);
    $('#arrivalLocalDateTime').val(arrivalLocalDateTime);
    $('#aircraftName').val(aircraftName);
    $('#initialBaseTicketPrice').val(initialBaseTicketPrice);
    $('#maxBaseTicketPrice').val(maxBaseTicketPrice);
    $('#editRow').modal();
}

