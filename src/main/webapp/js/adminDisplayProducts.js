// Function to send XHR request and update the table with products data
function displayProducts() {
    // Create an XHR object
    var xhr = new XMLHttpRequest();

    // Define the request parameters
    xhr.open('GET', '/ecommerce/displayProducts', true);

    // Set up event listener to handle the response
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            // Parse JSON response
            var products = JSON.parse(xhr.responseText);

            // Clear existing table rows
            var tableBody = document.querySelector('#product-table tbody');
            tableBody.innerHTML = '';

            // Loop through the products and append them to the table
            products.forEach(function (product, index) {
                // Convert image data to Base64
                var base64Image = 'data:image/jpeg;base64,' + product.productImage;

                var newRow = '<tr>' +
                    '<td><span>' + product.productName + '</span><input type="text" style="display:none;"></td>' +
                    '<td><img src="' + base64Image + '" alt="' + product.productName + '" style="width: 100px; height: 100px;"></td>' +
                    '<td><span>' + product.quantity + '</span><input type="text" style="display:none;"></td>' +
                    '<td><span>' + product.description + '</span><input type="text" style="display:none;"></td>' +
                    '<td><span>' + product.price + '</span><input type="text" style="display:none;"></td>' +
                    '<td><span>' + product.category + '</span><input type="text" style="display:none;"></td>' +
                    '<td><button class="btn btn-primary save-btn" style="display:none;">Save</button><button class="btn btn-primary edit-btn">Edit</button></td>' + // Edit button
                    '</tr>';
                tableBody.innerHTML += newRow;
            });

            // Add event listeners to edit buttons
            var editButtons = document.querySelectorAll('.edit-btn');
            editButtons.forEach(function (button, index) {
                button.addEventListener('click', function () {
                    var row = button.closest('tr');

                    // Hide edit button
                    button.style.display = 'none';
                    // Show save button
                    var saveButton = row.querySelector('.save-btn');
                    saveButton.style.display = 'inline-block';

                    // Loop through row cells, excluding the first two (product name and image) and the last one (edit/save button)
                    row.querySelectorAll('td:not(:nth-child(1)):not(:nth-child(2)):not(:last-child)').forEach(function (cell) {
                        var span = cell.querySelector('span');
                        var input = cell.querySelector('input');

                        // Check if span and input elements exist
                        if (span && input) {
                            // Set input value to span content
                            input.value = span.textContent;
                            // Hide span, show input
                            span.style.display = 'none';
                            input.style.display = 'inline-block';
                        }
                    });
                });
            });

            // Add event listeners to save buttons
            var saveButtons = document.querySelectorAll('.save-btn');
            saveButtons.forEach(function (button, index) {
                button.addEventListener('click', function () {
                    var row = button.closest('tr');

                    // Hide save button
                    button.style.display = 'none';
                    // Show edit button
                    var editButton = row.querySelector('.edit-btn');
                    editButton.style.display = 'inline-block';

                    // Loop through row cells, excluding the first two (product name and image) and the last one (edit/save button)
                    row.querySelectorAll('td:not(:nth-child(1)):not(:nth-child(2)):not(:last-child)').forEach(function (cell) {
                        var span = cell.querySelector('span');
                        var input = cell.querySelector('input');

                        // Check if span and input elements exist
                        if (span && input) {
                            // Set span content to input value
                            span.textContent = input.value;
                            // Hide input, show span
                            input.style.display = 'none';
                            span.style.display = 'inline-block';
                        }
                    });

                    // Implement your save logic here
                    console.log('Changes saved for row ' + (index + 1));
                });
            });

            // Add event listeners to save buttons
            var saveButtons = document.querySelectorAll('.save-btn');
            saveButtons.forEach(function (button, index) {
                button.addEventListener('click', function () {
                    var row = button.closest('tr');
                    var product = {
                        productName: row.querySelector('td:first-child span').textContent,
                        quantity: row.querySelector('td:nth-child(3) span').textContent,
                        description: row.querySelector('td:nth-child(4) span').textContent,
                        price: row.querySelector('td:nth-child(5) span').textContent,
                        category: row.querySelector('td:nth-child(6) span').textContent
                    };

                    console.log(product);
                    // Create an XHR object
                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/ecommerce/updateProduct', true);
                    xhr.setRequestHeader("Content-type", "application/json");
                    xhr.onload = function () {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            console.log('Product updated successfully');
                        } else {
                            console.error('Failed to update product');
                        }
                    };
                    xhr.onerror = function () {
                        console.error('XHR request failed');
                    };

                    // Send the request with product data as JSON string
                    xhr.send(JSON.stringify(product));
                });
            });
        } else {
            console.error('Request failed');
        }
    };

    // Send the request
    xhr.send();
}

// Call the function when the page is loaded
window.onload = function () {
    displayProducts();
};