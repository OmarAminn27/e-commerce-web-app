document.getElementById("checkoutBtn").addEventListener("click", function () {
    var confirmed = confirm("Are you sure you want to proceed with the checkout?");
    if (confirmed) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "checkout", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Servlet activated successfully.");
                    var responseText = xhr.responseText; 
                    alert(responseText); 
                    window.location.href = "home";
                } else {
                    console.error("Failed to activate servlet. Status: " + xhr.status);
                }
            }
        };
        xhr.send();
    } else {
        console.log("Checkout canceled by the user.");
    }
});


