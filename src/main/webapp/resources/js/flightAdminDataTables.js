var datatableApi;
var entityName = 'flight';
var ajaxUrl = 'ajax/admin/flight/';

$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "processing": true,
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "lengthMenu": [3, 5, 10],
        "serverSide": true,
        "ajax": {
            "url": ajaxUrl,
            "data": function (d) {
                return {
                    draw: d.draw,
                    length: d.length,
                    start: d.start,
                    fromDepartureDateTimeCondition: $('#fromDepartureDateTimeCondition').val(),
                    toDepartureDateTimeCondition: $('#toDepartureDateTimeCondition').val(),
                    departureAirportCondition: $('#departureAirportCondition').val(),
                    arrivalAirportCondition: $('#arrivalAirportCondition').val()
                };
            }
        },
        "iDeferLoading": 0,
        "searching": false,
        "pagingType": "simple_numbers",
        "paging": true,
        "info": true,
        "columns": [
            {"data": "id", "orderable": false},
            {"data": "departureAirport", "orderable": false},
            {"data": "arrivalAirport", "orderable": false},
            {"data": "departureLocalDateTime", "className": "input-datetime", "orderable": false},
            {"data": "arrivalLocalDateTime", "className": "input-datetime", "orderable": false},
            {"data": "aircraftName", "orderable": false},
            {
                "data": "initialBaseTicketPrice",
                "render": appendDecimalsAndDollarSign,
                "orderable": false
            }, {
                "data": "maxBaseTicketPrice",
                "render": appendDecimalsAndDollarSign,
                "orderable": false
            },
            {"orderable": false, "render": renderUpdateBtn},
            {"orderable": false, "render": renderCancelDiscardCancellingBtn},
            {"orderable": false, "render": renderDeleteBtn}
        ],
        "initComplete": onTableReady,
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "autoWidth": false
    });

    datatableApi.on('click', '.update-btn', showUpdateModal);

    $('#departureAirportCondition').val("Boryspil International Airport");
    $('#arrivalAirportCondition').val("Heathrow Airport");
    var date = new Date();
    $('#fromDepartureDateTimeCondition').val(dateToString(date));
    date.setHours(date.getHours() + 1440);
    $('#toDepartureDateTimeCondition').val(dateToString(date));

    $('.input-datetime').datetimepicker({
        format: getDateTimePickerFormat()
        , minDate: 0
    });

    $('.input-airport').autocomplete({
        source: 'ajax/anonymous/airport/autocomplete-by-name'
    });


    $('.input-aircraft').autocomplete({
        source: 'ajax/admin/aircraft/autocomplete-by-name'
    });
    $('.input-airport, .input-aircraft')
        .on("autocompleteselect",
            function (event, ui) {
                var $this = $(this);
                $this.addClass('valid in-process');
                if ($this.hasClass('modal-input')) {
                    moveFocusToNextFormElement($this);
                } else {
                    $this.blur();
                }
            }
        ).on("autocompletechange",
        function (event, ui) {
            var $this = $(this);
            if (!$this.hasClass('in-process')) {
                $this.removeClass('valid');
            }
            $this.removeClass('in-process');
        }
    ).autocomplete("option", "minLength", 2);
});


function showAddModal() {
    $('#modalTitle').html('Add new ' + entityName);
    $('#id').val('0');

    $('#departureAirport,#arrivalAirport,#aircraftName').val('');
    var defaultDate = new Date();
    defaultDate.setHours(defaultDate.getHours() + 24); // same time next day
    $('#departureLocalDateTime').val(dateToString(defaultDate));
    defaultDate.setHours(defaultDate.getHours() + 1); // 1 hour flight
    $('#arrivalLocalDateTime').val(dateToString(defaultDate));
    $('#initialBaseTicketPrice').val('10.00');
    $('#maxBaseTicketPrice').val('20.00');
    $('#editRow').modal();
}

function clearFilter() {
    $("#filter")[0].reset();
}

function showOrUpdateTable(forceUpdate, nextPreviousPage, added, isTabPressed, orderId) {
    var message = "";
    var departureAirportCondition = $('#departureAirportCondition');
    var arrivalAirportCondition = $('#arrivalAirportCondition');
    var fromDateTimeValue = $('#fromDepartureDateTimeCondition').val();
    var toDateTimeValue = $('#toDepartureDateTimeCondition').val();

    if (!(departureAirportCondition.val().length === 0 && arrivalAirportCondition.val().length === 0
        && fromDateTimeValue.length === 0 && toDateTimeValue.length === 0) || forceUpdate) {

        if (!departureAirportCondition.hasClass('valid') && !(departureAirportCondition.val().length === 0)) {
            message = addNextLineSymbolIfNotEmpty(message);
            message += i18n['flight.selectDepartureAirport'];
            departureAirportCondition.val('');
            departureAirportCondition.addClass('valid');
        }

        if (!arrivalAirportCondition.hasClass('valid') && !(arrivalAirportCondition.val().length === 0)) {
            message = addNextLineSymbolIfNotEmpty(message);
            message += i18n['flight.selectArrivalAirport'];
            arrivalAirportCondition.val('');
            arrivalAirportCondition.addClass('valid');
        }

        if (departureAirportCondition.val() === arrivalAirportCondition.val()
            && departureAirportCondition.val().length !== 0 && arrivalAirportCondition.val().length !== 0) {
            message = addNextLineSymbolIfNotEmpty(message);
            departureAirportCondition.val('');
            departureAirportCondition.addClass('valid');
            arrivalAirportCondition.val('');
            arrivalAirportCondition.addClass('valid');
            message += i18n['flight.airportsCantBeSame'];
        }


        if (fromDateTimeValue.length !== 0 && toDateTimeValue.length !== 0) {
            var fromDateTime = new Date(fromDateTimeValue);
            var toDateTime = new Date(toDateTimeValue);

            if (fromDateTime > toDateTime) {
                message = addNextLineSymbolIfNotEmpty(message);
                message += i18n['flight.fromShouldBeEarlier'];
            }
        }

        if (message.length !== 0) {
            swal({
                title: i18n['common.validationFailed'],
                text: message,
                confirmButtonText: "OK"
            });
            $('.datatable').attr("hidden", true);
        } else {
            forceDataTableReload();
        }
    } else {
        forceDataTableReload();
    }
}

function save() {
    var message = "";

    if (!$('#aircraftName').hasClass('valid')) {
        message += i18n['flight.selectAircraft'];
    }

    var departureAirport = $('#departureAirport');
    var arrivalAirport = $('#arrivalAirport');
    if (!departureAirport.hasClass('valid')) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.selectDepartureAirport'];
    }
    if (!arrivalAirport.hasClass('valid')) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.selectArrivalAirport'];
    }

    if (departureAirport.val() === arrivalAirport.val()
        && departureAirport.val().length !== 0 && arrivalAirport.val().length !== 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        // departureAirport.val('');
        // departureAirport.removeClass('valid');
        // arrivalAirport.val('');
        // arrivalAirport.removeClass('valid');
        message += i18n['flight.airportsCantBeSame'];
    }

    var currentMoment = new Date();

    var departureLocalDateTimeValue = $("#departureLocalDateTime").val();
    if (departureLocalDateTimeValue.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.selectDepartureTime'];
    } else if (new Date(departureLocalDateTimeValue) < currentMoment) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.departureTimeCantBeEarlierThan'] + dateToString(currentMoment);
    }

    var arrivalLocalDateTimeValue = $("#arrivalLocalDateTime").val();
    if (arrivalLocalDateTimeValue.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.setArrivalTime'];
    } else if (new Date(arrivalLocalDateTimeValue) < currentMoment) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.arrivalTimeCantBeEarlierThan'] + dateToString(currentMoment);
    }

    var initialBaseTicketPrice = $('#initialBaseTicketPrice');
    var maxBaseTicketPrice = $('#maxBaseTicketPrice');
    var initialBaseTicketPriceInt = parseInt(initialBaseTicketPrice.val(), 10);
    var maxBaseTicketPriceInt = parseInt(maxBaseTicketPrice.val(), 10);
    if (initialBaseTicketPriceInt > maxBaseTicketPriceInt) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.initialPriceCantBeGreater'];
    }

    if (initialBaseTicketPriceInt < 5) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.initialPriceCantBeLess'];
    }

    if (message.length !== 0) {
        swal({
            title: i18n['common.validationFailed'],
            text: message,
            // type: "error",
            confirmButtonText: "OK"
        });
    } else {
        $('.modal-input.valid, .modal-input.in-process').removeClass('valid in-process');
        $('.in-process').removeClass('in-process');
        $.ajax({
            type: $('#id').val() === "0" ? "POST" : "PUT",
            url: ajaxUrl,
            data: $('#detailsForm').serialize(),
            success: function () {
                $('#editRow').modal('hide');
                showOrUpdateTable(true, false);
                successNoty('common.saved');
            }
        });
    }

}


function moveFocusToNextFormElement(formElement) {
    formElement.parents().eq(1).next().find('.form-control').first().focus();
}


function addNextLineSymbolIfNotEmpty(message) {
    if (message.length !== 0) {
        message += '\n'
    }
    return message;
}