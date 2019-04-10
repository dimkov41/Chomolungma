$(function () {
    let lastSlashIndex = window.location.pathname.lastIndexOf("/");
    let page = window.location.pathname.substring(lastSlashIndex + 1);

    $(".pagination a").each(function (index) {
        let currentAnchor = $(this)[0];
        $(currentAnchor).removeClass("active");

        if(currentAnchor.innerText === page){
            $(currentAnchor).addClass("active");
        }
    });
});