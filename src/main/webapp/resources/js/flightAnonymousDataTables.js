var datatableApi;
var entityName = 'purchase';
var ajaxUrl = 'ajax/anonymous/purchase/';

$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "processing": true,
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "lengthMenu": [3, 5, 10],
        "serverSide": true,
        "ajax": {
            "url": 'ajax/anonymous/flight/',
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
            {
                "data": "ticketPrice",
                "render": appendDecimalsAndDollarSign,
                "orderable": false
            },
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

    $('#departureAirportCondition').val("Boryspil International Airport");
    $('#arrivalAirportCondition').val("Heathrow Airport");
    var date = new Date();
    $('#fromDepartureDateTimeCondition').val(dateToString(date));
    date.setHours(date.getHours() + 1440);
    $('#toDepartureDateTimeCondition').val(dateToString(date));

    $('.departure-datetime, .modal-input.input-datetime, .modal-input.input-airport, .modal-input.input-city').attr("readonly", "readonly");
    $('.departure-datetime, .modal-input.input-datetime').removeClass("active-input");

    $('.input-datetime.active-input').datetimepicker({
        format: getDateTimePickerFormat(),
        minDate: 0,
        onSelectDate: onSelectDateTime,
        onSelectTime: onSelectDateTime
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

// todo consider moving it for shared file for all flight.js
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
            message += i18n['flight.selectDepartureAirportForFilter'];
            departureAirportCondition.val('');
            departureAirportCondition.addClass('valid');
        }

        // ;
        if (!arrivalAirportCondition.hasClass('valid') && !(arrivalAirportCondition.val().length === 0)) {
            message = addNextLineSymbolIfNotEmpty(message);
            message += i18n['flight.selectArrivalAirportForFilter'];
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
                title: i18n['common.validationFailer'],
                text: message,
                // type: "error",
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

function onSelectDateTime (date) {
    var newDate = new Date(date);
    newDate.setHours(newDate.getHours() + 1443);
    $('#toDepartureDateTimeCondition').val(dateToString(newDate));
}