
var keyOr = document.getElementById("key").innerHTML;
var passField =  document.getElementById("password");
var form = document.getElementById("login-form");
function submitForm(){
    var plainPass = passField.value;
    key = CryptoJS.enc.Latin1.parse(keyOr)
    var encoedPass = CryptoJS.AES.encrypt(plainPass, key, { 
        iv: key,
        padding: CryptoJS.pad.ZeroPadding,
        mode: CryptoJS.mode.CBC
    }); 
    passField.value = encoedPass;
    form.submit();
};

