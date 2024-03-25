document.addEventListener("DOMContentLoaded", function () {
    console.log("Document loaded");

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "showCart", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var totalCost = 0; // Initialize totalCost
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
                    var quantityInput = document.createElement("input");
                    quantityInput.setAttribute("id", "cartItemQuantity"+productNameCell.textContent);
                    quantityInput.type = "number";
                    quantityInput.value = item.quantity;

                    // if(item.quantity < item.productDTO.quantity){
                    //     quantityInput.value = item.quantity;
                    // }else{
                    //     quantityInput.value = item.productDTO.quantity;
                    //     alert("Maximum quantity of " + item.productDTO.productName +"S to buy is " + item.productDTO.quantity);
                    // }

                    quantityInput.min = "1";
                    quantityInput.max = item.productDTO.quantity;
                    quantityInput.classList.add("quantity-input");
                    quantityCell.appendChild(quantityInput);

                    var priceCell = document.createElement("td");
                    priceCell.textContent = item.productDTO.price;

                    var totalCell = document.createElement("td");
                    totalCell.textContent = item.totalPrice;

                    // Update totalCost
                    totalCost += parseFloat(item.totalPrice);

                    // Create buttons for adjusting quantity
                    var quantityAdjustCell = document.createElement("td");

                    // Increment item
                    var plusButton = document.createElement("button");
                    plusButton.textContent = "+";
                    plusButton.id = "plusButton_" + item.productDTO.id; // Set ID for plus button
                    plusButton.classList.add("btn", "btn-primary", "quantity-adjust-btn");
                    plusButton.addEventListener("click", function () {
                        quantityInput.value = parseInt(quantityInput.value) + 1;
                        item.quantity = quantityInput.value;
                        updateItemQuantity(item);
                        updateTotalCost(); // Update total cost when quantity changes
                    });

                    // Decrement item
                    var minusButton = document.createElement("button");
                    minusButton.textContent = "-";
                    minusButton.id = "minusButton_" + item.productDTO.id; // Set ID for minus button
                    minusButton.classList.add("btn", "btn-primary", "quantity-adjust-btn");
                    minusButton.addEventListener("click", function () {
                        if (parseInt(quantityInput.value) > 1) {
                            quantityInput.value = parseInt(quantityInput.value) - 1;
                            item.quantity = quantityInput.value;
                            updateItemQuantity(item);
                            updateTotalCost(); // Update total cost when quantity changes
                        }
                    });
                    quantityAdjustCell.appendChild(minusButton);
                    quantityAdjustCell.appendChild(quantityInput);
                    quantityAdjustCell.appendChild(plusButton);

                    // Create a remove button
                    var removeButtonCell = document.createElement("td");
                    var removeButton = document.createElement("button");
                    removeButton.textContent = "Remove";
                    removeButton.classList.add("btn", "btn-danger", "remove-btn"); // Add the specified classes
                    removeButton.addEventListener("click", function () {
                        removeItemFromCart(row, item.productDTO.id);
                        updateItemQuantity(quantityInput.value);
                        updateTotalCost(); // Update total cost when item is removed
                    });
                    removeButtonCell.appendChild(removeButton);

                    // Append cells to the row
                    row.appendChild(productNameCell);
                    row.appendChild(quantityAdjustCell);
                    row.appendChild(priceCell);
                    row.appendChild(totalCell);
                    row.appendChild(removeButtonCell); // Append the remove button cell to the row

                    // Append the row to the tbody
                    tbody.appendChild(row);
                });

                function updateTotalCost() {
                    var totalCostElement = document.getElementById("totalCost");
                    totalCostElement.textContent = "Total Cost: $" + totalCost.toFixed(2);
                }

                updateTotalCost(); // Call updateTotalCost to initially display total cost
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


function updateItemQuantity(item){
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "updateCartItem", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log(item.quantity);
    console.log(item.productDTO.productName);
    console.log(item.productDTO.price);
    console.log(item.productDTO.id);
    console.log(item.cartId);

    // if(item.quantity < item.productDTO.quantity){
    //     quantityInput.value = item.quantity;
    // }else{
    //     quantityInput.value = item.productDTO.quantity;
    //     alert("Maximum quantity of the " + item.productDTO.productName +" product to buy is " + item.productDTO.quantity);
    // }

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("showCart.js -> item quantity: updates sent successfully");
            }else {
                console.error("showCart.js -> Error send item quantity updates");
            }
        }
    }
    xhr.send(JSON.stringify({
        productId: item.productDTO.id,
        cartId: item.cartId,
        totalPrice: item.productDTO.price,
        userQuantity: item.quantity
    }))
}