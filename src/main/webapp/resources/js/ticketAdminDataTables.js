var datatableApi;
var entityName = 'ticket';
var ajaxUrl = 'ajax/admin/ticket/';

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
                    userEmailCondition: $('#userEmailCondition').val()

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
            {"data": "price", "render": appendDecimalsAndDollarSign, "orderable": false},
            {"data": "status", "orderable": false},
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



    var $userEmailCondition = $('#userEmailCondition');
    $userEmailCondition.autocomplete({
        source: 'ajax/admin/user/autocomplete-by-email'
    });
    $userEmailCondition
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
    var $userEmailCondition = $('#userEmailCondition');

    if (!($userEmailCondition.val().length === 0) || forceUpdate) {

        if (!$userEmailCondition.hasClass('valid') && !($userEmailCondition.val().length === 0)) {
            message += i18n['common.selectUserEmailFromDropdown'];
            $userEmailCondition.val('');
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
            $('.datatable').attr("hidden", false);
            forceDataTableReload();
        }
    } else {
        $('.datatable').attr("hidden", false);
        forceDataTableReload();
    }
}

function save() {

    var errorCount = 0;
    var $form = $('#detailsForm');
    var id = $form.find('#id');
    var $firstName = $form.find('#passengerFirstName');
    var $lastName = $form.find('#passengerLastName');


    // Adds to counter if firstname is less than 2 symbols OR contains digits
    if ($firstName.val().length < 2 || $firstName.val().match(/\d+/g)) {
        if ($firstName.siblings('.error-text').length == 0) {
            $firstName
                .closest('.form-group')
                .addClass('error')
                .find('div')
                .append('<div class="error-text">'+ i18n["common.inputCorrectFirstName"] +'</div>');
        }
        errorCount++;
    } else {
        $firstName
            .closest('.form-group')
            .removeClass('error')
            .find('.error-text')
            .remove();
    }


    // Adds to counter if lastname is less than 2 symbols OR contains digits
    if ($lastName.val().length < 2 || $lastName.val().match(/\d+/g)) {
        if ($lastName.siblings('.error-text').length == 0) {
            $lastName
                .closest('.form-group')
                .addClass('error')
                .find('div')
                .append('<div class="error-text">'+ i18n["common.inputCorrectLastName"] +'</div>');
        }
        errorCount++;
    } else {
        $lastName
            .closest('.form-group')
            .removeClass('error')
            .find('.error-text')
            .remove();
    }


    if (!errorCount) {
        $.ajax({
            type: "PUT",
            url: ajaxUrl,
            data: $form.serialize(),
            success: function () {
                $('#editRow').modal('hide');
                showOrUpdateTable(true, false);
                successNoty('common.saved');
            }
        });
    }

    return false;


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