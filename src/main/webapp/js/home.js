let productDTOs;
const productsDiv = document.getElementById('products');

class Pagination {
    constructor(itemsPerPage, items) {
        this.itemsPerPage = itemsPerPage;
        this.items = items;
        this.currentPage = 1;
    }

    getCurrentPageItems() {
        console.log(this.currentPage);
        if (!this.items) {
            return this.items;
        }
        const start = (this.currentPage - 1) * this.itemsPerPage;
        const end = this.currentPage * this.itemsPerPage;
        return this.items.slice(start, end);
    }

    onNextPage() {
        console.log(this.currentPage);
        if (!this.items || this.currentPage * this.itemsPerPage >= this.items.length) {
            return [];
        }
        this.currentPage++;
        return this.getCurrentPageItems();
    }

    onPreviousPage() {
        console.log(this.currentPage);
        if (this.currentPage <= 1) {
            return [];
        }
        this.currentPage--;
        return this.getCurrentPageItems();
    }

    isFirstPage() {
        return this.currentPage === 1;
    }

    isLastPage() {
        return this.currentPage * this.itemsPerPage >= this.items.length;
    }
}


const itemsPerPage = 12;
let pagination;


window.onload = function () {
    console.log("reached home js");
    const xhr = new XMLHttpRequest();
    const url = '/ecommerce/displayProducts';

    xhr.open('GET', url, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    productDTOs = JSON.parse(xhr.responseText);

                    pagination = new Pagination(itemsPerPage, productDTOs);

                    const currentPageItems = pagination.getCurrentPageItems();
                    
                    currentPageItems.forEach(productDTO => {
                        console.log(productDTO);
                        productsDiv.appendChild(createProduct(productDTO));
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

const nextButton = document.getElementById('nextBtn');
const previousButton = document.getElementById('previousBtn');

const pageNumberElement = document.getElementById('pageNumber');

nextButton.addEventListener('click', (event) => {
    console.log('next button clicked');
    if (pagination.isLastPage()) {
        event.preventDefault(); // prevent the default action
    } else {
        const nextPageItems = pagination.onNextPage();
        
        productsDiv.innerHTML = '';

        nextPageItems.forEach(productDTO => {
            productsDiv.appendChild(createProduct(productDTO));
        });
        pageNumberElement.innerText = pagination.currentPage;
    }
});

previousButton.addEventListener('click', (event) => {
    console.log('previous button clicked');
    if (pagination.isFirstPage()) {
        event.preventDefault();
    } else {
        const previousPageItems = pagination.onPreviousPage();

        productsDiv.innerHTML = '';

    previousPageItems.forEach(productDTO => {
        productsDiv.appendChild(createProduct(productDTO));
    });
    pageNumberElement.innerText = pagination.currentPage;
    }
});


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
let counter=0;
const categoriesBtn = document.getElementById('categoriesBtn');
function showCategory(item) {
    categoriesBtn.innerHTML = item.innerHTML;

    productsDiv.innerHTML = '';

    const isAll = item.innerHTML.toLowerCase() === 'all';

    productDTOs.filter(p => isAll || p.category.toLowerCase() === item.innerHTML.toLowerCase()).forEach(productDTO => {
        productsDiv.appendChild(createProduct(productDTO));
    });
}

const sortBtn = document.getElementById('sortBtn');

function sortProducts(sortType,item) {
    console.log(sortType);
    sortBtn.innerHTML = item.innerHTML;

    productsDiv.innerHTML = '';

    productDTOs.sort((a, b) => {
        if (sortType === 'PLH') {
            return a.price - b.price;
        } else if (sortType === 'NAZ') {
            return a.productName.localeCompare(b.productName);
        } else if (sortType === 'NZA') {
            return b.productName.localeCompare(a.productName);
        } else if (sortType === 'PHL') {
            return b.price - a.price;
        }
    }).forEach(productDTO => {
        console.log(productDTO);
        productsDiv.appendChild(createProduct(productDTO));
    });
}


function createProduct(productDTO) {
    const productElement = document.createElement('div');
    productElement.classList.add('col-md-6', 'col-lg-6', 'col-xl-4');

    productElement.innerHTML = `
        <div class="rounded position-relative fruite-item border border-secondary rounded-bottom">
            <div class="d-flex gap-3 flex-column">
                <div class="d-flex justify-content-between">
                    <div class="fruite-img">
                        <img src="https://iti.blob.core.windows.net/e-commerce-images/${productDTO.productName}.jpg" class="img-fluid rounded-top product-image" alt="${productDTO.productName}" style="width: 200px; height: 200px;">
                    </div>
                    <div class="d-flex flex-column justify-content-between">
                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">${productDTO.category}</div>
                        <div class="p-4">
                            <h4>${productDTO.productName}</h4>
                            <p>${productDTO.description}</p>
                            <div class="d-flex justify-content-between flex-lg-wrap">
                                <p class="text-dark fs-5 fw-bold mb-0">$${productDTO.price} / kg</p>
                            </div>
                        </div>
                                
                    </div>
                </div>
                <div class="d-flex align-items-center mb-3">
                                            <label for="quantity-${productDTO.id}" class="me-2">Quantity:</label>
                                            <input type="number" id="quantity-${productDTO.id}" class="form-control" value="1" min="1" max="${productDTO.quantity}">
                                        </div>
                                        <button class="btn border border-secondary rounded-pill px-3 text-primary" 
                                        onclick="addToCart('${productDTO.id}', '${productDTO.productName}', 
                                        '${productDTO.price}', '${productDTO.quantity}', 
                                        document.getElementById('quantity-${productDTO.id}').value)">Add to Cart</button>
            </div>
        </div>
    `;

    return productElement;
}