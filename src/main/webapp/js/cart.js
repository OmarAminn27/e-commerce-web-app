function addToCart(id, productName, price, productQuantity, userQuantity) {
    productQuantity = parseInt(productQuantity);
    userQuantity = parseInt(userQuantity);
    console.log("Product Quantity:", productQuantity);
    console.log("User Quantity:", userQuantity);

    // Construct the product object
    const product = {
        id: id,
        price: price,
        name: productName,
        userQuantity: userQuantity
    };

    if (productQuantity === 0) {
        alert("Out of stock!");
        return;
    }

    // Retrieve cart map from cookie if it exists, otherwise initialize it
    let cartMap = {};
    const cookieValue = document.cookie.replace(/(?:(?:^|.*;\s*)cartMap\s*=\s*([^;]*).*$)|^.*$/, "$1");
    if (cookieValue) {
        cartMap = JSON.parse(decodeURIComponent(cookieValue));
    }

    // Check if product already exists in cart, if yes, increase its quantity, otherwise add it to cart
    if (cartMap[id]) {
        cartMap[id] += userQuantity;
    } else {
        cartMap[id] = userQuantity;
    }

    // Save updated cart map back to cookie
    document.cookie = "cartMap=" + encodeURIComponent(JSON.stringify(cartMap));

    const xhr = new XMLHttpRequest();
    const url = 'addToCart';

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

    xhr.send(JSON.stringify({
        productId: id,
        userQuantity: userQuantity,
        totalPrice: price * userQuantity
    }));
}

// add to local storage
// add to cart
// add quantity w productID hashmap
// upon sending we should clear local storage
// login (servlet)
// fetch local storage and update cart