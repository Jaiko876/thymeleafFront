function checkAccess(requiredRole){
    var token = localStorage.getItem("token");
    var role = localStorage.getItem("role");
    if(token==null || requiredRole!==role ){
        window.location.replace("./login.html");
    } else {
        var element = document.getElementsByTagName("body");
        element[0].classList.remove("d-none");
        var login = document.getElementById("login");
        login.innerHTML=localStorage.getItem("username");
    }
};

function getToken(){
    return localStorage.getItem("token");
}

function logout() {
    localStorage.clear();
    window.location.replace("./login.html");
}