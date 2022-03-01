$(document).ready(function () {
    $("#buttonAdd2Cart").on("click", function (e) {
        addToCart();
    })
})

var contextPath = "[[@{/}]]";
var crsfHeaderName = "[[${_csrf.headerName}]]";
var csrfValue = "[[${_csrf.token}]]";

function addToCart() {
    const quantity = $("#quantity" + productId).val();

    url = "cart/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        // beforeSend: function (xhr) {
        //     xhr.setRequestHeader(crsfHeaderName, csrfValue);
        // }
    }).done(function (response) {
        // $("#modalTitle").text("Shopping Card");
        // $("#modalBody").text(response);
        // $("#myModal").modal();
        alert('Ok');
    }).fail(function () {
        // $("#modalTitle").text("Shopping Card");
        // $("#modalBody").text("Có lỗi khi thêm vào giỏ hàng.");
        // $("#myModal").modal();
        alert('error');
    });
}