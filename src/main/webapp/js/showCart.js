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

                    // Append cells to the row
                    row.appendChild(productNameCell);
                    row.appendChild(quantityCell);
                    row.appendChild(priceCell);
                    row.appendChild(totalCell);

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
