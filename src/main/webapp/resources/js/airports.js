var datatableApi;
var entityName = 'user';
var ajaxUrl = 'ajax/admin/airport/';




// todo single result here!!!!! draw airport, its update and delete button after filtering




$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "ajax": {
            "url": ajaxUrl,
            "data": function (d) {
                return {nameCondition: $('#nameCondition').val()}
            },
            "dataSrc": ""
        },

        // "iDeferLoading": 0,
        "searching": false,
        // !!!!!!!!!!!! todo hide .disabled paginate_button and every paginate button if recordsTotal <= length
        "paging": false,
        "info": false,
        "columns": [
            {"data": "id", "orderable": false},
            {"data": "name", "orderable": false},
            {"data": "cityName", "orderable": false},
            {"orderable": false, "render": renderUpdateBtn},
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


    var $nameCondition = $('#nameCondition');
    $nameCondition.autocomplete({
        source: 'ajax/anonymous/airport/autocomplete-by-name'
    });
    $nameCondition
        .on("autocompleteselect",
            function (event, ui) {
                var $this = $(this);
                $this.addClass('valid in-process');
                $this.blur();
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

function showOrUpdateTable(forceUpdate, nextPreviousPage, added, isTabPressed, orderId) {
    var message = "";
    var $nameCondition = $('#nameCondition');

    if (!($nameCondition.val().length === 0) || forceUpdate) {

        if (!$nameCondition.hasClass('valid') && !($nameCondition.val().length === 0)) {
            message += 'Please select airport name for filter from drop-down.';
            $nameCondition.val('');
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

function save() {

    // todo add validation here using example in commetnts

    /*
     var message = "";

     if (!$('#aircraftName').hasClass('valid')) {
     message += 'Please select aircraft from drop-down list.';
     }

     var departureAirport = $('#departureAirport');
     var arrivalAirport = $('#arrivalAirport');
     if (!departureAirport.hasClass('valid')) {
     message = addNextLineSymbolIfNotEmpty(message);
     message += 'Please select departure airport from drop-down list.';
     }
     if (!arrivalAirport.hasClass('valid')) {
     message = addNextLineSymbolIfNotEmpty(message);
     message += 'Please select arrival airport from drop-down list.';
     }

     if (departureAirport.val() === arrivalAirport.val()
     && departureAirport.val().length !== 0 && arrivalAirport.val().length !== 0) {
     message = addNextLineSymbolIfNotEmpty(message);
     // departureAirport.val('');
     // departureAirport.removeClass('valid');
     // arrivalAirport.val('');
     // arrivalAirport.removeClass('valid');
     message += 'Departure and arrival airports can\'t be the same.';
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

     var initialBaseTicketPrice = $('#initialBaseTicketPrice');
     var maxBaseTicketPrice = $('#maxBaseTicketPrice');
     var initialBaseTicketPriceInt = parseInt(initialBaseTicketPrice.val(), 10);
     var maxBaseTicketPriceInt = parseInt(maxBaseTicketPrice.val(), 10);
     if (initialBaseTicketPriceInt > maxBaseTicketPriceInt) {
     message = addNextLineSymbolIfNotEmpty(message);
     message += 'Initial price cannot be greater than max ticket price.';
     }

     if (initialBaseTicketPriceInt < 5) {
     message = addNextLineSymbolIfNotEmpty(message);
     message += 'Initial price cannot be less than 5.00$';
     }

     if (message.length !== 0) {
     swal({
     title: "Validation of entered data failed.",
     text: message,
     // type: "error",
     confirmButtonText: "OK"
     });
     } else {
     $('.modal-input.valid, .modal-input.in-process').removeClass('valid in-process');
     $('.in-process').removeClass('in-process');

     */
    $.ajax({
        type: "PUT",
        url: ajaxUrl,
        data: $('#detailsForm').serialize(),
        success: function () {
            // ;
            $('#editRow').modal('hide');
            showOrUpdateTable(true, false);
            successNoty('common.saved');
            // ;
        }
    });

    // } // end of last else

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