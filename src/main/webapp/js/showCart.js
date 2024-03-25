document.addEventListener("DOMContentLoaded", function () {
    console.log("Document loaded");

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "showCart", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var data = JSON.parse(xhr.responseText);
                var tbody = document.querySelector("#cartTable tbody");
                tbody.innerHTML = '';
                data.forEach(function (item) {
                    console.log("Item:", item); // Log the entire item object

                    // Create a new row
                    var row = document.createElement("tr");

                    // Create table cells for each field
                    var productNameCell = document.createElement("td");
                    productNameCell.textContent = item.productDTO.productName;

                    var quantityCell = document.createElement("td");
                    quantityCell.textContent = item.quantity;

                    var priceCell = document.createElement("td");
                    priceCell.textContent = item.productDTO.price;

                    var totalCell = document.createElement("td");
                    totalCell.textContent = item.totalPrice;

                    // Create a remove button
                    var removeButtonCell = document.createElement("td");
                    var removeButton = document.createElement("button");
                    removeButton.textContent = "Remove";
                    removeButton.classList.add("btn", "btn-danger", "remove-btn"); // Add the specified classes
                    removeButton.addEventListener("click", function (){
                        removeItemFromCart(row, item.productDTO.id);
                    });
                    removeButtonCell.appendChild(removeButton);

                    // Append cells to the row
                    row.appendChild(productNameCell);
                    row.appendChild(quantityCell);
                    row.appendChild(priceCell);
                    row.appendChild(totalCell);
                    row.appendChild(removeButtonCell); // Append the remove button cell to the row

                    // Append the row to the tbody
                    tbody.appendChild(row);
                });
            } else {
                console.error("Error fetching user data");
            }
        }
    };
    xhr.send();
});

function removeItemFromCart(row, itemId){
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "removeCartItem", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function (){
        if (xhr.readyState === 4){
            if (xhr.status === 200){
                console.log("showCart.js -> Cart Item sent for removal");
            }
        }
    }
    row.remove();
    xhr.send(JSON.stringify({
        itemId: itemId
    }));
}
