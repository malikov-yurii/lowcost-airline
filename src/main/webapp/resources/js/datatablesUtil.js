function showAddModal() {
    $('#modalTitle').html('Add new ' + entityName);
    $('.form-control').val('');
    $('#editRow').modal();
}


function renderUpdateBtn(data, type, row) {
    return '<a class="btn btn-xs btn-primary update-btn">Update</a>';
}

function renderCancelDiscardCancellingBtn(data, type, row) {
    return row.canceled === false ?
        '<a class="cancel-btn btn btn-xs btn-danger" onclick="setCanceled(' + row.id + ', ' + true + ');">' + i18n['common.cancel'] + '</a>' :
        '<a class="cancel-btn btn btn-xs btn-success" onclick="setCanceled(' + row.id + ', ' + false + ');">' + i18n['common.discardCancelling'] + '</a>';
}

function setCanceled(id, isCanceled) {
    // debugger;
    $.ajax({
        url: ajaxUrl + id + '/set-canceled',
        type: 'POST',
        data: 'canceled=' + isCanceled,
        success: function (data) {
            updateTable(true);
            successNoty('common.saved');
        }
    })
}

function showUpdateModal() {
    var rowData = datatableApi.row($(this).closest('tr')).data();

    $('#modalTitle').html(i18n['common.update'] + ' ' + entityName);

    // debugger;
    $('.form-control.modal-input').addClass('valid in-process');

    for (var key in rowData) {
        var $node = $('#'+key);
        if ($node.length) $node.val(rowData[key]);
    }

    $('#editRow').modal();
}

function renderUpdateUserBtn(data, type, row) {
    var result = '<a class="btn btn-xs btn-primary" onclick="showUpdateUserModal(' +
        row.id + ', \'' +
        row.firstName + '\', \'' +
        row.lastName + '\', \'' +
        row.login + '\', \'' +
        row.email +
        '\', \'';

    if (entityName === 'freelancer')
        result += row.skills;
    result += '\')">'+ i18n['common.update'] + '</a>';

    console.log(result);
    return result;
}

function showUpdateUserModal(id, firstName, lastName, login, email, skills) {
    $('#modalTitle').html(i18n['common.update'] + entityName);
    $('#id').val(id);
    $('#firstName').val(firstName);
    $('#lastName').val(lastName);
    $('#login').val(login);
    $('#password').val('');
    $('#email').val(email);
    if (entityName === 'freelancer')
        $('#skills').val(skills);
    $('#editRow').modal();

}

// function updateTable(added, isTabPressed, orderId) {
//     $.get(ajaxUrl, updateTableByData);
// }


function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function showAddFlightModal() {
    $('#modalTitle').html(i18n['common.addNew'] + entityName);
    $('#id').val(0);
    $('#firstName').val('');
    $('#lastName').val('');
    $('#login').val('');
    $('#password').val('');
    $('#email').val('');

    $('#editRow').modal();
}

function renderDeleteBtn(data, type, row) {
    return '<a class="btn btn-xs btn-danger" onclick="deleteEntity(' + row.id + ');">' + i18n['common.delete'] + '</a>';
}

function deleteEntity(id) {
    if (confirm(i18n['common.areYouSureWantToDelete'])) {
        $.ajax({
            url: ajaxUrl + id,
            type: 'DELETE',
            success: function (data) {
                updateTable();
                successNoty('common.deleted');
            }
        })
    }
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: $('#detailsForm').serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('common.saved');
        }
    });
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function getDateTimePickerFormat() {
    return "Y-m-d H:i";
}

function dateToString(date) {
    return date.toJSON().slice(0, 16).replace('T', ' ');
}