var datatableApi;
var entityName = 'ticket';
var ajaxUrl = 'ajax/user/ticket/';

$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "processing": true,
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "lengthMenu": [5, 10, 25],
        "serverSide": true,
        "ajax": {
            "url": ajaxUrl,
            "data": function (d) {
                return {
                    draw: d.draw,
                    length: d.length,
                    start: d.start,
                    userEmailCondition: $('#userEmailCondition').val()

                };
            }
        },
        "searching": false,
        "pagingType": "simple_numbers",
        "paging": true,
        "info": true,
        "columns": [
            {"data": "id", "orderable": false},
            {"data": "departureAirport", "orderable": false},
            {"data": "departureCity", "orderable": false},
            {"data": "arrivalAirport", "orderable": false},
            {"data": "arrivalCity", "orderable": false},
            {"data": "departureLocalDateTime", "orderable": false},
            {"data": "arrivalLocalDateTime", "orderable": false},
            {"data": "passengerFirstName", "orderable": false},
            {"data": "passengerLastName", "orderable": false},
            {"data": "hasPriorityRegistrationAndBoarding", "orderable": false},
            {"data": "hasBaggage", "orderable": false},
            {"data": "seatNumber", "orderable": false},
            {"data": "price", "render": appendDecimalsAndDollarSign, "orderable": false},
            {"render": renderTicketStatus, "orderable": false},
            {"orderable": false, "render": renderPayBtn},
            {"orderable": false, "render": renderDiscardBookingBtn}
        ],
        "initComplete": onTicketsTableReady,
        "drawCallback": onTicketsTableReady,
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "autoWidth": false
    });

    $('.datatable').attr('hidden', false);
});

function showArchivedTickets(){
    $(".page-title").html(i18n['ticket.archivedTickets']);
    $(".show-active").removeClass('active');
    $(".show-archived").addClass('active');
    datatableApi.ajax.url( 'ajax/user/ticket/archived/' ).load();
}

function showActiveTickets(){
    $(".page-title").html(i18n['ticket.activeTickets']);
    $(".show-active").addClass('active');
    $(".show-archived").removeClass('active');
    datatableApi.ajax.url( 'ajax/user/ticket/' ).load();
}

function renderDiscardBookingBtn(data, type, row) {
    return row.status === 'BOOKED'
        ? '<a class="btn btn-xs btn-danger" onclick="confirmBookingCancelling(' + row.id + ');">' + i18n["ticket.discardBooking"] + '</a>'
        : '';

}

function confirmBookingCancelling(id) {
    swal({
            title: i18n['ticket.areYouSureCancelBooking'],
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: i18n['ticket.confirmCancelBooking'],
            cancelButtonText: i18n['ticket.discardCancelBooking']
        },
        function (isConfirm) {
            if (isConfirm) {
                cancelBooking(id);
                popup(i18n['ticket.bookingCancel']);
            }

        });

}

function renderPayBtn(data, type, row) {
    return row.status === 'BOOKED'
        ? '<a class="btn btn-xs btn-danger" onclick="confirmPayment(' + row.id + ');">' + i18n["ticket.pay"] + '</a>'
        :'';
}

function confirmPayment(id) {
    swal({
            title: i18n['ticket.areYouSurePay'],
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: i18n['ticket.confirmPay'],
            cancelButtonText: i18n['common.no']
        },
        function (isConfirm) {
            if (isConfirm) {
                payForTicket(id);
                popup(i18n['ticket.purchaseSuccess']);
            }
        });
}


function renderTicketStatus(data, type, row) {
    if (row.status === 'BOOKED') {
        return i18n['ticket.bookedFor'] + ': ' + renderTimer(row.remainingDelay);
    } else {
        return row.status;
    }
}

function onTicketsTableReady() {
    if (timerExists) {
        startTimer();
    }
    onTableReady();
}