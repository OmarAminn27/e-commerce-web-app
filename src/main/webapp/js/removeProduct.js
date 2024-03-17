// Wrap your JavaScript code inside a DOMContentLoaded event listener
document.addEventListener('DOMContentLoaded', function () {
    // Function to send XHR request to remove product
    function removeProduct(productName) {
        // Create an XHR object
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/ecommerce/removeProduct?productName=' + encodeURIComponent(productName), true);
        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 300) {
                console.log('Product removed successfully');
                // Optionally, you can reload the page or update the product list after successful removal
            } else {
                console.error('Failed to remove product');
            }
        };
        xhr.onerror = function () {
            console.error('XHR request failed');
        };
        // No need to set Content-Type since we're using URL parameters
        xhr.send();
    }

    // Add event listeners to all remove buttons
    document.querySelectorAll('.remove-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            var productName = button.closest('tr').querySelector('td:first-child span').textContent;
            removeProduct(productName);
        });
    });
});
