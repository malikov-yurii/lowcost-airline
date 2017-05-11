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

function updateTable(added, isTabPressed, orderId) {
    $.get(ajaxUrl, updateTableByData);
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function showAddUserModal() {
    $('#modalTitle').html(i18n['common.addNew'] + entityName);
    $('#id').val(0);
    $('#firstName').val('');
    $('#lastName').val('');
    $('#login').val('');
    $('#password').val('');
    $('#email').val('');

    $('#editRow').modal();
}

function renderBlockUnblockBtn(data, type, row) {
    return row.blocked === false ?
    '<a class="btn btn-xs btn-danger" onclick="block(' + row.id + ');">' + i18n['common.block'] + '</a>' :
    '<a class="btn btn-xs btn-success" onclick="unblock(' + row.id + ');">' + i18n['common.unblock'] + '</a>';
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

function block(id) {
    $.ajax({
        url: ajaxUrl + id + '/block',
        type: 'POST',
        success: function (data) {
            updateTable();
            successNoty('common.saved');
        }
    })
}

function unblock(id) {
    $.ajax({
        url: ajaxUrl + id + '/unblock',
        type: 'POST',
        success: function (data) {
            updateTable();
            successNoty('common.saved');
        }
    })
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

function onProjectTableReady() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}