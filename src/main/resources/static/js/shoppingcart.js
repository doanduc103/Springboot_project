$(document).ready(function () {
    $(".minusButton").on("click", function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });
    $(".plusButton").on("click", function (evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });

    updateTotal();
});

function decreaseQuantity(link) {
    productID = link.attr("pid");
    qtyInput = $("#quantity" + productID);
    newQty = parseInt(qtyInput.val()) - 1;
    if (newQty > 0) {
        qtyInput.val(newQty);
        updateQuantity(productID, newQty);
    }
}

function increaseQuantity(link) {
    productID = link.attr("pid");
    qtyInput = $("#quantity" + productID);
    newQty = parseInt(qtyInput.val()) + 1;
    if (newQty > 0) {
        qtyInput.val(newQty);
        updateQuantity(productID, newQty);
    }
}

function updateQuantity(productID, quantity) {

    let url = "cart/update/" + productID + "/" + quantity;
    $.ajax({
        type: "POST",
        url: url,

    }).done(function (newSubtotal) {
        updateSubtotal();
        updateTotal();
    }).fail(function () {
        alert('error');
    });

}

function updateSubtotal() {

}

function updateTotal(productId, quantity) {
    let total = 0.0;

    $(".productSubtotal").each(function (index, element) {
        total = total + parseFloat(element.innerHTML);
    });

    $("#totalPrice").text(total + "VND");
}