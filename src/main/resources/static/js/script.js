function searchDoc() {
    var list = document.getElementById("resultList");
    var req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            list.innerHTML = this.responseText;
        }
    };
    var cognome = document.getElementById("cognome").value;
    var id = document.getElementById("idUser").value;
    req.open('GET', "/admin/search-docente?cognome=" + cognome + "&idUser=" + id);
    req.send();    
}