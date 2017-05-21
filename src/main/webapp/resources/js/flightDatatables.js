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
            {"data": "departureLocalDateTime", "className": "input-datetime", "orderable": false},
            {"data": "arrivalLocalDateTime", "className": "input-datetime","orderable": false},
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

    $('.input-datetime').datetimepicker({format:	getDateTimePickerFormat()});
    $('.input-airport').autocomplete({
        source: 'ajax/profile/airport/autocomplete-by-name'
        , minLength: 0
    });
    $('.input-aircraft').autocomplete({
        source: 'ajax/profile/aircraft/autocomplete-by-name'
        , minLength: 0
    });
    // $('#departureLocalDateTimeFilter').datetimepicker({format:	getDateTimePickerFormat()});
    // $('#arrivalLocalDateTimeFilter').datetimepicker({format:	getDateTimePickerFormat()});
    // $('#departureAirportFilter').autocomplete({
    //     source: ajaxUrl + 'autocomplete-airport'
    //     , minLength: 0
    // });
    // $('#arrivalAirportFilter').autocomplete({
    //     source: ajaxUrl + 'autocomplete-airport'
    //     , minLength: 0
    // });
    //
    //
    //
    //
    // $('#departureLocalDateTime').datetimepicker({format:	getDateTimePickerFormat()});
    // $('#arrivalLocalDateTime').datetimepicker({format:	getDateTimePickerFormat()});
    // $('#departureAirport').autocomplete({
    //     source: ajaxUrl + 'autocomplete-airport'
    //     , minLength: 0
    // });
    // $('#arrivalAirport').autocomplete({
    //     source: ajaxUrl + 'autocomplete-airport'
    //     , minLength: 0
    // });

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



