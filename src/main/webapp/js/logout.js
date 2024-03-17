document.addEventListener("DOMContentLoaded", function () {
    // Add event listener to the logout link
    document.getElementById("logoutBtn").addEventListener("click", function (event) {
        event.preventDefault();
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/ecommerce/logout");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Logout successful");
                    window.location.href = "login";
                } else {
                    console.error("Logout failed");
                }
            }
        };
        xhr.send();
    });
});
