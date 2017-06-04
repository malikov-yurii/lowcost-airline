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
                // debugger;
                return {
                    draw: d.draw,
                    length: d.length,
                    start: d.start,
                    // todo    consider rename condition as filter or criteria
                    userEmailCondition: $('#userEmailCondition').val()

                };
            }
            // ,"dataSrc": ""
        },
        "searching": false,
        // !!!!!!!!!!!! todo hide .disabled paginate_button and every paginate button if recordsTotal <= length
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
            {"data": "withPriorityRegistrationAndBoarding", "orderable": false},
            {"data": "withBaggage", "orderable": false},
            {"data": "seatNumber", "orderable": false},
            {"data": "price", "orderable": false},
            {"data": "status", "orderable": false},
 // todo we have a "data.remainingDelay" here for clock
            {"orderable": false, "render": renderPayBtn},
            {"orderable": false, "render": renderDiscardBookingBtn}
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

    $('.datatable').attr('hidden', false);
});

function showArchivedTickets(){
    $(".page-title").html('Archived tickets');
    $(".show-active").show();
    $(".show-archived").hide();
    datatableApi.ajax.url( 'ajax/user/ticket/archived/' ).load();
    // forceDataTableReload();
}

function showActiveTickets(){
    $(".page-title").html('Active tickets');
    $(".show-active").hide();
    $(".show-archived").show();
    datatableApi.ajax.url( 'ajax/user/ticket/' ).load();
    // forceDataTableReload();
}

function renderDiscardBookingBtn(data, type, row) {
    return row.status === 'BOOKED'
        ? '<a class="btn btn-xs btn-danger" onclick="confirmBookingCancelling(' + row.id + ');">' + /*i18n['common.delete']*/ 'discard booking' + '</a>'
        :'';

}

function confirmBookingCancelling(id) {
    swal({
            title: /*i18n['ticket.paymentWindowTitle']*/ 'Are you sure to cancel booking?',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: 'Yes, confirm booking cancelling',
            cancelButtonText: "Discard cancelling"
        },
        function (isConfirm) {
            if (isConfirm) {
                cancelBooking(id);
            }

        });

}

function renderPayBtn(data, type, row) {
    return row.status === 'BOOKED'
        ? '<a class="btn btn-xs btn-danger" onclick="confirmPayment(' + row.id + ');">' + /*i18n['common.delete']*/ 'pay' + '</a>'
        :'';
}

function confirmPayment(id) {
    swal({
            title: /*i18n['ticket.paymentWindowTitle']*/ 'Are you sure to pay for ticket?',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: 'Yes, confirm payment',
            cancelButtonText: "No"
        },
        function (isConfirm) {
            if (isConfirm) {
                payForTicket(id);
            }

        });
}

