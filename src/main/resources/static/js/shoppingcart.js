$(document).ready(function () {
    $(".minusButton").on("click", function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });
    $(".plusButton").on("click", function (evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });

    $(".link-remove").on("click",function (evt){
        evt.preventDefault();
        removeFromcart($(this));
    })

    updateTotal();
});

function removeFromcart(link){
    let url = link.attr("href");
    $.ajax({
        type: "POST",
        url: url,

    }).done(function (response) {
        let rowNumber;
        if (response.includes("xóa")) {
            alert("Bạn đã xóa sản phẩm khỏi giỏ hàng");
            rowNumber = link.attr("rowNumber");
            removeProduct(rowNumber);
            updateTotal();
        }
    }).fail(function () {
        alert('error');
    });

}

function removeProduct(rowNumber){
    let rowId = "row" + rowNumber;
    $("#" + rowId).remove();
}

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
        updateSubtotal(newSubtotal,productID);
        updateTotal();
    }).fail(function () {
        alert('error');
    });

}

function updateSubtotal(newSubtotal,productID) {
$("#subtotal" + productID).text(newSubtotal);
}

function updateTotal(productId, quantity) {
    let total = 0.0;

    $(".productSubtotal").each(function (index, element) {
        total = total + parseFloat(element.innerHTML);
    });

    $("#totalPrice").text(total + "VND");
}