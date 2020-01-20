/**
 * Esegue una richiesta http al server per una lista di docenti
 */
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
/**
 * controlla che quando si aggiunge una nuova lezione questa non abbia parametri nulli
 */
function addLesson(){
    var data = document.getElementById("data");
    var desc = document.getElementById("descrizione");
    if(data.value == null || data.value == ""){
        data.focus();
        return false;
    }
    else if(desc.value == null || desc.value == ""){
       desc.focus();
       return false; 
    }
    return true;
}