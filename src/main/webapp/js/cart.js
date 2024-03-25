function addToCart(id, productName, price, productQuantity, userQuantity) {
    // console.log("Product ID:", id);
    // console.log("Product Name:", productName);
    // console.log("Product Price:", price);
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
