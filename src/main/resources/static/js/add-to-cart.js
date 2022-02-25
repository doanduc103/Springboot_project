$(document).ready(function () {
    $("#buttonAdd2Cart").on("click", function (e) {
        addToCart();
    })
})

var productId = "[[${product.id}]]";
var contextPath = "[[@{/}]]";
var crsfHeaderName = "[[${_csrf.headerName}]]";
var csrfValue = "[[${_csrf.token}]]";

function addToCart() {
   var quantity = $("#quantity" + productId).val();

    url = contextPath + "cart/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(crsfHeaderName, csrfValue);
        }
    }).done(function (response) {
        $("#modalTitle").text("Shopping Card");
        $("#modalBody").text(response);
        $("#myModal").modal();
    }).fail(function () {
        $("#modalTitle").text("Shopping Card");
        $("#modalBody").text("Có lỗi khi thêm vào giỏ hàng.");
        $("#modalBody").modal();
    });
}