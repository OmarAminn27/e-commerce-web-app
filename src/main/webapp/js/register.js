document.addEventListener("DOMContentLoaded", function() {
    var xmlHttp1;
    var xmlHttp2;
    var userName = document.getElementById("name");
    var birthDate = document.getElementById("birthday");
    var password = document.getElementById("password");
    var email = document.getElementById("email");
    var form = document.querySelector('form');

// Name
userName.addEventListener("blur", function () {
    console.log('userName blur event triggered');
    xmlHttp1 = new XMLHttpRequest();
    xmlHttp1.onreadystatechange = function () {
        if (xmlHttp1.readyState === 4 && xmlHttp1.status === 200) {
            if (xmlHttp1.responseText != "unique") {
                userName.classList.add('error');
                userName.title = "Username already taken";
            } else {
                userName.classList.remove('error');
                userName.title = "";
            }
        }
    };
    xmlHttp1.open("POST", "RegisterServlet");
    xmlHttp1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp1.send("username=" + userName.value + "&type=" + "username");
});

userName.addEventListener("focus", function () {
    console.log('userName focus event triggered');
    userName.classList.remove('error');
    userName.title = "";
});


// Email
birthDate.addEventListener("blur", function () {
    console.log('birthDate blur event triggered');
    var today = new Date();
    var birth = new Date(birthDate.value);
    if (today.getFullYear() - birth.getFullYear() < 16) {
        birthDate.classList.add('error');
        birthDate.title = "You must be at least 16 years old";
    } else {
        birthDate.classList.remove('error');
        birthDate.title = "";
    }
});

birthDate.addEventListener("focus", function () {
    console.log('birthDate focus event triggered');
    birthDate.classList.remove('error');
    birthDate.title = "";
});

//document.querySelector('form').addEventListener('submit', function(event) {
//    var email = document.getElementById('email').value;
//    var emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
//
//    if (!email.match(emailPattern)) {
//        event.preventDefault();
//        alert('Please enter a valid email address.');
//    }
//});


// Birthdate
password.addEventListener("blur", function () {
    console.log('password blur event triggered');
    if (password.value.length < 8) {
        password.classList.add('error');
        password.title = "Password must be at least 8 characters long";
    } else {
        password.classList.remove('error');
        password.title = "";
    }
});

password.addEventListener("focus", function () {
    console.log('password focus event triggered');
    password.classList.remove('error');
    password.title = "";
});


// Email
email.addEventListener("blur", function () {
    console.log('email blur event triggered');
    xmlHttp2 = new XMLHttpRequest();
    xmlHttp2.onreadystatechange = function () {
        if (xmlHttp2.readyState === 4 && xmlHttp2.status === 200) {
            if (xmlHttp2.responseText != "unique") {
                email.classList.add('error');
                email.title = "Email already taken";
            } else {
                email.classList.remove('error');
                email.title = "";
            }
        }
    };
    xmlHttp2.open("POST", "RegisterServlet");
    xmlHttp2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp2.send("email=" + email.value + "&type=" + "email");
});

email.addEventListener("focus", function () {
    console.log('email focus event triggered');
    email.classList.remove('error');
    email.title = "";
});


form.addEventListener('submit', function (event) {
    // Check if any of the fields have the 'error' class
    var hasError = document.querySelector('.error') !== null;

    if (hasError) {
        // Prevent form submission
        event.preventDefault();

        // Display error message
        alert('Invalid Inputs');
    }
});

});


