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


function addToCart(id, productName, price, productQuantity, userQuantity) {
    console.log("Product ID:", id);
    console.log("Product Name:", productName);
    console.log("Product Price:", price);
    console.log("Product Quantity:", productQuantity);
    console.log("User Quantity:", userQuantity);

    // Construct the product object
    const product = {
        id: id,
        price: price,
        name: productName,
        userQuantity: userQuantity
        // Add more properties as needed
    };

    if (userQuantity > productQuantity) {
        alert("Maximum quantity of this product to buy is " + productQuantity);
        return;
    }

    const xhr = new XMLHttpRequest();
    const url = '/ecommerce/addToCart';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log('Product added to cart successfully.');
            } else {
                console.error('Error adding product to cart:', xhr.statusText);
            }
        }
    };

    xhr.onerror = function () {
        console.error('Network error while adding product to cart:', xhr.statusText);
    };

    // Retrieve existing cart items from local storage or initialize an empty array
    let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];

    // Add the new product to the cart
    cartItems.push(product);

    // Save the updated cart items back to local storage
    localStorage.setItem('cartItems', JSON.stringify(cartItems));

    xhr.send(JSON.stringify({
        productId: id,
        userQuantity: userQuantity,
        totalPrice: price * userQuantity
    }));
}

// productElement.innerHTML = `
//                             <div class="rounded position-relative fruite-item">
//                                 <div class="fruite-img">
//                                     <img src="data:image/png;base64,${arrayBufferToBase64(productDTO.productImage)}" class="img-fluid rounded-top product-image" alt="${productDTO.productName}" style="width: 200px; height: 200px;">
//                                 </div>
//                                 <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">${productDTO.category}</div>
//                                 <div class="p-4 border border-secondary border-top-0 rounded-bottom">
//                                     <h4>${productDTO.productName}</h4>
//                                     <p>${productDTO.description}</p>
//                                     <div class="d-flex justify-content-between align-items-center flex-wrap">
//                                         <p class="text-dark fs-5 fw-bold mb-0">$${productDTO.price} / kg</p>
//                                         <div class="d-flex align-items-center mb-3">
//                                             <label for="quantity-${productDTO.id}" class="me-2">Quantity:</label>
//                                             <input type="number" id="quantity-${productDTO.id}" class="form-control" value="1" min="1" max="${productDTO.quantity}">
//                                         </div>
//                                         <button class="btn border border-secondary rounded-pill px-3 text-primary"
//                                         onclick="addToCart('${productDTO.id}', '${productDTO.productName}',
//                                         '${productDTO.price}', '${productDTO.quantity}',
//                                         document.getElementById('quantity-${productDTO.id}').value)">Add to Cart</button>
//                                     </div>
//                                 </div>
//                             </div>
//                         `;

// document.addEventListener("DOMContentLoaded", function() {
//     var xhr = new XMLHttpRequest();
//     xhr.open("GET", "showCart", true);
//     xhr.setRequestHeader("Content-Type", "application/json");
//     xhr.onreadystatechange = function() {
//         if (xhr.readyState === 4) {
//             if (xhr.status === 200) {
//                 var data = JSON.parse(xhr.responseText);
//
//             } else {
//                 console.error("Error fetching user data");
//             }
//         }
//     };
//     xhr.send();
// });


//USED ONE
// function addToCart(id, productName, price, quantity) {
//     console.log("Product ID:", id);
//     console.log("Product Price:", price);
//     console.log("Product Name:", productName);
//     console.log("Quantity:", quantity);
//
//     // Construct the product object
//     const product = {
//         id: id,
//         price: price,
//         name: productName,
//         quantity: quantity
//         // Add more properties as needed
//     };
//
//     // Retrieve existing cart items from local storage or initialize an empty array
//     let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
//
//     // Add the new product to the cart
//     cartItems.push(product);
//
//     // Save the updated cart items back to local storage
//     localStorage.setItem('cartItems', JSON.stringify(cartItems));
//
//     console.log('Product added to cart successfully.');
// }


//
// function addToCart(productId, productPrice){
//     console.log("Product ID:", productId);
//     const xhr = new XMLHttpRequest();
//     const url = '/ecommerce/addToCart';
//
//     xhr.open('POST', url, true);
//     xhr.setRequestHeader('Content-Type', 'application/json');
//
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === 4) {
//             if (xhr.status === 200) {
//                 console.log('Product added to cart successfully.');
//             } else {
//                 console.error('Error adding product to cart:', xhr.statusText);
//             }
//         }
//     };
//
//     xhr.onerror = function () {
//         console.error('Network error while adding product to cart:', xhr.statusText);
//     };
//
//     xhr.send(JSON.stringify({
//         productId: productId,
//         productPrice: productPrice
//     }));
// }
//
