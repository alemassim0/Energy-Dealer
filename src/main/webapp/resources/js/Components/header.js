// Aspetta che il DOM sia completamente caricato prima di eseguire il codice
document.addEventListener('DOMContentLoaded', function() {
    // Seleziona l'elemento del pulsante di attivazione del menu
    const menuToggle = document.querySelector('.menu-toggle');

    // Seleziona l'elemento contenitore delle icone di navigazione
    const navIcons = document.querySelector('.nav-icons');

    // Aggiunge un listener per l'evento click al pulsante di attivazione del menu
    menuToggle.addEventListener('click', function() {
        // Attiva/disattiva la classe 'show' sul contenitore delle icone di navigazione
        navIcons.classList.toggle('show');
    });

    // Aggiunge un listener per l'evento click all'intero documento
    document.addEventListener('click', function(event) {
        // Controlla se il click è fuori dall'header e se il menu è aperto
        if (!header.contains(event.target) && navIcons.classList.contains('show')) {
            // Chiude il menu rimuovendo la classe 'show'
            navIcons.classList.remove('show');
        }
    });
});