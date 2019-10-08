$(function () {
    $("#dateError").css("display", "none");
    $("#wrongDate").css("display", "none");

    $("#hire").click(function (e) {
        e.preventDefault();

        $("#hire").css("display", "none");
        $("#dateForm").fadeIn();
    });


    $("#dateForm").submit(function (event) {
        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val();

        let hiredDays = datediff(new Date(startDate), new Date(endDate)) + 1;

        if (hiredDays < 1) {
            ///
            $("#dateError").css("display", "block");
            event.preventDefault();
            return false;
        }

        $("#dateError").css("display", "none");

        let lastSlashIndex = window.location.pathname.lastIndexOf("/");
        let freelancerId = window.location.pathname.substring(lastSlashIndex + 1);

        let isValid = false;
        $.ajax({
            url: "http://localhost:8080/mountainguides/check/" + freelancerId,
            method: "POST",
            data: {
                startDate: formatDate(startDate),
                endDate: formatDate(endDate)
            },
            async: false
        })
            .done(function (data) {
                if (data) {
                    isValid = true;
                } else {
                    isValid = false;
                    $("#wrongDate").css("display", "block");
                }
            });

        if (isValid) {
            if (!confirm("Потвърдете наемането на планинският водач.")) {
                event.preventDefault();
                return false;
            }
        } else {
            event.preventDefault();
            return false;
        }
    });


    function datediff(first, second) {
        // Take the difference between the dates and divide by milliseconds per day.
        // Round to nearest whole number to deal with DST.
        return Math.round((second - first) / (1000 * 60 * 60 * 24));
    }

    function formatDate(date) {
        let d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }
});