function login (){
    $.post("/login", {username: $("#username").val(), password: $("#password").val()}, (ok)=>{
        if (ok){
        window.location.href = "/loggedin.html";
        }
    }).fail(JXHR => { alert(JXHR.responseText)})
}
function register (){
$.get("/saveUser", {username: $("#username").val(), password: $("#password").val()}, (ok)=>{
    alert ("regiserted!")
})
}