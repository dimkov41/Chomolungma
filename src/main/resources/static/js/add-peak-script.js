$(function () {
        $("#error").css("display", "none");

        if (window.location.search === "?error=true") {
            $("#error").css("display", "block");
        }

        $("#actualForm").submit(function (event) {
            let name = $("#name").val();
            let elevation = $("#elevation").val();
            let description = $("#description").val();
            let image = $("#image").val();
            let mountain = $("#mountain").val();

            if (name === "" ||
                elevation === "" ||
                description === "" ||
                image === "" ||
                mountain === null) {
                event.preventDefault();
                $("#error").css("display", "block");
                return false;
            }

            return true;
        })
    }
);