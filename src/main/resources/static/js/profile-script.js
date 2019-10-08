$(function () {
    $("#error").css("display","none");

    $("#profileForm").submit(function (event) {
        let password = $("#pw").val();
        let newPassword = $("#newPw").val();
        let newPasswordRepeat = $("#repeatPw").val();

        if(newPassword === "" || newPasswordRepeat === "" ||newPassword !== newPasswordRepeat){
            event.preventDefault();
            $("#error").css("display","block");
            return false;
        }
    });
});