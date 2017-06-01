var datatableApi;
var entityName = 'purchase';
var ajaxUrl = 'ajax/user/purchase/';

$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "processing": true,
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "lengthMenu": [3, 5, 10],
        "serverSide": true,
        "ajax": {
            "url": 'ajax/user/flight/',
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

    // $('#modalTitle').html('Purchase ticket');
    // debugger;

    $.ajax({
        type: "GET",
        url: 'ajax/user/ticket/details-with-free-seats/',
        data: {
            'flightId': rowData.id
        },
        success: function (data) {
            renderSeatPicker(data);

            $('#departureAirport').val(data.departureAirport);
            $('#arrivalAirport').val(data.arrivalAirport);
            $('#departureCity').val(data.departureCity);
            $('#arrivalCity').val(data.arrivalCity);
            $('#departureLocalDateTime').val(data.departureLocalDateTime);
            $('#arrivalLocalDateTime').val(data.arrivalLocalDateTime);
            $('#withBaggage').prop("checked", false);
            $('#withPriorityRegistration').prop("checked", false);
            $('#price').val(data.price);
            $('#passengerFirstName').val('');
            $('#passengerLastName').val('');

            $('#editRow').modal();
        }
    });


    // todo implement what if error??? (when last ticket just has been bought)

}

function save() {
    $('#editRow').modal('hide');
    $.ajax({
        url: 'ajax/user/ticket/',
        type: 'POST',
        data: $('#detailsForm').serialize(),
        success: function (data) {
            // debugger;
            $('#id').val(data);

            swal({
                    title: i18n['ticket.paymentWindowTitle'],
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Yes, confirm payment',
                    cancelButtonText: "No, cancel it"
                },
                function (isConfirm) {
                    if (isConfirm) {

                        // debugger;
                        dateToOffsetString(new Date());
                        $.ajax({
                            url: 'ajax/user/ticket/' + $('#id').val() + '/confirm-payment',
                            type: 'PUT',
                            data: {'purchaseOffsetDateTime':dateToOffsetString(new Date())},
                            success: function () {
                                swal({
                                    title: "Payment success",
                                    text: "Ticket has been successfully purchased. You can access it in your profile",
                                    confirmButtonText: "OK"
                                });
                            }
                        });
                    } else {
                        $.ajax({
                            url: 'ajax/user/ticket/' + $('#id').val() + '/cancel-booking',
                            type: 'PUT',
                            success: function () {
                                swal({
                                    title: "Cancel",
                                    text: "Ticket booking has been canceled",
                                    confirmButtonText: "OK"
                                });

                            }
                        });

                    }
                });
        }

    });


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
                message += '"from" date should be earlier than "to" date!! Please reselect values!';
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
            $('.datatable').attr("hidden", false);
            forceDataTableReload();
        }
    } else {
        $('.datatable').attr("hidden", false);
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