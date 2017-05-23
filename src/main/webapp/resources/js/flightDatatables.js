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
            {"data": "arrivalLocalDateTime", "className": "input-datetime", "orderable": false},
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

    $('.input-datetime').datetimepicker({
        format: getDateTimePickerFormat()
        , minDate: 0
    });

    $('.input-airport').autocomplete({source: 'ajax/profile/airport/autocomplete-by-name'});

    $('.input-aircraft').autocomplete({source: 'ajax/profile/aircraft/autocomplete-by-name'});

    $('.input-airport, .input-aircraft')
        .on("autocompleteselect",
            function (event, ui) {
                var $this = $(this);
                $this.addClass('valid in-process');
                moveFocusToNextFormElement($this);
            }
        ).on("autocompletechange",
        function (event, ui) {
            var $this = $(this);
            $this.addClass('valid');
            if (!$this.hasClass('in-process')) {
                $this.removeClass('valid');
            }
            $this.removeClass('in-process');
        }
    ).autocomplete("option", "minLength", 0);


});

function saveFlight() {
    var message = "";

    if (!$('#aircraftName').hasClass('valid')) {
        message += 'Please select aircraft from drop-down list.';
    }

    if (!$('#departureAirport').hasClass('valid')) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please select departure airport from drop-down list.';
    }
    if (!$('#arrivalAirport').hasClass('valid')) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please select arrival airport from drop-down list.';
    }

    var currentMoment = new Date();

    var departureLocalDateTimeValue = $("#departureLocalDateTime").val();
    if (departureLocalDateTimeValue.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please set departure local date time.';
    } else if (new Date(departureLocalDateTimeValue) < currentMoment) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Departure local date time cannot be earlier than ' + dateToString(currentMoment);
    }

    var arrivalLocalDateTimeValue = $("#arrivalLocalDateTime").val();
    if (arrivalLocalDateTimeValue.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please set arrival local date time.';
    } else if (new Date(arrivalLocalDateTimeValue) < currentMoment) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Arrival local date time cannot be earlier than ' + dateToString(currentMoment);
    }

    if (message.length !== 0) {
        swal({
            title: "Validation of entered data failed.",
            text: message,
            // type: "error",
            confirmButtonText: "OK"
        });
    } else {
        $('.valid, .in-process').removeClass('valid in-process');
        $.ajax({
            type: "POST",
            url: ajaxUrl,
            data: $('#detailsForm').serialize(),
            success: function () {
                $('#editRow').modal('hide');
                updateTable();
                successNoty('common.saved');
            }
        });
    }

}

// function markNextFormElementOf(formElement) {
//
// }

function moveFocusToNextFormElement(formElement) {
    formElement.parents().eq(1).next().find('.form-control').focus();
}

function dateToString(date) {
    return date.toJSON().slice(0,16).replace('T', ' ');
}

function addNextLineSymbolIfNotEmpty(message){
    if (message.length !== 0) {
        message += '\n'
    }
    return message;
}

function onFlightTableReady() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function showAddFlightModal() {
// $('#id').val(0);
// $('#departureAirport').val('');
// $('#arrivalAirport').val('');
    // todo is it working ?
    debugger;
    console.log($('#departureLocalDateTime').val('') + "before");
    $('#modalTitle').html('Add new ' + entityName);
    $('.form-control').clear();
    console.log($('#departureLocalDateTime').val('') + "after");
    // $('#arrivalLocalDateTime').val('');
    // $('#aircraftName').val('');
    // $('#initialBaseTicketPrice').val('');
    // $('#maxBaseTicketPrice').val('');
    // $('#editRow').modal();
}



