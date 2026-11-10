let page = 1;

document.addEventListener("DOMContentLoaded", function () {
    function loadPage() {
        let xhr = new XMLHttpRequest();

        xhr.open("GET", "https://localhost:8443/immagini?page=" + page, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) { // 200 OK

                    let response = JSON.parse(xhr.responseText);
                    let immagini = response.body.immagini;
                    let prodotti = response.body.prodotti;

                    // usare i dati
                    generateRowFromFetchedPage(response, prodotti, immagini)

                    // Incrementa il numero delle pagine per le prossime request
                    page++;
                } else {
                    // Error handling
                    console.error("AJAX Request failed: ", xhr.status, xhr.statusText);
                }
            }
        };
        xhr.send();
    }

    // Attach event listener to the button
    document.getElementById("loadButton").addEventListener("click", loadPage);
});


// Metodo che prende in input la ajax response, array di immagini e array di prodotti e genera la riga con i dati in arrivo.
function generateRowFromFetchedPage(response, prodotti, immagini) {
    // Contenitore in cui mettere risultato della ajax
    let tableBody = document.getElementById("tableBody");

    // Cicla per tutto l'array di immagini e ogni iterazione crea riga con il prodotto
    prodotti.forEach(function (prodotto) {
        // per generare la riga
        let row = document.createElement("tr");

        // Cerca l'immagine corrispondente al prodotto
        let immagine = immagini.find(img => img.prodotto_id === prodotto.prodotto_id);

        // Aggiunge il contenuto nella riga generata
        // aggiunge l'immagine nella prima colona della riga (Immagine)
        let imgCell = document.createElement("td");

        let imgElement = document.createElement("img");
        if (immagine) {
            imgElement.src = immagine.url;                   // setta l'immagine prendendo l'url dall'oggetto image
            imgElement.alt = "Image ID " + immagine.immagine_id; // descrizione nel caso l'immagine non si carica
            imgElement.style.width = "10rem";                 // setta grandezza per l'immagine
            imgCell.appendChild(imgElement);                  // nel tag TD aggiunge tag img
        }

        row.appendChild(imgCell);                         // aggiunge tag TD con immagine nella riga

        // aggiunge id nella seconda colonna della riga (Id del prodotto)
        let idProdottoCell = document.createElement("td");  // genera tag TD
        idProdottoCell.textContent = prodotto.prodotto_id;                                // inserisce id all'interno
        row.appendChild(idProdottoCell);                                               // inserisce TD generato nella riga

        // aggiunge id nella terza colonna della riga (nome della categoria del prodotto)
        let categoriaCell = document.createElement("td");
        categoriaCell.textContent = prodotto.categoria_id;
        row.appendChild(categoriaCell);

        // aggiunge id nella quarta colonna della riga (nome del prodotto)
        let nomeCell = document.createElement("td");
        nomeCell.textContent = prodotto.nome;
        row.appendChild(nomeCell);

        // aggiunge id nella quinta colonna della riga (prezzo del prodotto)
        let prezzoCell = document.createElement("td");
        prezzoCell.textContent = parseFloat(prodotto.prezzo).toPrecision(3) + "€";
        row.appendChild(prezzoCell);

        // aggiunge id nella sesta colonna della riga (quantità del prodotto)
        let quantitaCell = document.createElement("td");
        quantitaCell.textContent = prodotto.quantita_inventario;
        row.appendChild(quantitaCell);

        // aggiunge descrizione nella penultima colonna della riga (Descrizione del prodotto)
        let descrizioneCell = document.createElement("td");
        descrizioneCell.textContent = prodotto.descrizione;
        row.appendChild(descrizioneCell);

        // aggiunge azione elimina nell'ultima colonna della riga (ELIMINA)
        let azioneElimina = document.createElement("td");
        let formElimina = document.createElement("form");
        formElimina.action = "/admin/AdminPage";
        formElimina.method = "post";
        let azione = document.createElement("input");
        azione.type = "hidden";
        azione.name = "action";
        azione.value = "deleteProduct";
        formElimina.appendChild(azione);
        let prodottoId = document.createElement("input");
        prodottoId.type = "hidden";
        prodottoId.name = "prodottoId";
        prodottoId.value = prodotto.prodotto_id;
        formElimina.appendChild(prodottoId);
        let submitButtonElimina = document.createElement("input");
        submitButtonElimina.type = "submit";
        submitButtonElimina.value = "Elimina";
        submitButtonElimina.onclick = function () {
            return confirm('Sei sicuro di voler eliminare questo prodotto?');
        };
        formElimina.appendChild(submitButtonElimina);
        azioneElimina.appendChild(formElimina);
        row.appendChild(azioneElimina);

        // aggiunge azione aggiorna nella ultima colonna della riga (AGGIORNA)
        let azioneAggiornaElement = document.createElement("td");
        let formAggiorna = document.createElement("form");
        formAggiorna.action = "/admin/AdminPage";
        formAggiorna.method = "get";
        let azioneAggiorna = document.createElement("input");
        azioneAggiorna.type = "hidden";
        azioneAggiorna.name = "action";
        azioneAggiorna.value = "updateProduct";
        formAggiorna.appendChild(azioneAggiorna);
        let requestedEntity = document.createElement("input");
        requestedEntity.type = "hidden";
        requestedEntity.name = "entity";
        requestedEntity.value = "products";
        formAggiorna.appendChild(requestedEntity);
        let prodottoId_aggiornamento = document.createElement("input");
        prodottoId_aggiornamento.type = "hidden";
        prodottoId_aggiornamento.name = "productId";
        prodottoId_aggiornamento.value = prodotto.prodotto_id;
        formAggiorna.appendChild(prodottoId_aggiornamento);
        let submitButtonAggiorna = document.createElement("input");
        submitButtonAggiorna.type = "submit";
        submitButtonAggiorna.value = "Aggiorna";
        submitButtonAggiorna.onclick = function () {
            return confirm('Sei sicuro di voler aggiornare questo prodotto?');
        };
        formAggiorna.appendChild(submitButtonAggiorna);
        azioneAggiornaElement.appendChild(formAggiorna);
        row.appendChild(azioneAggiornaElement);

        // Inserisce intera riga appena generata nella tabella
        tableBody.appendChild(row);
    });
}