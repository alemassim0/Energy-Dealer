package energy_dealer.application.energydealer.Model.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.DAOImpl.CategoriaDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ImmagineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ProdottoDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.Categoria;
import energy_dealer.application.energydealer.Model.Entity.Immagine;
import energy_dealer.application.energydealer.Model.Entity.Prodotto;

import java.util.*;

public class ProdottoService {

    ProdottoDAOImpl prodottoDAO = new ProdottoDAOImpl();
    CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
    ImmagineDAOImpl immagineDAO = new ImmagineDAOImpl();

    /**
     * Validates and returns the product details by its ID from an HTTP request.
     *
     * This method checks if the "prodotto_id" parameter is present in the request and retrieves
     * the corresponding product, category, and image from the database.
     * If any of these are missing, it sets an appropriate error message and status code in the response.
     *
     * @param request  the HttpServletRequest containing the client's request
     * @param response the HttpServletResponse for setting the response status and attributes
     * @return the HttpServletRequest with attributes set for the product, category, and image,
     *         or with error messages and status codes if validation fails
     */
    public HttpServletRequest validateAndReturnProdottoById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String prodotto_id = request.getParameter("prodotto_id");

            if (prodotto_id == null || prodotto_id.isEmpty()) {
                // nel caso in cui prodotto_id nella richiesta sia assente viene mostrato un errore
                request.setAttribute("errore: prodotto_id nella queryString e' obbligatorio.", "400");
                response.setStatus(400);
                return request;
            }

            Prodotto prodotto = prodottoDAO.findProdottoId(Integer.parseInt(prodotto_id));

            if (prodotto == null) {
                // se l'id non corrisponde a nessun prodotto viene mostrato errore
                request.setAttribute("errore: prodotto non trovato.", "404");
                response.setStatus(404);
                return request;
            }

            Categoria categoria = categoriaDAO.findCategoriaId(prodotto.getCategoria_id());

            if (categoria == null) {
                // se la categoria cercata non esiste viene mostrato errore
                request.setAttribute("errore: categoria del prodotto non trovata.", "500");
                response.setStatus(500);
                return request;
            }

            Immagine immagine = immagineDAO.findImmagineId(prodotto.getProdotto_id());

            if (immagine == null) {
                // se l'immagine non esiste viene mostrato errore
                request.setAttribute("errore: immagine del prodotto non trovata.", "500");
                response.setStatus(500);
                return request;
            }

            request.setAttribute("prodotto", prodotto);
            request.setAttribute("categoria", categoria);
            request.setAttribute("immagine", immagine);

            return request;

        } catch (Exception exception) {
            System.out.println("exception is following");
            exception.printStackTrace();
        }

        return null;
    }

    public void getProductsForHomePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Recupera tutti i prodotti dal database
            List<Prodotto> allProdotti = prodottoDAO.findAllProdotto();
            System.out.println("Numero di prodotti recuperati: " + allProdotti.size());

            // Seleziona 3 prodotti casuali
            List<Prodotto> randomProdotti = selectRandomProducts(allProdotti, 3);

            // Crea mappe per categorie e immagini
            Map<Integer, Categoria> categorie = new HashMap<>();
            Map<Integer, Immagine> immagini = new HashMap<>();

            // Itera sui prodotti casuali selezionati
            for (Prodotto prodotto : randomProdotti) {
                // Aggiunge la categoria del prodotto se non è già presente
                if (!categorie.containsKey(prodotto.getCategoria_id())) {
                    Categoria categoria = categoriaDAO.findCategoriaId(prodotto.getCategoria_id());
                    categorie.put(prodotto.getCategoria_id(), categoria);
                    System.out.println("Aggiunta categoria: " + (categoria != null ? categoria.getNome() : "null") + " per il prodotto: " + prodotto.getNome());
                }

                // Aggiunge l'immagine del prodotto se non è già presente
                if (!immagini.containsKey(prodotto.getProdotto_id())) {
                    Immagine immagine = immagineDAO.findImmagineId(prodotto.getProdotto_id());
                    immagini.put(prodotto.getProdotto_id(), immagine);
                    System.out.println("Aggiunta immagine: " + (immagine != null ? immagine.getUrl() : "null") + " per il prodotto: " + prodotto.getNome());
                }
            }

            // Imposta gli attributi nella richiesta
            request.setAttribute("prodotti", randomProdotti);
            request.setAttribute("categorie", categorie);
            request.setAttribute("immagini", immagini);

            System.out.println("Attributi impostati: prodotti (casuali), categorie, immagini");

        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            request.setAttribute("error", "Si è verificato un errore durante il recupero dei prodotti: " + e.getMessage());
            System.out.println("Errore verificato: " + e.getMessage());
        }
    }

    private List<Prodotto> selectRandomProducts(List<Prodotto> allProdotti, int count) {
        // Se ci sono meno prodotti di quanti ne richiediamo, restituisci tutti i prodotti
        if (allProdotti.size() <= count) {
            return new ArrayList<>(allProdotti);
        }

        List<Prodotto> randomProdotti = new ArrayList<>();
        Random random = new Random();
        Set<Integer> selectedIndexes = new HashSet<>();

        // Seleziona prodotti casuali
        while (selectedIndexes.size() < count) {
            int randomIndex = random.nextInt(allProdotti.size());
            if (selectedIndexes.add(randomIndex)) {
                randomProdotti.add(allProdotti.get(randomIndex));
            }
        }
        return randomProdotti;
    }
}
