$(function () {

});

function register() {
    $.ajax({
        type: 'POST',
        url: "http://localhost:7788/v1/register",
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