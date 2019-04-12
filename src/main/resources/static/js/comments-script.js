$(function () {
    $("#error").css("display", "none");

    $("#form").submit(function (e) {
        e.preventDefault();

        let comment = $("#comment").val();

        if (comment === "" || comment.length > 255) {
            $("#error").css("display", "block");
            return false;
        }

        let lastSlashIndex = window.location.pathname.lastIndexOf("/");
        let freelancerId = window.location.pathname.substring(lastSlashIndex + 1);

        $.ajax({
            url: "http://localhost:8080/comment/" + freelancerId,
            data: {
                comment
            },
            method: "POST"
        })
            .done(function (data) {
                $("#error").css("display", "none");
                console.log(data);
            })
            .fail(function (data) {
                console.log("fail");
                console.log(data);
            })
    });
});