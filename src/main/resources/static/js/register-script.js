const usernameInputFieldId = "username";
const passwordInputFieldId = "pw";
const confirmPasswordInputFieldId = "confirmPw";
const emailInputFieldId = "email";

const usernameWrapperId = "usernameWrapper";
const passwordWrapperId = "passwordWrapper";
const confirmPasswordWrapperId = "confirmPasswordWrapper";
const emailWrapperId = "emailWrapper";

let usernameValidity = false;
let passwordValidity = false;
let confirmPasswordValidity = false;
let emailValidity = false;

$(function () {
    validateUsername();
    validatePassword();
    validateConfirmPassword();
    validateEmail();

    $(".animated").click(function () {
        checkIfFieldsIsEmpty();

        $("#register-form").submit(function (event) {
            if(usernameValidity && passwordValidity && confirmPasswordValidity && emailValidity){
                return true;
            }
            event.preventDefault();
            return false;
        })
    });
});

function validateUsername() {
    const path = "/users/checkUsername";

    $("#" + usernameInputFieldId).focusout(function () {
        let username = $("#" + usernameInputFieldId).val();

        if (username !== "") {
            makeAjaxCall(path, username)
                .done(function (data) {
                    usernameValidity = handleData(data, usernameValidity, usernameWrapperId, usernameInputFieldId);
                });
        } else {
            notValid(usernameWrapperId, usernameInputFieldId);
            usernameValidity = false;
        }
    });
}

function validatePassword() {
    $("#" + passwordInputFieldId).focusout(function () {
        let password = $("#" + passwordInputFieldId).val();

        if (password !== "") {
            isValid(passwordWrapperId, passwordInputFieldId);
            passwordValidity = true;
        } else {
            notValid(passwordWrapperId, passwordInputFieldId);
            passwordValidity = false;
        }
    });
}

function validateConfirmPassword() {
    $("#" + confirmPasswordInputFieldId).focusout(function () {
        let password = $("#" + passwordInputFieldId).val();
        let confirmPassword = $("#" + confirmPasswordInputFieldId).val();

        if (password === confirmPassword && confirmPassword !== "") {
            isValid(confirmPasswordWrapperId, confirmPasswordInputFieldId);
            confirmPasswordValidity = true;
        } else {
            notValid(confirmPasswordWrapperId, confirmPasswordInputFieldId);
            confirmPasswordValidity = false;
        }
    });
}

function validateEmail() {
    const path = "/users/checkEmail";

    $("#" + emailInputFieldId).focusout(function () {
        let email = $("#" + emailInputFieldId).val();
        let regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if (regex.test(String(email).toLowerCase())) {
            makeAjaxCall(path, email)
                .done(function (data) {
                    emailValidity = handleData(data, emailValidity, emailWrapperId, emailInputFieldId);
                });
        } else {
            notValid(emailWrapperId, emailInputFieldId);
            emailValidity = false;
        }
    });
}

function notValid(wrapperId, inputFieldId) {
    $("#" + wrapperId + " .fa-check").css("display", "none");
    $("#" + inputFieldId).css("border-bottom", "2px solid red");
    $("#" + wrapperId + " .fa-times").css("display", "inline");
}

function isValid(wrapperId, inputFieldId) {
    $("#" + wrapperId + " .fa-times").css("display", "none");
    $("#" + inputFieldId).css("border-bottom", "2px solid green");
    $("#" + wrapperId + " .fa-check").css("display", "inline");
}

function makeAjaxCall(path, param) {
    return $.ajax({
        url: "http://localhost:8080" + path + "/" + param,
        method: "GET"
    });
}

function handleData(data, validity, wrapperId, inputFieldId) {
    if (data) {
        //if such username exists
        notValid(wrapperId, inputFieldId);
        validity = false;
    } else {
        isValid(wrapperId, inputFieldId);
        validity = true;
    }

    return validity;
}

function checkIfFieldsIsEmpty() {
    let username = $("#" + usernameInputFieldId).val();
    let password = $("#" + passwordInputFieldId).val();
    let confirmPassword = $("#" + confirmPasswordInputFieldId).val();
    let email = $("#" + emailInputFieldId).val();

    if (username === "") {
        notValid(usernameWrapperId, usernameInputFieldId);
    }
    if (password === "") {
        notValid(passwordWrapperId, passwordInputFieldId);
    }
    if (confirmPassword === "") {
        notValid(confirmPasswordWrapperId, confirmPasswordInputFieldId);
    }
    if (email === "") {
        notValid(emailWrapperId, emailInputFieldId);
    }
}