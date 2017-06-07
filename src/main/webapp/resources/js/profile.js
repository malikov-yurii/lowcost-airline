$(function() {
    // $('#userDTO').on('submit', validateProfileForm);

});

function validateProfileForm() {
    var errorCount = 0;
    var $firstName = $('#firstName');
    var $lastName = $('#LastName');
    var $phoneNumber = $('#phoneNumber');


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


    if (errorCount) {
        return false;
    }
}