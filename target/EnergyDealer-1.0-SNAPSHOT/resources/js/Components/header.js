
// Aspetta che il DOM sia completamente caricato
document.addEventListener('DOMContentLoaded', function() {

    // Seleziona l'header
    const header = document.querySelector('header');

    // Seleziona il pulsante del menu
    const menuToggle = document.querySelector('.menu-toggle');

    // Seleziona il contenitore delle icone
    const navIcons = document.querySelector('.nav-icons');

    // Verifica che tutti gli elementi esistano
    if (!header || !menuToggle || !navIcons) {
        return;
    }

    // Apertura e chiusura del menu
    menuToggle.addEventListener('click', function(event) {
        event.stopPropagation();
        navIcons.classList.toggle('show');
    });

    // Chiusura del menu cliccando fuori dall'header
    document.addEventListener('click', function(event) {
        if (!header.contains(event.target) &&
            navIcons.classList.contains('show')) {

            navIcons.classList.remove('show');
        }
    });

});
