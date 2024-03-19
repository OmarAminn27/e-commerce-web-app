document.addEventListener("DOMContentLoaded", function() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "showProfile", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var data = JSON.parse(xhr.responseText);
                console.log(data.birthday.toLocaleString());
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
    console.log(data.birthdate);

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