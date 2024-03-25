function prependHeaderToBody() {
    fetch('navbar.html')
        .then(response => response.text())
        .then(headerHtml => {
            document.body.insertAdjacentHTML('afterbegin', headerHtml);
            const logoutBtn = document.getElementById('logoutBtn');
//           if (cookieExists()) {
//               console.log("Button should appear");
//               logoutBtn.style.display = 'inline-block';
//           } else {
//               console.log("Button should disappear");
//               logoutBtn.style.display = 'none';
//           }
        })
        .catch(error => console.error('Error loading header:', error));
}

function cookieExists() {
    console.log("getCookie function");
    return document.cookie.includes("JSESSIONID");
}

document.addEventListener('DOMContentLoaded', function () {
    prependHeaderToBody();
});
