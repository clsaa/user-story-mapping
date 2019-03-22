$(function () {

});

function register() {
    $.ajax({
        type: 'POST',
        url: "/v1/register",
        contentType: "application/json",
        data: JSON.stringify({
            "nickname": $("#name").val(),
            "username": $("#email").val(),
            "email": $("#email").val(),
            "password": $("#password").val()
        }),
        success: function (data) {
            console.log(JSON.stringify(data));
            $(window).attr('location', 'login.html');
        },
        error: function (data) {
            alert(data.responseJSON.message)
        }
    });
}

function modifier() {
    $.ajax({
        type: 'PUT',
        url: "/v1/register",
        contentType: "application/json",
        data: JSON.stringify({
            "email": $("#email").val(),
            "oldPassword": $("#old-password").val(),
            "password": $("#password").val()
        }),
        success: function (data) {
            console.log(JSON.stringify(data));
            $(window).attr('location', 'login.html');
        },
        error: function (data) {
            alert(data.responseJSON.message)
        }
    });
}