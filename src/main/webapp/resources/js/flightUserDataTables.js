var freeSeats;
var datatableApi;
var entityName = 'flight';

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
            {"orderable": false, "render": renderPurchaseBtn}
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

    datatableApi.on('click', '.purchase-btn', showSetTicketDetailsModal);

    $('#departureAirportCondition').val("Boryspil International Airport");
    $('#arrivalAirportCondition').val("Heathrow Airport");
    var date = new Date();
    $('#fromDepartureDateTimeCondition').val(dateToString(date));
    date.setHours(date.getHours() + 1440);
    $('#toDepartureDateTimeCondition').val(dateToString(date));

    $('.departure-datetime, .modal-input.input-datetime, .modal-input.input-airport, .modal-input.input-city').attr("readonly", "readonly");
    $('.departure-datetime, .modal-input.input-datetime').removeClass("active-input");

    $('.input-datetime.active-input').datetimepicker({
        format: getDateTimePickerFormat()
        , minDate: 0
    });

    $('.input-airport').autocomplete({
        source: 'ajax/anonymous/airport/autocomplete-by-name'
    });

    $('.input-airport')
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

    $('.seat-picker').on('click', '.seat', selectSeat);

    // $(".show-add-new-modal").html('');
});

$('#editRow').on('hidden.bs.modal', function () {
    $("#hasBaggage").off('change');
    $("#hasPriorityRegistrationAndBoarding").off('change');
});

function renderPurchaseBtn(data, type, row) {
    return '<a class="btn btn-xs btn-primary purchase-btn">' + i18n["ticket.buy"] + '</a>';
}

function showSetTicketDetailsModal() {
    var rowData = datatableApi.row($(this).closest('tr')).data();
    var $price = $("#price");
    var $hasBaggage = $("#hasBaggage");
    var $hasPriorityRegistrationAndBoarding = $("#hasPriorityRegistrationAndBoarding");
    var $baggagePrice = $("#baggagePrice");
    var $priorityRegistrationAndBoardingPrice = $("#priorityRegistrationAndBoardingPrice");

    $hasBaggage.on('change', function () {
        if ($(this).is(':checked')) {
            $price.val(parseInt($price.val()) + parseInt($baggagePrice.val()));
        } else {
            $price.val(parseInt($price.val()) - parseInt($baggagePrice.val()));
        }
        $('.price').text(appendDecimalsAndDollarSign($price.val()));
    });

    $hasPriorityRegistrationAndBoarding.on('change', function () {
        if ($(this).is(':checked')) {
            $price.val(parseInt($price.val()) + parseInt($priorityRegistrationAndBoardingPrice.val()))
        } else {
            $price.val(parseInt($price.val()) - parseInt($priorityRegistrationAndBoardingPrice.val()))
        }
        $('.price').text(appendDecimalsAndDollarSign($price.val()));
    });

    $.ajax({
        type: "GET",
        url: 'ajax/user/ticket/details-with-free-seats/',
        data: {
            'flightId': rowData.id
        },
        success: function (data) {
            renderSeatPicker(data);
            freeSeats = data.freeSeats;
            $('#departureAirport').val(data.departureAirport);
            $('.departureAirport').text(data.departureAirport);
            $('#arrivalAirport').val(data.arrivalAirport);
            $('.arrivalAirport').text(data.arrivalAirport);
            $('#departureCity').val(data.departureCity);
            $('.departureCity').text(data.departureCity);
            $('#arrivalCity').val(data.arrivalCity);
            $('.arrivalCity').text(data.arrivalCity);
            $('#departureLocalDateTime').val(data.departureLocalDateTime);
            $('.departureLocalDateTime').text(data.departureLocalDateTime);
            $('#arrivalLocalDateTime').val(data.arrivalLocalDateTime);
            $('.arrivalLocalDateTime').text(data.arrivalLocalDateTime);
            $hasBaggage.prop("checked", false);
            $hasPriorityRegistrationAndBoarding.prop("checked", false);
            $('#price').val(data.ticketPriceDetails.baseTicketPrice);
            $('.price').text(appendDecimalsAndDollarSign(data.ticketPriceDetails.baseTicketPrice));
            $baggagePrice.val(data.ticketPriceDetails.baggagePrice);
            $('.baggagePrice').text(appendDecimalsAndDollarSign(data.ticketPriceDetails.baggagePrice));
            $priorityRegistrationAndBoardingPrice.val(data.ticketPriceDetails.priorityRegistrationAndBoardingPrice);
            $('.priorityRegistrationAndBoardingPrice').text(appendDecimalsAndDollarSign(data.ticketPriceDetails.priorityRegistrationAndBoardingPrice));
            $('#passengerFirstName').val('');
            $('#passengerLastName').val('');
            $('#seatNumber').val(0);

            $('#editRow').modal();
        }
    });
}

function save() {

    var message = "";
    var passengerFirstName = $('#passengerFirstName').val();
    var passengerLastName = $('#passengerLastName').val();
    var seatNumber = parseInt($('#seatNumber').val());

    if (passengerFirstName.length < 2 || passengerFirstName.match(/\d+/g)) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['common.inputCorrectFirstName'];
    }

    if (passengerLastName.length < 2 || passengerLastName.match(/\d+/g)) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['common.inputCorrectLastName'];
    }

    if (seatNumber === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += i18n['flight.pickYourSeat'];
    } else {
        var isFreeSeat = false;
        for (var i = 0; i < freeSeats.length; i++) {
            if (seatNumber === freeSeats[i]) {
                isFreeSeat = true;
                break;
            }
        }
        if (!isFreeSeat) {
            message = addNextLineSymbolIfNotEmpty(message);
            message += i18n['flight.pickYourSeatLegally'];
        }
    }

    if (message.length !== 0) {
        swal({
            title: i18n['common.validationFailed'],
            text: message,
            // type: "error",
            confirmButtonText: "OK"
        });

    } else {
        $('#editRow').modal('hide');
        $.ajax({
            url: 'ajax/user/ticket/',
            type: 'POST',
            data: $('#detailsForm').serialize(),
            success: function (data) {
                $('#id').val(data.bookedTicketId);
                console.log(data.bookingDuration);
                var bookingDuration = Math.floor(data.bookingDuration / 60000);
                swal({
                        title: i18n['ticket.paymentWindowTitle'] + ' ' + data.bookedTicketTotalPrice + '$',
                        type: "warning",
                        text: i18n['ticket.yourTicketBookedFor'] + ' ' + bookingDuration + ' ' + i18n['common.minutes'],
                        showCancelButton: true,
                        confirmButtonColor: '#DD6B55',
                        confirmButtonText: i18n['ticket.confirmPay'],
                        cancelButtonText: i18n['ticket.cancelPay']
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            payForTicket(data.bookedTicketId);
                            popup(i18n['ticket.purchased']);
                        } else {
                            popup(i18n['ticket.booked']);
                        }
                    });
                forceDataTableReload();
            }
        });

    }
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
            message += i18n['flight.selectDepartureAirportForFilter'];
            departureAirportCondition.val('');
            departureAirportCondition.addClass('valid');
        }

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
                title: i18n['common.validationFailed'],
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

function moveFocusToNextFormElement(formElement) {
    formElement.parents().eq(1).next().find('.form-control').first().focus();
}

function addNextLineSymbolIfNotEmpty(message) {
    if (message.length !== 0) {
        message += '\n'
    }
    return message;
}

function renderSeatPicker(data) {
    var content = '';
    for (var i = 1; i < data.totalSeats + 1; i++) {
        content += '<div class="seat disabled" data-seat="' + i + '">' + i + '</div>';
    }

    var pickerWidth = Math.ceil(data.totalSeats / 3) * 40 + 30;
    $('.seat-picker').html(content).css("width", pickerWidth);

    for (var j = 0; j < data.freeSeats.length; j++) {
        $('.seat[data-seat=' + data.freeSeats[j] + ']').removeClass('disabled').addClass('enabled');
    }
}

function selectSeat(e) {
    var seat = $(e.target).data('seat');
    $('.seat').removeClass('active');
    $(e.target).addClass('active');
    $('input#seatNumber').val(seat);
}
