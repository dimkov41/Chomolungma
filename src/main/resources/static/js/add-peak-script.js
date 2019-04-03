$(function () {
        $("#actualForm").submit(function (event) {
            let name = $("#name").val();
            let elevation = $("#elevation").val();
            let description = $("#description").val();
            let image = $("#image").val();

            if (name === "" ||
                elevation === "" ||
                description === "" ||
                image === "") {
                event.preventDefault();
                $("#error").css("display","block");
                return false;
            }

            return true;
        })
    }
);