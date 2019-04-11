$(document).ready(function () {
    $(".editor-header a").click(function (e) {
        e.preventDefault();

        var _val = $(this).data("role"),
            _sizeValIn = parseInt($(this).data("size-val") + 1),
            _sizeValRe = parseInt($(this).data("size-val") - 1),
            _size = $(this).data("size");
        if (_size === "in-size") {
            document.execCommand(_val, false, _sizeValIn + "px");
        } else {
            document.execCommand(_val, false, _sizeValRe + "px");
        }
    });
});

$(document).ready(function () {
    var $text = $("#text"),
        $submit = $("input[type='button']"),
        $listComment = $(".list-comments"),
        $loading = $(".loading"),
        _data,
        $totalCom = $(".total-comment");

    $totalCom.text($(".list-comments > div").length);

    $("#error").css("display", "none");

    $($submit).click(function () {
        let comment = $text[0].innerText;

        if (comment === "" || comment.length > 255) {
            $("#error").css("display", "block");
            $text.focus();
        } else {

            let lastSlashIndex = window.location.pathname.lastIndexOf("/");
            let freelancerId = window.location.pathname.substring(lastSlashIndex + 1);

            _data = $text.html();
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/comment/" + freelancerId,
                data: _data,
                cache: false,
                success: function (isSaved) {
                    $loading.show().fadeOut(300);
                    if (isSaved) {
                        $listComment.append("<div>" + _data + "</div>");
                        $text.html("");
                        $totalCom.text($(".list-comments > div").length);
                    }
                }
            });
            return false;
        }
    });
});