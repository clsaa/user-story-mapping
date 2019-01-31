window.onload = function () {
    console.log("index load finished");
    load_ok_usm();
    count();
};

function count() {
    $.ajax({
        type: 'GET',
        async: true,
        url: "/v1/usms?stauts=DELETED",
        success: function (data) {
            $("#delete_usm_count").text(data.length);
        }
    });
}

function load_ok_usm() {
    $.ajax({
        type: 'GET',
        async: true,
        url: "/v1/usms?stauts=OK",
        success: function (data) {
            if (data.toString().startsWith('<!DOCTYPE html>')) {
                window.location.reload()
            }
            $("#ok_usm_count").text(data.length);
            $("#ok_list_btn").addClass("active");
            $("#deleted_list_btn").removeClass("active");
            $("#ok_usm_list").removeClass("hidden");
            $("#deleted_usm_list").addClass("hidden");
            $("#ok_usm_list tbody").children().remove();
            for (i in data) {
                item = data[i];
                $("#ok_usm_list tbody").append(
                    '<tr>' +
                    '<td>' + (i) + '</td>' +
                    '<td class="hidden">' + item.id + '</td>' +
                    '<td>' + '<a href='+'"/usm.html?id='+item.id+'"'+'>'+item.name+'</a>' + '</td>' +
                    '<td>' + item.description + '</td>' +
                    '<td class="hidden">' + item.data + '</td>' +
                    '<td>' + item.mtime + '</td>' +
                    '<td>' +
                    '<button type="button" class="btn btn-danger" value="DELETED" item_id="' + item.id + '"' +
                    'item_id=' + '"' + item.id + '"' +
                    'item_name=' + '"' + item.name + '"' +
                    'item_description=' + '"' + item.description + '"' +
                    'item_data=' + '"' + item.data + '"' +
                    ' onclick="update_usm(this)" >' +
                    'delete</button>' +
                    '</td>' +
                    '</tr>'
                )
            }
        },
        error: function (data) {
            console.log(data)
        },
        complete: function (data, textStatus) {
            console.log(data.status);
        }
    });
}

function load_deleted_usm() {
    $.ajax({
        type: 'GET',
        async: true,
        url: "/v1/usms?stauts=DELETED",
        success: function (data) {
            if (data.toString().startsWith('<!DOCTYPE html>')) {
                window.location.reload();
            }
            $("#delete_usm_count").text(data.length);
            $("#ok_list_btn").removeClass("active");
            $("#deleted_list_btn").addClass("active");
            $("#ok_usm_list").addClass("hidden");
            $("#deleted_usm_list").removeClass("hidden");
            $("#deleted_usm_list tbody").children().remove();
            for (i in data) {
                item = data[i];
                $("#deleted_usm_list tbody").append(
                    '<tr>' +
                    '<td>' + (i) + '</td>' +
                    '<td class="hidden">' + item.id + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' + item.description + '</td>' +
                    '<td class="hidden">' + item.data + '</td>' +
                    '<td>' + item.mtime + '</td>' +
                    '<td>' +
                    '<button type="button" class="btn btn-success" value="OK" item_id="' + item.id + '"' +
                    'item_id=' + '"' + item.id + '"' +
                    'item_name=' + '"' + item.name + '"' +
                    'item_description=' + '"' + item.description + '"' +
                    'item_data=' + '"' + item.data + '"' +
                    ' onclick="update_usm(this)" >' +
                    'recover</button>' +
                    '</td>' +
                    '</tr>'
                )
            }
        },
        error: function (data) {
            console.log(data)
        },
        complete: function (data, textStatus) {
            console.log(data.status);
        }
    });
}

function update_usm(value) {
    var id = value.getAttribute('item_id');
    var name = value.getAttribute('item_name');
    var description = value.getAttribute('item_description');
    var data = value.getAttribute('item_data');
    var status = value.value;
    if (data == undefined || data.length == 0) {
        data = ""
    }
    $.ajax({
        type: 'PUT',
        url: "/v1/usms/" + id,
        contentType: "application/json",
        data: JSON.stringify({
            "name": name,
            "description": description,
            "data": data,
            "status": status
        }),
        success: function (data) {
            console.log(JSON.stringify(data));
            load_ok_usm();
        },
        error: function (data) {
            alert(data.responseJSON.message)
        }
    });
}

function new_usm() {
    $.ajax({
        type: 'POST',
        url: "/v1/usms?" +
            "name=" + $("#new_usm_name").val() +
            "&description=" + $("#new_usm_description").val(),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            window.location.reload();
        },
        error: function (data) {
            alert(data.responseJSON.message);
        }
    });
}