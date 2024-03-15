window.onload = function () {
    console.log("reached home js");
    const xhr = new XMLHttpRequest();
    const url = '/ecommerce/displayProducts';

    xhr.open('GET', url, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    const productDTOs = JSON.parse(xhr.responseText);

                    const productsDiv = document.getElementById('products');

                    productDTOs.forEach(productDTO => {
                        const productElement = document.createElement('div');
                        productElement.classList.add('col-md-6', 'col-lg-6', 'col-xl-4');

                        productElement.innerHTML = `
                            <div class="rounded position-relative fruite-item">
                                <div class="fruite-img">
                                    <img src="data:image/png;base64,${arrayBufferToBase64(productDTO.productImage)}" class="img-fluid rounded-top product-image" alt="${productDTO.productName}" style="width: 200px; height: 200px;">
                                </div>
                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">${productDTO.category}</div>
                                <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                    <h4>${productDTO.productName}</h4>
                                    <p>${productDTO.description}</p>
                                    <div class="d-flex justify-content-between flex-lg-wrap">
                                        <p class="text-dark fs-5 fw-bold mb-0">$${productDTO.price} / kg</p>
                                        <a href="#" class="btn border border-secondary rounded-pill px-3 text-primary"><i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart</a>
                                    </div>
                                </div>
                            </div>
                        `;

                        productsDiv.appendChild(productElement);
                    });
                } catch (error) {
                    console.error('Error parsing JSON:', error);
                }
            } else {
                console.error('Error loading products:', xhr.statusText);
            }
        }
    };
    xhr.onerror = function () {
        console.error('Network error while loading products:', xhr.statusText);
    };

    xhr.send();
};

// Function to convert byte array to base64
function arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
}
