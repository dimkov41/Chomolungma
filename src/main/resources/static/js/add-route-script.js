$(function () {
        $("#error").css("display", "none");
        $("#descriptionError").css("display","none");

        if(window.location.search === "?error=true"){
            $("#error").css("display", "block");
        }

        $("#actualForm").submit(function (event) {
            let start = $("#start").val();
            let stop = $("#stop").val();
            let elevation = $("#lenght").val();
            let description = $("#description").val();
            let mountain = $("#mountain").val();

            if(description.length > 80){
                $("#descriptionError").css("display", "block");
                event.preventDefault();
                return false;
            }

            if (elevation === "" ||
                elevation < 1 ||
                description === "" ||
                mountain === null ||
                start === "" ||
                stop === "") {
                event.preventDefault();
                $("#error").css("display", "block");
                return false;
            }

            return true;
        })
    }
);