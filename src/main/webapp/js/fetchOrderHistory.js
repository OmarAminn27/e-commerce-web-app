// Function to send XHR request to fetch order history when the page loads
function fetchOrderHistory() {
    // Create an XHR object
    var xhr = new XMLHttpRequest();

    // Define the request parameters
    xhr.open('GET', 'fetchOrderHistory', true);

    // Set up event listener to handle the response
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                // Parse JSON response
                var orderDTOs = JSON.parse(xhr.responseText);

                // Get the table body element
                var tableBody = document.querySelector('#order-history-table tbody');

                // Clear existing table rows
                tableBody.innerHTML = '';

                // Loop through the orderDTOs and update the table
                orderDTOs.forEach(function (orderDTO) {
                    var newRow = '<tr>' +
                        '<td>' + orderDTO.orderID + '</td>' +
                        '<td>' + orderDTO.username + '</td>' +
                        '<td>' + orderDTO.orderedAt + '</td>' +
                        '<td>' + orderDTO.totalCost + '</td>' +
                        '</tr>';
                    tableBody.innerHTML += newRow;
                });

                console.log('Order history fetched successfully');
            } else {
                console.error('Request failed');
            }
        }
    };

    // Send the request
    xhr.send();
}

// Call the function when the page is loaded
window.onload = function () {
    fetchOrderHistory();
};
