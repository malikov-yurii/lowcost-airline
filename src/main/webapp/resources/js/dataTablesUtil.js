function showAddModal() {
    $('#modalTitle').html('Add new ' + entityName);
    $('.form-control').val('');
    $('#editRow').modal();
}

function forceDataTableReload() {
    datatableApi.ajax.reload();
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
    swal({
            title: i18n['common.areYouSureWantToChangeCancellingStatus'],
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: 'Yes, I am sure!',
            cancelButtonText: "No, cancel it!",
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    url: ajaxUrl + id + '/set-canceled',
                    type: 'POST',
                    data: 'canceled=' + isCanceled,
                    success: function (data) {
                        updateTable(true, false);
                        successNoty('common.saved');
                    }
                });
            }
        });
}

function showUpdateModal() {
    var rowData = datatableApi.row($(this).closest('tr')).data();

    $('#modalTitle').html(i18n['common.update'] + ' ' + entityName);

    // ;

    $('.form-control.modal-input').addClass('valid');
    $('.form-control.modal-input').removeClass('in-process');

    for (var key in rowData) {
        var $node = $('#' + key);
        if ($node.length) $node.val(rowData[key]);
    }

    $('#editRow').modal();
}


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
    swal({
            title: i18n['common.areYouSureWantToDelete'],
            text: "This is final delete. No way to restore data after confirmation",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: 'Yes, I am sure',
            cancelButtonText: "No, cancel it",
        },
        function (isConfirm) {
            if (isConfirm) {
                $.ajax({
                    url: ajaxUrl + id,
                    type: 'DELETE',
                    success: function (data) {
                        updateTable(true, false);
                        successNoty('common.deleted');
                    }
                })
            }
        });
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: $('#detailsForm').serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable(false, false);
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


function onTableReady() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}