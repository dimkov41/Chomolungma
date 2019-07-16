$(function () {
    $("#error").css("display","none");

    $("#profileForm").submit(function (event) {
        let newPassword = $("#newPw").val();
        let newPasswordRepeat = $("#repeatPw").val();

        if(newPassword !== newPasswordRepeat){
            event.preventDefault();
            $("#error").css("display","block");
            return false;
        }
    });
});