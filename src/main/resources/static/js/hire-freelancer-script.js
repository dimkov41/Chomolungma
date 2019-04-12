$(function () {
    $("#dateError").css("display", "none");

    $("#hire").click(function (e) {
        e.preventDefault();

        $("#hire").css("display", "none");
        $("#dateForm").fadeIn();
    });


    $("#dateForm").submit(function (event) {
        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val();

        let hiredDays = datediff(new Date(startDate), new Date(endDate)) + 1;

        if(hiredDays < 1){
            ///
            $("#dateError").css("display", "block");
            e.preventDefault();
        }

        confirm("Потвърдете наемането на планинският водач.");
    });


    function datediff(first, second) {
        // Take the difference between the dates and divide by milliseconds per day.
        // Round to nearest whole number to deal with DST.
        return Math.round((second-first)/(1000*60*60*24));
    }
});