// Funzione per inviare una richiesta al server per aggiungere un prodotto al carrello
//  Funzione asincrona per l'invio di dati al server
function askServerToAddProdotto() {
    // Ottiene l'ID del prodotto dall'URL della pagina
    //  Parsing dei parametri di query dell'URL
    prodotto_id = window.location.search    // restituisce attributi di query come: ?prodotto_id=2
    prodotto_id = prodotto_id.split("=")[1]

    // Ottiene i dettagli del prodotto dagli elementi HTML
    //  Accesso al DOM (Document Object Model)
    prodotto_nome = document.getElementById("prodotto_nome").textContent
    prodotto_descrizione = document.getElementById("prodotto_descrizione").textContent
    prodotto_categoria = document.getElementById("prodotto_categoria").textContent

    // Verifica se la quantità selezionata è valida
    //  Validazione dei dati lato client
    if (!check_if_selected_products_lte_avaiable()) {
        // Avvisa l'utente se la quantità selezionata supera quella disponibile
        alert("La quantità selezionata risulta essere maggiore della quantità disponibile")

        // Resetta il contatore
        counter = 1
        document.getElementById("selected_products").innerText = counter
        return;
    }

    // Ottiene la quantità selezionata
    prodotto_quantita = document.getElementById("selected_products").innerText

    // Invia una richiesta POST al server per aggiungere il prodotto al carrello
    //  Chiamata API asincrona utilizzando fetch
    // Crea un oggetto XMLHttpRequest
    let xhr = new XMLHttpRequest();

    // Configura la richiesta
    xhr.open("POST", "https://localhost:8443/carrello", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    // Gestisce la risposta della richiesta
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) { // 4 significa che la richiesta è completata
            if (xhr.status === 200) { // 200 significa che la risposta è OK
                var data = JSON.parse(xhr.responseText);
                console.log(data);
            } else {
                console.error("Request failed with status: " + xhr.status);
            }
        }
    };

    // Crea il corpo della richiesta
    let body = JSON.stringify({
        "prodotto_id": prodotto_id,
        "prodotto_nome": prodotto_nome,
        "prodotto_quantita": prodotto_quantita,
        "prodotto_categoria": prodotto_categoria
    });

    // Invia la richiesta
    xhr.send(body);
}

// Inizializza il contatore per la quantità di prodotti
let counter = 1;

// Funzione per incrementare il contatore
//  Gestione dello stato dell'interfaccia utente
function increment_counter() {
    // Ottiene il numero di prodotti disponibili e selezionati
    available_products = document.getElementById("available_products").textContent;
    selected_products = Number(document.getElementById("selected_products").textContent);

    // Estrae il numero di prodotti disponibili dalla stringa
    available_products = Number(available_products.split(":")[1].trim());

    // Incrementa il contatore se non supera la quantità disponibile
    if (selected_products < available_products) {
        counter++;
        document.getElementById("selected_products").innerText = counter
    }

    console.log(counter);
}

// Funzione per decrementare il contatore
//  Gestione dello stato dell'interfaccia utente
function decrement_counter() {
    selected_products = document.getElementById("selected_products").textContent;

    // Decrementa il contatore se è maggiore di 1
    if (counter > 1) {
        counter--;
        document.getElementById("selected_products").innerText = counter
    }

    console.log(counter);
}

// Funzione per verificare se la quantità selezionata è minore o uguale a quella disponibile
//  Validazione dei dati
function check_if_selected_products_lte_avaiable() {
    available_products = document.getElementById("available_products").textContent;
    selected_products = Number(document.getElementById("selected_products").textContent);

    available_products = Number(available_products.split(":")[1].trim());

    return selected_products <= available_products;
}

// Inizializza il numero di pagina
let page = 1;

// Aggiunge un event listener quando il DOM è completamente caricato
//  Gestione degli eventi del DOM
document.addEventListener("DOMContentLoaded", function () {
    // Funzione per caricare una pagina di dati dal server
    //  Richiesta AJAX
    function loadPage() {
        let xhr = new XMLHttpRequest();

        xhr.open("GET", "https://localhost:8443/immagini?page=" + page, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) { // 200 OK
                    // Parsing della risposta JSON
                    let response = JSON.parse(xhr.responseText);
                    let immagini = response.body.immagini;
                    let prodotti = response.body.prodotti;
                    let categorie = response.body.categorie;

                    // Genera le righe della tabella con i dati ricevuti
                    generateRowFromFetchedPage(response, prodotti, immagini, categorie)

                    // Incrementa il numero della pagina per le prossime richieste
                    page++;
                } else {
                    // Gestione degli errori
                    console.error("AJAX Request failed: ", xhr.status, xhr.statusText);
                }
            }
        };
        xhr.send();
    }

    // Aggiunge un event listener al pulsante di caricamento
    document.getElementById("loadButton").addEventListener("click", loadPage);
});

// Funzione per generare le righe della tabella con i dati ricevuti
//  Manipolazione dinamica del DOM
function generateRowFromFetchedPage(response, prodotti, immagini, categorie) {
    let tableBody = document.getElementById("tableBody");

    prodotti.forEach(function (prodotto) {
        let row = document.createElement("tr");
        row.onclick = function () {
            location.href = "/ProductPage?prodotto_id=" + prodotto.prodotto_id
        }

        // Crea e aggiunge la cella per l'immagine
        let imgCell = document.createElement("td");
        let imgElement = document.createElement("img");
        let immagine = immagini.find(img => img.prodotto_id === prodotto.prodotto_id);
        if (immagine) {
            imgElement.src = immagine.url;
            imgElement.alt = "Image for " + prodotto.nome;
            imgElement.style.height = "15rem";
            imgCell.appendChild(imgElement);
        }
        row.appendChild(imgCell);

        // Crea e aggiunge la cella per il nome
        let nomeCell = document.createElement("td");
        nomeCell.textContent = prodotto.nome;
        row.appendChild(nomeCell);

        // Crea e aggiunge la cella per la categoria
        let categoria = categorie.find(cat => cat.categoria_id === prodotto.categoria_id);
        let categoriaCell = document.createElement("td");
        categoriaCell.textContent = categoria.nome;
        row.appendChild(categoriaCell);

        // Crea e aggiunge la cella per il prezzo
        let prezzoCell = document.createElement("td");
        prezzoCell.textContent = parseFloat(prodotto.prezzo).toPrecision(3) + "€";
        row.appendChild(prezzoCell);

        // Crea e aggiunge la cella per la descrizione
        let descrizioneCell = document.createElement("td");
        descrizioneCell.textContent = prodotto.descrizione;
        row.appendChild(descrizioneCell);

        let quantitaCell = document.createElement("td");
        quantitaCell.textContent = prodotto.quantita_inventario;
        row.appendChild(quantitaCell);

        // Aggiunge la riga completa alla tabella
        tableBody.appendChild(row);
    });
    console.log("Received products:", prodotti);
}