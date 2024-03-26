document.addEventListener("DOMContentLoaded", function() {

    const userName = document.getElementById("username");
    const birthDate = document.getElementById("birthdate");
    const email = document.getElementById("email");
    const form = document.querySelector('form');

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "showProfile", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var data = JSON.parse(xhr.responseText);
                console.log("date:  " + data.birthday.toLocaleString());
                console.log(data.job);
                document.getElementById("username").value = data.username;
                document.getElementById("email").value = data.email;
                document.getElementById("birthdate").value = data.birthday.toLocaleString();
                document.getElementById("job").value = data.job;
                document.getElementById("country").value = data.country;
                document.getElementById("city").value = data.city;
                document.getElementById("street").value = data.streetName;
                document.getElementById("credit-limit").value = data.creditLimit;
                document.getElementById("interests").value = data.interests;
            } else {
                console.error("Error fetching user data");
            }
        }
    };
    xhr.send();


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
        xmlHttp1.open("POST", "editProfile");
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
        xmlHttp2.open("POST", "editProfile");
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



function editProfile(){
    event.preventDefault();
    document.getElementById("username").removeAttribute("disabled");
    document.getElementById("email").removeAttribute("disabled");
    document.getElementById("birthdate").removeAttribute("disabled");
    document.getElementById("job").removeAttribute("disabled");
    document.getElementById("country").removeAttribute("disabled");
    document.getElementById("city").removeAttribute("disabled");
    document.getElementById("street").removeAttribute("disabled");
    document.getElementById("credit-limit").removeAttribute("disabled");
    document.getElementById("interests").removeAttribute("disabled");

    var saveChangesBtn = document.getElementById("saveChangesBtn");

    if (!saveChangesBtn) {
        saveChangesBtn = document.createElement("button");
        saveChangesBtn.setAttribute("type", "button");
        saveChangesBtn.setAttribute("class", "btn btn-primary fw-bold");
        saveChangesBtn.setAttribute("id", "saveChangesBtn");
        saveChangesBtn.innerHTML = "Save Changes";

        var editBtn = document.querySelector('.product-details .col-12 button:last-child');
        editBtn.insertAdjacentElement('afterend', saveChangesBtn);
    }

    saveChangesBtn.addEventListener("click", saveChanges);
}

function saveChanges() {
    document.getElementById("saveChangesBtn").remove();
    document.getElementById("username").setAttribute("disabled", "");
    document.getElementById("email").setAttribute("disabled", "");
    document.getElementById("birthdate").setAttribute("disabled", "");
    document.getElementById("job").setAttribute("disabled", "");
    document.getElementById("country").setAttribute("disabled", "");
    document.getElementById("city").setAttribute("disabled", "");
    document.getElementById("street").setAttribute("disabled", "");
    document.getElementById("credit-limit").setAttribute("disabled", "");
    document.getElementById("interests").setAttribute("disabled", "");
    var xhr = new XMLHttpRequest();
    var url = "editProfile";
    var data = {
        username: document.getElementById("username").value,
        email: document.getElementById("email").value,
        job: document.getElementById("job").value,
        country: document.getElementById("country").value,
        city: document.getElementById("city").value,
        street: document.getElementById("street").value,
        credit: document.getElementById("credit-limit").value,
        interests: document.getElementById("interests").value,
        birthdate: document.getElementById("birthdate").value
    };
    console.log(data.username);
    console.log(data.email);
    console.log(data.job);
    console.log(data.country);
    console.log(data.city);
    console.log(data.street);
    console.log(data.credit);
    console.log(data.interests);
    console.log("this is correct date" + data.birthdate);

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("profile.js -> saveChanges: Data saved successfully");
            } else {
                console.error("profile.js -> Error saving user data");
            }
        }
    };

    xhr.send(JSON.stringify(data));
}