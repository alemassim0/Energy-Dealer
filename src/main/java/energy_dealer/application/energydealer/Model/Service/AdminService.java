package energy_dealer.application.energydealer.Model.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.DAO.CategoriaDAO;
import energy_dealer.application.energydealer.Model.DAOImpl.ImmagineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.OrdineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ProdottoDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.UtenteDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    final String DEFAULT_REDIRECT_PAGE = "adminPage_prodotto.jsp";

    UtenteDAOImpl utenteDAO;
    ProdottoDAOImpl prodottoDAO;
    OrdineDAOImpl ordineDAO;
    ImmagineDAOImpl immagineDAO;
    CategoriaDAO categoriaDAO;

    public AdminService() {
        utenteDAO = new UtenteDAOImpl();
        prodottoDAO = new ProdottoDAOImpl();
        ordineDAO = new OrdineDAOImpl();
        immagineDAO = new ImmagineDAOImpl();
    }

    public String handleGetRequest(HttpServletRequest request, HttpServletResponse response) {
        String requestedEntity = request.getParameter("entity");

        if (requestedEntity == null)
            return handleGetAdminDashboard(request, response);

        switch (requestedEntity) {
            case "users":
                return handleGetUsers(request, response);
            case "products":
                return handleGetProducts(request, response); // dovrebbe ritornare la pagina con i prodotti
            case "orders":
                return handleGetOrdini(request, response); // dovrebbe ritornare la pagina con gli ordini
            default:
                return handleGetAdminDashboard(request, response); // pagina di dashboard
        }
    }

    // Metodo pubblico che gestisce le richieste POST, restituisce una String e può lanciare IOException
    public String handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Ottiene il valore del parametro "action" dalla richiesta
        String action = request.getParameter("action");

        // Controlla se l'azione è null
        if (action == null) {
            // Invia una risposta di errore 400 (Bad Request) se l'azione è mancante
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action is required");
            return null;
        }

        // Usa uno switch per gestire diverse azioni
        switch (action) {
            // Caso per l'azione "deleteUser"
            case "deleteUser":
                // Chiama il metodo handleDeleteUser e restituisce il suo risultato
                return handleDeleteUser(request, response);

            case "addProduct":
                return handleAddProduct(request, response);

            case "updateProduct":
                return handleUpdateProduct(request, response);

            case "deleteProduct":
                return handleDeleteProduct(request, response);

            // Caso di default se l'azione non corrisponde a nessun caso specifico
            default:
                // Invia una risposta di errore per azione non valida
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return null;
        }
    }

    private String handleGetUsers(HttpServletRequest request, HttpServletResponse response) {
        // Ottiene tutti gli utenti dal database
        List<Utente> allUtenti = utenteDAO.findAllUtente();

        // Filtra gli utenti non amministratori
        List<Utente> nonAdminUtenti = allUtenti.stream()
                .filter(utente -> "ADMIN".equalsIgnoreCase(utente.getRuolo()) == false)
                .collect(Collectors.toList());

        // Aggiunge la lista di utenti non amministratori come attributo della richiesta
        request.setAttribute("utenti", nonAdminUtenti);

        // Restituisce il nome della pagina JSP da visualizzare
        return "adminPage_utente.jsp";
    }

    private String handleGetAdminDashboard(HttpServletRequest request, HttpServletResponse response) {
        int utenteCount = utenteDAO.findAllUtente().size();
        int prodottoCount = prodottoDAO.findAllProdotto().size();
        int ordineCount = ordineDAO.findAllOrdine().size();

        request.setAttribute("utenteCount", utenteCount);
        request.setAttribute("prodottoCount", prodottoCount);
        request.setAttribute("ordineCount", ordineCount);

        return "adminPage.jsp";
    }

    private String handleGetOrdini(HttpServletRequest request, HttpServletResponse response) {
        List<Ordine> ordini = ordineDAO.findAllOrdine();

        request.setAttribute("ordini", ordini);

        return "adminPage_ordini.jsp";
    }

    // Metodo per gestire l'eliminazione di un utente
    private String handleDeleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Ottiene l'ID dell'utente dalla richiesta
        String userIdStr = request.getParameter("userId");

        // Verifica se l'ID dell'utente è valido
        if (isValidUserId(userIdStr)) {
            try {
                // Converte l'ID da stringa a intero
                int userId = Integer.parseInt(userIdStr);
                // Cerca l'utente nel database
                Utente utente = utenteDAO.findUtenteById(userId);

                // Se l'utente esiste, lo elimina e reindirizza
                if (utente != null) {
                    deleteUserAndRedirect(utente, request, response);
                } else {
                    // Se l'utente non esiste, invia una risposta di errore
                    sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Utente non trovato");
                }
            } catch (NumberFormatException e) {
                // Se l'ID non è un numero valido, invia una risposta di errore
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "ID utente non valido");
            }
        } else {
            // Se l'ID dell'utente non è fornito, invia una risposta di errore
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "ID utente richiesto");
        }

        return null;
    }

    // Metodo per verificare se l'ID dell'utente è valido
    private boolean isValidUserId(String userIdStr) {
        return userIdStr != null && !userIdStr.isEmpty();
    }

    // Metodo per eliminare l'utente e reindirizzare
    private void deleteUserAndRedirect(Utente utente, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Elimina l'utente dal database
        utenteDAO.deleteUtente(utente);
        // Reindirizza alla pagina di gestione degli utenti
        response.sendRedirect(request.getContextPath() + "/admin/AdminPage?entity=users");
    }

    // Metodo per inviare una risposta di errore
    private void sendErrorResponse(HttpServletResponse response, int errorCode, String errorMessage) throws IOException {
        response.sendError(errorCode, errorMessage);
    }

    // Metodo per gestire la richiesta di visualizzazione dei prodotti
    private String handleGetProducts(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("updateProduct")) {
                int prodottoId = Integer.parseInt(request.getParameter("productId"));
                Prodotto prodotto;

                try {
                    prodotto = prodottoDAO.findProdottoId(prodottoId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                request.setAttribute("action", "updateProduct");
                request.setAttribute("prodotto", prodotto);

                return "adminPage_updateProdotto.jsp";
            }

            if (request.getParameter("action").equals("addProduct")){
                request.setAttribute("action", "addProduct");
                return "adminPage_updateProdotto.jsp";
            }
        }


        List<Prodotto> prodotti = prodottoDAO.findAllProdotto();
        ArrayList<Immagine> immagini = new ArrayList<>();

        for (Prodotto prodotto : prodotti) {
            try {
                immagini.add(immagineDAO.findImmagineByProdottoId(prodotto.getProdotto_id()));

                // Fetch the category name
                Categoria categoria = categoriaDAO.findCategoriaId(prodotto.getCategoria_id());
                if (categoria != null) {
                    prodotto.setCategoriaNome(categoria.getNome());
                } else {
                    prodotto.setCategoriaNome("Categoria non trovata");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Aggiunge la lista dei prodotti e list delle immagini come attributi della richiesta
        request.setAttribute("prodotti", prodotti);
        request.setAttribute("immagini", immagini);

        // Restituisce il nome della pagina JSP per visualizzare i prodotti
        return DEFAULT_REDIRECT_PAGE;
    }

    private String handleAddProduct(HttpServletRequest request, HttpServletResponse response) {
        try {

            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));

            String nome = request.getParameter("nome");
            if (nome == null){
                response.setStatus(404);
                return DEFAULT_REDIRECT_PAGE;
            }

            String descrizione = request.getParameter("nome");

            if (descrizione == null){
                response.setStatus(404);
                return DEFAULT_REDIRECT_PAGE;
            }

            double prezzo = Double.parseDouble(request.getParameter("prezzo"));

            if (prezzo <= 0.00){
                response.setStatus(400);
                return DEFAULT_REDIRECT_PAGE;
            }

            int quantitaInventario = Integer.parseInt(request.getParameter("quantitaInventario"));

            // la quantita puo essere 0
            if (quantitaInventario < -1){
                response.setStatus(400);
                return DEFAULT_REDIRECT_PAGE;
            }

            Prodotto prodotto = new Prodotto();

            prodotto.setCategoria_id(categoriaId);
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantita_inventario(quantitaInventario);

            prodottoDAO.saveProdotto(prodotto);

        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            response.setStatus(404);
            return DEFAULT_REDIRECT_PAGE;
        } catch (NumberFormatException numberFormatException){
            response.setStatus(400);
            return DEFAULT_REDIRECT_PAGE;
        }

        return DEFAULT_REDIRECT_PAGE;
    }

    private String handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) {

        try {
            int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));
            Prodotto prodotto = prodottoDAO.findProdottoId(prodottoId);

            if (prodotto == null) {
                response.setStatus(404);
                return DEFAULT_REDIRECT_PAGE;
            }

            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("nome");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantitaInventario = Integer.parseInt(request.getParameter("quantitaInventario"));

            prodotto.setCategoria_id(categoriaId);
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantita_inventario(quantitaInventario);

            prodottoDAO.updateProdotto(prodotto);

        } catch (NullPointerException | SQLException nullPointerException) {
            nullPointerException.printStackTrace();
            response.setStatus(404);
            return DEFAULT_REDIRECT_PAGE;
        } catch (NumberFormatException numberFormatException){
            response.setStatus(400);
            return DEFAULT_REDIRECT_PAGE;
        }


        return DEFAULT_REDIRECT_PAGE;
    }

    private String handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));
            Prodotto prodotto = prodottoDAO.findProdottoId(prodottoId);

            if (prodotto == null) {
                response.setStatus(404);
                return DEFAULT_REDIRECT_PAGE;
            }

            prodottoDAO.deleteProdotto(prodotto);

        } catch (NullPointerException | SQLException nullPointerException) {
            nullPointerException.printStackTrace();
            response.setStatus(404);
            return DEFAULT_REDIRECT_PAGE;
        } catch (NumberFormatException numberFormatException){
            response.setStatus(400);
            return DEFAULT_REDIRECT_PAGE;
        }

        return "adminPage_prodotto.jsp";
    }

}

