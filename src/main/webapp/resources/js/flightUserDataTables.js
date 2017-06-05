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
            // ,"dataSrc": ""
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
            {"data": "ticketPrice", "orderable": false},
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
            // $this.addClass('valid'); ????????????? why was uncommented???????
            // if ($this.hasClass('input-filter') && ($this.val().length === 0)) {
            //     $this.addClass('valid');
            // } else
            if (!$this.hasClass('in-process')) {
                $this.removeClass('valid');
            }
            $this.removeClass('in-process');
        }
    ).autocomplete("option", "minLength", 2);

    $('.seat-picker').on('click', '.seat', selectSeat);

    // $(".show-add-new-modal").html('');
});

function renderPurchaseBtn(data, type, row) {
    // return '<a>Buy ticket</a>';
    // return '<a class="btn btn-xs btn-primary" onclick="showPurchaseModal()">Buy ticket</a>';
    return '<a class="btn btn-xs btn-primary purchase-btn">Buy ticket</a>';
}

function showSetTicketDetailsModal() {
    var rowData = datatableApi.row($(this).closest('tr')).data();
    var $price = $("#price");
    var $withBaggage = $("#withBaggage");
    var $withPriorityRegistrationAndBoarding = $("#withPriorityRegistrationAndBoarding");
    var $baggagePrice = $("#baggagePrice");
    var $priorityRegistrationAndBoardingPrice = $("#priorityRegistrationAndBoardingPrice");

    $withBaggage.on('change', function () {
        if ($(this).is(':checked')) {
            $price.val(parseInt($price.val()) + parseInt($baggagePrice.val()))
        } else {
            $price.val(parseInt($price.val()) - parseInt($baggagePrice.val()))
        }
    });

    $withPriorityRegistrationAndBoarding.on('change', function () {
        if ($(this).is(':checked')) {
            $price.val(parseInt($price.val()) + parseInt($priorityRegistrationAndBoardingPrice.val()))
        } else {
            $price.val(parseInt($price.val()) - parseInt($priorityRegistrationAndBoardingPrice.val()))
        }
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
            $('#arrivalAirport').val(data.arrivalAirport);
            $('#departureCity').val(data.departureCity);
            $('#arrivalCity').val(data.arrivalCity);
            $('#departureLocalDateTime').val(data.departureLocalDateTime);
            $('#arrivalLocalDateTime').val(data.arrivalLocalDateTime);
            $withBaggage.prop("checked", false);
            $withPriorityRegistrationAndBoarding.prop("checked", false);
            $('#price').val(data.ticketPriceDetails.baseTicketPrice);
            $baggagePrice.val(data.ticketPriceDetails.baggagePrice);
            $priorityRegistrationAndBoardingPrice.val(data.ticketPriceDetails.priorityRegistrationAndBoardingPrice);
            $('#passengerFirstName').val('');
            $('#passengerLastName').val('');
            $('#seatNumber').val(0);

            $('#editRow').modal();
        }
    });


    // todo implement what if error??? (when last ticket just has been bought)

}

function save() {

    var message = "";
    var passengerFirstName = $('#passengerFirstName').val();
    var passengerLastName = $('#passengerLastName').val();
    var seatNumber = parseInt($('#seatNumber').val());

    if (passengerFirstName.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please input first name.';
    }

    if (passengerLastName.length === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please input last name.';
    }

    if (seatNumber === 0) {
        message = addNextLineSymbolIfNotEmpty(message);
        message += 'Please pick your seat.';
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
            message += 'Please pick your seat using picker. Fraud is illegal action';
        }
    }

    if (message.length !== 0) {
        swal({
            title: "Validation of entered ticket data failed.",
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
                swal({
                        title: i18n['ticket.paymentWindowTitle'] + ' ' + data.bookedTicketTotalPrice + '$',
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: '#DD6B55',
                        confirmButtonText: 'Yes, confirm payment',
                        cancelButtonText: "No, i will pay later",
                        // closeOnConfirm: false
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            payForTicket(data.bookedTicketId);
                        } else {
                            alert("You can pay for ticket or discard booking on page tickets.");
                            // swal hide to fast here it should work
                            // swal({
                            //     title: "Info.",
                            //     text: "You can pay for ticket or discard booking on page tickets.",
                            //     type: "info",
                            //     confirmButtonText: "OK",
                            //     closeOnConfirm: false
                            // });
                            // debugger;
                        }
                        // forceDataTableReload();
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
            message += 'Please select departure airport for filter from drop-down list or leave it empty.';
            departureAirportCondition.val('');
            departureAirportCondition.addClass('valid');
        }

        // ;
        if (!arrivalAirportCondition.hasClass('valid') && !(arrivalAirportCondition.val().length === 0)) {
            message = addNextLineSymbolIfNotEmpty(message);
            message += 'Please select arrival airport for filter from drop-down list or leave it empty.';
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
            message += 'Departure and arrival airports can\'t be the same.';
        }


        if (fromDateTimeValue.length !== 0 && toDateTimeValue.length !== 0) {
            var fromDateTime = new Date(fromDateTimeValue);
            var toDateTime = new Date(toDateTimeValue);

            if (fromDateTime > toDateTime) {
                message = addNextLineSymbolIfNotEmpty(message);
                message += '"from" date should be earlier than "dto" date!! Please reselect values!';
            }
        }

        if (message.length !== 0) {
            swal({
                title: "Validation of entered data in filter failed.",
                text: message,
                // type: "error",
                confirmButtonText: "OK"
            });
            $('.datatable').attr("hidden", true);
        } else {
            forceDataTableReload();
            // $('.datatable').attr("hidden", false);
        }
    } else {
        forceDataTableReload();
        // $('.datatable').attr("hidden", false);
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