package energy_dealer.application.energydealer.Model.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import energy_dealer.application.energydealer.Model.DAOImpl.CategoriaDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ImmagineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ProdottoDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.*;
import energy_dealer.application.energydealer.Controller.ProductPaginationController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrelloService {
    /**
     * Esegue le validazioni necessarie e aggiunge il prodotto nel carrello in caso le validazioni vanno a buon fine.
     * Altrimenti ritorna errore al cliente
     *
     * @param request la request passata dal controller
     * @param response la response passata dal controller
     * */
    public void addProdotto(HttpServletRequest request, HttpServletResponse response){

        // contiene il contenuto del body della request
        String body = null;
        try {
            body = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            System.out.println("Errore nella lettura del corpo nel carrello service [POST]");
            response.setStatus(400);
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(body);

        // todo mettere parsing per id
        int prodotto_id = jsonObject.getInt("prodotto_id");
        int prodotto_quantita = Integer.parseInt(jsonObject.getString("prodotto_quantita"));
        String prodotto_nome = jsonObject.getString("prodotto_nome");

        String categoria_nome = jsonObject.getString("prodotto_categoria");
        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();

        Categoria categoria = categoriaDAO.findCategoriaNome(categoria_nome);
        if (categoria == null){
            System.out.println("Non trovata categoria con nome " + categoria_nome + " nel carrello service [POST]");
            response.setStatus(404);
            return;
        }

        Carrello carrello = (Carrello) request.getSession(false).getAttribute("carrello");

        ProdottoDAOImpl prodottoDAO = new ProdottoDAOImpl();
        Prodotto prodotto_da_aggiungere = prodottoDAO.getProdottoByIdDescrizioneNomeCategoria(prodotto_id, prodotto_nome, categoria.getCategoria_id());

        if (prodotto_da_aggiungere == null){
            response.setStatus(404);
            return;
        }

        if (prodotto_quantita > prodotto_da_aggiungere.getQuantita_inventario()){
            response.setStatus(400);
            return;
        }

        ProdottoNelCarrello prodottoNelCarrello = new ProdottoNelCarrello(prodotto_da_aggiungere, prodotto_quantita);

        if (carrello.getProdotti().size() == 0){
            carrello.addProdotto(prodottoNelCarrello);
        } else {

            List<ProdottoNelCarrello> prodotti = carrello.getProdotti();
            List<ProdottoNelCarrello> daAggiungere = new ArrayList<>();

            boolean productUpdated = false;

            for (ProdottoNelCarrello prodotto : prodotti) {
                // per evitare di modificare la lista all'interno del ciclo creo una copia che aggiorno dopo l'iterazione
                // QUESTO DEVE ESSERE EVITATO PERCHE PRODUCE java.util.ConcurrentModificationException
                int prodotto_selezionato_id = prodotto.getProdotto().getProdotto_id();
                int prodotto_nel_carrello_id = prodottoNelCarrello.getProdotto().getProdotto_id();

                // non era stato ancora aggiunto nel carrello
                if (prodotto_selezionato_id != prodotto_nel_carrello_id) {
                    continue;
                }


                // verifica che la quantita totale data da quantita selezionata prima + selezionata ora sia minore della quantita totale disponibile
                int quantita_selezionata = prodotto.getQuantita_selezionata();
                int quantita_da_aggiungere = prodottoNelCarrello.getQuantita_selezionata();
                int totale_da_aggiungere = quantita_selezionata + quantita_da_aggiungere;

                if (totale_da_aggiungere > prodottoNelCarrello.getProdotto().getQuantita_inventario()) {
                    // quantita da aggiungere in totale risulta maggiore di quantita disponibile
                    response.setStatus(400);
                    return;
                }

                // se passa tutti i controlli allora e' un prodotto che era stato gia selezionato, allora viene aggiornata la quantita
                // e' il prodotto viene settato come da aggiugnere
                prodotto.setQuantita_selezionata(totale_da_aggiungere);
                productUpdated = true;
            }

            if (!productUpdated) {
                daAggiungere.add(prodottoNelCarrello);
            }

            // aggiornare il carrello
            for (ProdottoNelCarrello prodotto : daAggiungere) {
                carrello.addProdotto(prodotto);
            }
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_CREATED); // 201

        String jsonToReturn = "{ \"message\": \"Prodotto aggiunto con successo!\" }";

        // scrive nel body della response oggetto JSON
        ProductPaginationController.writeBody(response, jsonToReturn);

    }

    public void getProdotti(HttpServletRequest request, HttpServletResponse response){
        Carrello carrello = (Carrello) request.getSession(false).getAttribute("carrello");

        ArrayList<ProdottoNelCarrello> prodotti = carrello.getProdotti();
        ArrayList<Immagine> immagini = new ArrayList<>();
        ImmagineDAOImpl immagineDAO = new ImmagineDAOImpl();

        double subtotal = 0;
        int prodotto_id;
        for(ProdottoNelCarrello prodotto : prodotti){
            prodotto_id = prodotto.getProdotto().getProdotto_id();
            subtotal += prodotto.getProdotto().getPrezzo() * prodotto.getQuantita_selezionata();

            try {
                immagini.add(immagineDAO.findImmagineByProdottoId(prodotto_id));
                System.out.println(immagineDAO.findImmagineByProdottoId(prodotto_id));

            } catch (SQLException sqlException){
                System.out.println("Caught SQL Exception when querying prodotto immagine by id: " + prodotto_id);
            }

        }

        System.out.println(prodotti);

        request.setAttribute("prodotti", prodotti);
        request.setAttribute("immagini", immagini);
        request.setAttribute("prezzo_totale", subtotal);
    }

    public void deleteProdotto(HttpServletRequest request, HttpServletResponse response) {
        // contiene il contenuto del body della request
        String body = null;
        try {
            body = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            System.out.println("Errore nella lettura del corpo nel carrello service [DELETE]");
            response.setStatus(400);
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(body);

        String prodotto_nome = jsonObject.getString("prodotto_nome");

        Carrello carrello = (Carrello) request.getSession(false).getAttribute("carrello");
        ArrayList<ProdottoNelCarrello> prodotti = carrello.getProdotti();

        prodotti.removeIf(prodotto -> prodotto.getProdotto().getNome().equals(prodotto_nome));

        response.setStatus(204);
    }
}
