// Function to fetch user profiles from the servlet and update the table
function updateUserProfiles() {
    console.log("update user profiles");
    // Create an XHR object
    var xhr = new XMLHttpRequest();

    // Define the request parameters
    xhr.open('GET', '/ecommerce/updateUserProfile', true);

    // Set up event listener to handle the response
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                // Parse JSON response
                var userDTOs = JSON.parse(xhr.responseText);

                // Clear existing table rows
                var tableBody = document.querySelector('#user-table tbody');
                tableBody.innerHTML = '';

                // Loop through the userDTOs and update the table
                userDTOs.forEach(function (userDTO) {
                    var newRow = '<tr>' +
                        '<td>' + userDTO.username + '</td>' +
                        // '<td>' + userDTO.password + '</td>' +
                        '<td>' + userDTO.job + '</td>' +
                        '<td>' + userDTO.email + '</td>' +
                        '<td>' + userDTO.creditLimit + '</td>' +
                        '<td>' + userDTO.country + '</td>' +
                        '<td>' + userDTO.city + '</td>' +
                        '<td>' + userDTO.streetName + '</td>' +
                        '<td>' + userDTO.interests + '</td>' +
                        '<td><button class="btn btn-primary show-order-history-btn" onclick="showOrderHistory(\'' + userDTO.username + '\')">Order History</button></td>' +
                        '</tr>';
                    tableBody.innerHTML += newRow;
                });
            } else {
                console.error('Request failed');
            }
        }
    };

    // Send the request
    xhr.send();
}

// Function to handle clicking on the "Show Order History" button
function showOrderHistory(username) {
    var xhr = new XMLHttpRequest();

    xhr.open('POST', '/ecommerce/addOrderHistoryUserToContext', true);
    xhr.setRequestHeader('Content-Type', 'text/plain');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                console.log('Helper servlet success');
                window.location.href = "orderHistory";
            } else {
                console.error('Request failed');
            }
        }
    };
    xhr.send(username);
}


// Call the function when the page is loaded
window.onload = function () {
    updateUserProfiles();
};
