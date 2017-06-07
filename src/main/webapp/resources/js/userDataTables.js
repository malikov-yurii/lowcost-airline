var datatableApi;
var entityName = 'user';
var ajaxUrl = 'ajax/admin/user/';

$(document).ready(function () {
    datatableApi = $('#datatable').DataTable({
        "dom": "ft<'row'<'dataTables_length_wrap'l>><'row'<'col-md-6'p>>",
        "ajax": {
            "url": ajaxUrl,
            "data": function (d) {
                return {lastNameCondition: $('#lastNameCondition').val()}
            },
            "dataSrc": ""
        },

        "searching": false,
        "paging": false,
        "info": false,
        "columns": [
            {"data": "id", "orderable": false},
            {"data": "firstName", "orderable": false},
            {"data": "lastName", "orderable": false},
            {"data": "email", "orderable": false},
            {"data": "phoneNumber", "orderable": false},
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


    var $lastNameCondition = $('#lastNameCondition');
    $lastNameCondition.autocomplete({
        source: 'ajax/admin/user/autocomplete-by-last-name'
    });
    $lastNameCondition
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

    $('#detailsForm').on('submit', save);

});

function showOrUpdateTable(forceUpdate, nextPreviousPage, added, isTabPressed, orderId) {
    var message = "";
    var $lastNameCondition = $('#lastNameCondition');

    if (!($lastNameCondition.val().length === 0) || forceUpdate) {

        if (!$lastNameCondition.hasClass('valid') && !($lastNameCondition.val().length === 0)) {
            message += i18n['common.selectUserLastNameFromDropdown'];
            $lastNameCondition.val('');
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

function save(e) {

    var errorCount = 0;
    var $form = $(e.target);
    var id = $form.find('#id');
    var $firstName = $form.find('#firstName');
    var $lastName = $form.find('#lastName');
    var $phoneNumber = $form.find('#phoneNumber');


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


    var phoneNumberArray = $phoneNumber.val().split('+');
    // Adds to errorcounter if phone number doesn't start with + OR
    // contains not only numbers or doesn't contain any value at all
    // is less than 11 symbols OR
    // is more than 14 symbols
    if (
        phoneNumberArray[0] != '' ||
        !/^\d+$/.test(phoneNumberArray[1]) ||
        phoneNumberArray[1].length < 11 ||
        phoneNumberArray[1].length > 14
    ) {

        if ($phoneNumber.siblings('.error-text').length == 0) {
            $phoneNumber
                .closest('.form-group')
                .addClass('error')
                .find('div')
                .append('<div class="error-text"' +
                    'title="'+ i18n["common.phoneNumberFormat"] +
                    '">'+ i18n["common.inputCorrectPhoneNumber"] +'</div>');
        }
        errorCount++;
    } else {
        $phoneNumber
            .closest('.form-group')
            .removeClass('error')
            .find('.error-text')
            .remove();
    }


    if (!errorCount) {
        $.ajax({
            type: id != 0 ? "PUT" : "POST",
            url: ajaxUrl,
            data: $('#detailsForm').serialize(),
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