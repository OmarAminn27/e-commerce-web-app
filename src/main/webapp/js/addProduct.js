let isAdded = false;
// Function to add a new row for adding a product
function addProductRow() {
    // check if row already added once dont add it again
    if (isAdded) {
        console.log('row already added');
        return;
    }
    isAdded = true;
    var tableBody = document.querySelector('#product-table tbody');

    // Construct the new row HTML with input fields
    var newRow = '<tr>' +
        '<td><input type="text" class="form-control"></td>' + // Name
        '<td>' +
        '<label for="img_file" class="btn btn-primary">Choose Image</label>' +
        '<input id="img_file" type="file" accept="image/*" style="visibility:hidden;">' +
        '</td>' + // Image URL
        '<td><input type="number" class="form-control"></td>' + // Quantity
        '<td><input type="text" class="form-control"></td>' + // Description
        '<td><input type="number" class="form-control"></td>' + // Price
        `<td>
            <select class="form-control">
                <option value="Fruits">FRUITS</option>
                <option value="Vegetables">VEGETABLES</option>
                <option value="Herbs">HERBS</option>
            </select>
        </td>` + // Category
        '<td><button class="btn btn-primary add-btn">Add</button></td>' + // Add button
        '</tr>';

    // Insert the new row at the top of the table
    tableBody.insertAdjacentHTML('afterbegin', newRow);

    // Add event listener to the add button of the new row
    var addButton = tableBody.firstElementChild.querySelector('.add-btn');
    addButton.addEventListener('click', function () {
        var fileInput = tableBody.firstElementChild.querySelector('td:nth-child(2) input');
        var file = fileInput.files[0];

        if (!file) {
            console.error('No file selected');
            return;
        }

        var reader = new FileReader();
        reader.onload = function (e) {
            var base64String = e.target.result;

            var productDTO = {
                productName: tableBody.firstElementChild.querySelector('td:nth-child(1) input').value,
                productImage: base64String,
                quantity: tableBody.firstElementChild.querySelector('td:nth-child(3) input').value,
                description: tableBody.firstElementChild.querySelector('td:nth-child(4) input').value,
                price: tableBody.firstElementChild.querySelector('td:nth-child(5) input').value,
                category: tableBody.firstElementChild.querySelector('td:nth-child(6) select').value
            };


            // Validate product data
            if (!productDTO.productName || !productDTO.quantity || !productDTO.description || !productDTO.price || !productDTO.category) {
                console.error('All fields must be filled out');
                return;
            }
            if (isNaN(productDTO.quantity) || isNaN(productDTO.price)) {
                console.error('Quantity and price must be numbers');
                return;
            }
            if (productDTO.quantity < 0 || productDTO.price < 0) {
                console.error('Quantity and price must be positive');
                return;
            }
            // if(productDTO.category !== 'Fruits' && productDTO.category !== 'Vegetables' && productDTO.category !== 'HERBS'){
            console.log(productDTO);

            // Send the product data to the server
            var xh2 = new XMLHttpRequest();
            xh2.open('POST', 'addProduct', true);
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


            setTimeout(function () {
                location.reload();
            }, 1000);
        };
        reader.readAsDataURL(file);
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
