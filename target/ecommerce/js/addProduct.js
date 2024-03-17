// Function to add a new row for adding a product
function addProductRow() {
    var tableBody = document.querySelector('#product-table tbody');

    // Construct the new row HTML with input fields
    var newRow = '<tr>' +
        '<td><input type="text" class="form-control"></td>' + // Name
        '<td><input type="text" class="form-control"></td>' + // Image URL
        '<td><input type="number" class="form-control"></td>' + // Quantity
        '<td><input type="text" class="form-control"></td>' + // Description
        '<td><input type="number" class="form-control"></td>' + // Price
        '<td><input type="text" class="form-control"></td>' + // Category
        '<td><button class="btn btn-primary add-btn">Add</button></td>' + // Add button
        '</tr>';

    // Insert the new row at the top of the table
    tableBody.insertAdjacentHTML('afterbegin', newRow);

    // Add event listener to the add button of the new row
    var addButton = tableBody.firstElementChild.querySelector('.add-btn');
    addButton.addEventListener('click', function () {
        // Create a product DTO object with input field values
        var productDTO = {
            productName: tableBody.firstElementChild.querySelector('td:nth-child(1) input').value,
            // productImage: tableBody.firstElementChild.querySelector('td:nth-child(2) input').value,
            quantity: tableBody.firstElementChild.querySelector('td:nth-child(3) input').value,
            description: tableBody.firstElementChild.querySelector('td:nth-child(4) input').value,
            price: tableBody.firstElementChild.querySelector('td:nth-child(5) input').value,
            category: tableBody.firstElementChild.querySelector('td:nth-child(6) input').value
        };

        // Send the product data to the servvar
        var xh2 = new XMLHttpRequest();
        xh2.open('POST', '/ecommerce/addProduct', true);
        xh2.setRequestHeader("Content-type", "application/json");
        xh2.onload = function () {
            if (xh2.status >= 200 && xh2.status < 300) {
                console.log('Product added successfully');
            } else {
                console.error('Failed to add product');
            }
        };
        xh2.onerror = function () {
            console.error('xh2 request failed');
        };
        xh2.send(JSON.stringify(productDTO));
        setTimeout(function() {
            location.reload();
        }, 500); 
    });
}

// Clear input fields after adding the product
function clearInputFields() {
    var inputs = document.querySelectorAll('#product-table tbody input');
    inputs.forEach(function (input) {
        input.value = '';
    });
}

// Add event listener to the add product button
document.getElementById('add-product-btn').addEventListener('click', addProductRow);
