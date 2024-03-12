//@ts-check
var xmlHttp;
var userName = document.getElementById("name");

userName.addEventListener("blur", function () {
    xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            if (xmlHttp.responseText != "unique") {
                userName.classList.add('error');
                userName.title = "Username already taken";
            } else {
                userName.classList.remove('error');
                userName.title = "";
            }
        }
    };
    xmlHttp.open("POST", "register", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send("username=" + userName.value);
});

userName.addEventListener("focus", function () {
    userName.classList.remove('error');
    userName.title = "";
});
