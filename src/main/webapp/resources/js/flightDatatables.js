var datatableApi;

$(document).ready(function () {
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
            {"orderable": false, "render": renderUpdateBtn},
            {"orderable": false, "render": renderDeleteBtn}
        ],
        "initComplete": onFlightTableReady,
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "autoWidth": false
    });


    datatableApi.on('click', '.update-btn', showUpdateModal);
    $('#arrivalLocalDateTime').datetimepicker();

});

function onFlightTableReady() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

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



