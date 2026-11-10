// Questa funzione convalida i campi email e password in tempo reale mentre l'utente digita
// Una funzione che esegue la validazione dei dati inseriti dall'utente
function validateForm(event) {

    // Ottiene il valore inserito nei campi email, conferma email, password e conferma password
    // Recupero dei valori degli elementi del DOM (Document Object Model)
    let email = document.getElementById("email").value;
    let confirmEmail = document.getElementById("confirm-email").value;
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirm-password").value;

    // Controlla se entrambi i campi email sono compilati
    // Verifica della presenza di dati nei campi di input
    if (email !== "" && confirmEmail !== "") {

        // Se le email non corrispondono, visualizza un messaggio di errore
        // Confronto di stringhe e manipolazione del contenuto del DOM
        if (email !== confirmEmail) {
            document.getElementById("email-error").innerText = "Emails do not match";
        } else {
            // Se le email corrispondono, cancella il messaggio di errore
            document.getElementById("email-error").innerText = "";
        }
    }

    // Controlla se entrambi i campi password sono compilati
    // Verifica della presenza di dati nei campi di input
    if (password !== "" && confirmPassword !== "") {

        // Se le password non corrispondono, visualizza un messaggio di errore
        // Confronto di stringhe e manipolazione del contenuto del DOM
        if (password !== confirmPassword) {
            document.getElementById("password-error").innerText = "Passwords do not match";
        } else {
            // Se le password corrispondono, cancella il messaggio di errore
            document.getElementById("password-error").innerText = "";
        }
    }
}

// Questa funzione convalida i campi email e password quando il modulo viene inviato
// Funzione di gestione dell'evento di invio del modulo
function validateFormOnSubmit(event) {
    // Impedisce il comportamento predefinito di invio del modulo
    // Prevenzione del comportamento predefinito di un evento
    event.preventDefault();

    // Ottiene i valori dei campi email, conferma email, password e conferma password
    // Recupero dei valori degli elementi del DOM
    let email = document.getElementById("email").value;
    let confirmEmail = document.getElementById("confirm-email").value;
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirm-password").value;

    // Usa un flag (isValid) per determinare se il modulo è valido
    // Dichiarazione di una variabile booleana
    let isValid = true;

    // Se le email non corrispondono, imposta isValid a false
    // Confronto di stringhe e modifica del valore di una variabile booleana
    if (email !== confirmEmail) {
        isValid = false;
    }

    // Se le password non corrispondono, imposta isValid a false
    // Confronto di stringhe e modifica del valore di una variabile booleana
    if (password !== confirmPassword) {
        isValid = false;
    }

    // Se isValid è true, invia il modulo
    // Verifica di una condizione e invio del modulo
    if (isValid) {
        document.getElementById("register-form").submit();
    }
}

// I listener degli eventi keyup forniscono un feedback immediato all'utente mentre digita
// La funzione validateForm è associata all'evento keyup dei campi
// email, password, conferma email e conferma password
// Ciò significa che validateForm verrà chiamata ogni volta che viene rilasciato un tasto durante la digitazione in questi campi
// Aggiunta di event listener per l'evento keyup
document.getElementById("email").addEventListener("keyup", validateForm);
document.getElementById("password").addEventListener("keyup", validateForm);
document.getElementById("confirm-email").addEventListener("keyup", validateForm);
document.getElementById("confirm-password").addEventListener("keyup", validateForm);

// Il listener dell'evento submit garantisce che il modulo venga nuovamente convalidato prima dell'invio,
// impedendo l'invio di dati errati
// La funzione validateFormOnSubmit è associata all'evento submit del modulo
// Ciò significa che validateFormOnSubmit verrà chiamata ogni volta che il modulo viene inviato
// Aggiunta di un event listener per l'evento submit
document.getElementById("register-form").addEventListener("submit", validateFormOnSubmit);
