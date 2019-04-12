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
                comment,
                currentDate: getDate()
            },
            method: "POST"
        })
            .done(function (data) {
                $("#error").css("display", "none");
                $("#comment").val("");
            })
            .fail(function (data) {
                console.log("fail");
                console.log(data);
            })
    });

    function getDate() {
        let today = new Date();
        let dd = today.getDate();
        let mm = today.getMonth() + 1; //January is 0!

        let yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }

        let hours = today.getHours();
        let minutes = today.getMinutes();
        let seconds = today.getSeconds();
        return dd + '/' + mm + '/' + yyyy + ' ' + hours + ':' + minutes + ':' + seconds;
    }
});