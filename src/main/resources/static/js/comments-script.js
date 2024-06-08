var objJson = null;

$(function () {
    $("#error").css("display", "none");

    let lastSlashIndex = window.location.pathname.lastIndexOf("/");
    let freelancerId = window.location.pathname.substring(lastSlashIndex + 1);

    loadComments();

    $("#form").submit(function (e) {
        console.log("submitting form")
        e.preventDefault();

        const formData = $(this).serialize(); // Serialize the form data
        const formObject = formData.split('&').reduce(function(obj, item) {
            let parts = item.split('=');
            obj[decodeURIComponent(parts[0])] = decodeURIComponent(parts[1]);
            return obj;
        }, {});
        let comment = $("#comment").val();

        if (comment === "" || comment.length > 255) {
            $("#error").css("display", "block");
            return false;
        }

        $.ajax({
            url: window.location.origin + "/comment/" + freelancerId,
            data: {
                ...formObject,
                currentDate: getDate()
            },
            method: "POST"
        })
            .done(function (data) {
                $("#error").css("display", "none");
                $("#comment").val("");
                loadComments();
            })
            .fail(function (data) {
                $("#error").css("display", "block");
            })
    });

    function loadComments() {
        $.ajax({
            url: window.location.origin + "/comment/show/" + freelancerId,
            method: "GET"
        })
            .done(function (data) {
                objJson = JSON.parse(data);
                changePage(1);
            });
    }

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

var current_page = 1;
var records_per_page = 3;

function prevPage()
{
    if (current_page > 1) {
        current_page--;
        changePage(current_page);
    }
}

function nextPage()
{
    if (current_page < numPages()) {
        current_page++;
        changePage(current_page);
    }
}

function changePage(page)
{
    var btn_next = document.getElementById("btn_next");
    var btn_prev = document.getElementById("btn_prev");
    var listing_table = document.getElementById("listingTable");
    var page_span = document.getElementById("page");

    // Validate page
    if (page < 1) page = 1;
    if (page > numPages()) page = numPages();

    listing_table.innerHTML = "";

    for (var i = (page-1) * records_per_page; i < (page * records_per_page); i++) {
        if(objJson[i] !== undefined) {
            listing_table.innerHTML += `<div class="comment-container">
                <p class="comment-content">${objJson[i].comment}</p>
                <p class="comment-left">${objJson[i].date}</p>
                <p class="comment-right">${objJson[i].userCreated}</p>
            </div>`;
        }
    }
    page_span.innerHTML = page;

    if (page == 1) {
        btn_prev.style.visibility = "hidden";
    } else {
        btn_prev.style.visibility = "visible";
    }

    if (page == numPages()) {
        btn_next.style.visibility = "hidden";
    } else {
        btn_next.style.visibility = "visible";
    }
}

function numPages()
{
    return Math.ceil(objJson.length / records_per_page);
}