$(function () {
        $("#error").css("display", "none");

        if (window.location.search === "?error=true") {
            $("#error").css("display", "block");
        }

        $("#actualForm").submit(function (event) {
            let name = $("#name").val();
            let description = $("#description").val();
            let image = $("#image").val();

            if (name === "" ||
                description === "" ||
                image === "") {
                event.preventDefault();
                $("#error").css("display", "block");
                return false;
            }
            return true;
        })
    }
);