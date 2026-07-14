package energy_dealer.application.energydealer.Model.Service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.DAOImpl.OrdineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ProdottoDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.UtenteDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OrdineService {
    private OrdineDAOImpl ordineDAO;
    private UtenteDAOImpl utenteDAO;
    private ProdottoDAOImpl prodottoDAO;

    public OrdineService() {
        ordineDAO = new OrdineDAOImpl();
        utenteDAO = new UtenteDAOImpl();
        prodottoDAO = new ProdottoDAOImpl();
    }

    public void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null) {
            response.setStatus(400);
            System.out.println("Impossibile creare ordine senza sessione");
            return;
        }

        if (request.getSession(false).getAttribute("carrello") == null) {
            response.setStatus(400);
            System.out.println("Impossibile creare ordine senza carrello");
            return;
        }

        Carrello carrello = (Carrello) request.getSession(false).getAttribute("carrello");

        if (carrello.getProdotti().isEmpty()) {
            response.setStatus(400);
            System.out.println("Impossibile creare ordine se il carrello e' vuoto");
            return;
        }

        String cardNumber = request.getParameter("card_number");

        if (cardNumber == null) {
            response.setStatus(400);
            return;
        }

        if (cardNumber.length() != 16) {
            response.setStatus(400);
            System.out.println("Il numero della carta deve essere di 16 caratteri");
            return;
        }

        try {
            Integer.parseInt(cardNumber.substring(0, cardNumber.length() / 2));
            Integer.parseInt(cardNumber.substring(cardNumber.length() / 2));
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Il numero della carta non e' un numero[" + cardNumber + "]");
            response.setStatus(400);
            return;
        }

        String expirationDate = request.getParameter("expiration_date");

        if (expirationDate == null) {
            response.setStatus(400);
            System.out.println("La data di scadenza della carta e' obbligatoria per creare l'ordine");
            return;
        }

        String CVV = request.getParameter("CVV");

        if (CVV == null) {
            response.setStatus(400);
            System.out.println("CVV della carta obbligatorio per creare l'ordine");
            return;
        }

        if (CVV.length() != 3) {
            response.setStatus(400);
            System.out.println("Il CVV della carta deve essere di 3 caratteri");
            return;
        }

        try {
            Integer.parseInt(CVV);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("CVV non e' un numero");
            response.setStatus(400);
            return;
        }

        String cardHolderFullName = request.getParameter("card_holder_full_name");

        if (cardHolderFullName == null) {
            response.setStatus(400);
            System.out.println("Nome completo del proprietario della carta obbligatorio per creare l'ordine");
            return;
        }

        String country = request.getParameter("country");

        if (country == null) {
            response.setStatus(400);
            System.out.println("Il campo paese non deve essere null");
            return;
        }

        if (country.isEmpty() || country.isBlank()) {
            response.setStatus(400);
            System.out.println("Il campo paese non deve essere vuoto ");
            return;
        }

        if (!country.equals("Italia")) {
            response.setStatus(400);
            System.out.println("Il paese deve essere necessariamente Italia");
            return;
        }

        String region = request.getParameter("regions");

        if (region == null) {
            response.setStatus(400);
            System.out.println("Regione non deve essere null");
            return;
        }

        if (region.isEmpty() || region.isBlank()) {
            response.setStatus(400);
            System.out.println("Regione non deve essere vuota");
            return;
        }

        String province = request.getParameter("province");

        if (province == null) {
            response.setStatus(400);
            System.out.println("Provincia non deve essere null");
            return;
        }

        if (province.isEmpty() || province.isBlank()) {
            response.setStatus(400);
            System.out.println("Provincia non deve essere vuota");
            return;
        }

        String city = request.getParameter("city");

        if (city == null) {
            response.setStatus(400);
            System.out.println("Citta non deve essere null");
            return;
        }

        if (city.isEmpty() || city.isBlank()) {
            response.setStatus(400);
            System.out.println("Citta non deve essere vuota");
            return;
        }

        String postalCode = request.getParameter("postal_code");

        if (postalCode == null) {
            response.setStatus(400);
            System.out.println("codice postale non deve essere null");
            return;
        }

        if (postalCode.isEmpty() || postalCode.isBlank()) {
            response.setStatus(400);
            System.out.println("codice postale non deve essere vuoto");
            return;
        }

        String address = request.getParameter("address");

        if (address == null) {
            response.setStatus(400);
            System.out.println("Indirizzo non deve essere null");
            return;
        }

        if (address.isEmpty() || address.isBlank()) {
            response.setStatus(400);
            System.out.println("Indirizzo non deve essere vuoto");
            return;
        }

        String email = request.getParameter("email");

        if (!isValidEmail(email)) {
            response.setStatus(400);
            System.out.println("Indirizzo email non valido");
            return;
        }

        String phoneNumber = request.getParameter("phone_number");

        if (!isValidPhoneNumber(phoneNumber)) {
            response.setStatus(400);
            System.out.println("Numero di telefono non valido");
            return;
        }

        Utente utente = utenteDAO.findUtenteByEmail(email);
        String fullAddress = address + ", " + city + ", " + postalCode + ", " + province + ", " + region + ", " + country;

        // utente e' anonimo
        if (request.getSession(false).getAttribute("userEmail") == null) {
            utente = new Utente();

            utente.setEmail(email);
            utente.setRuolo("anonimo");
            utente.setIndirizzo_spedizione(fullAddress);
            utente.setNumero_telefono(phoneNumber);
            utente.setPassword("@DefaultPassword123");  // agli utenti anonimi non è permesso autenticarsi, quindi e' okay usare la stessa password per tutti.

            try {
                utenteDAO.saveUtente(utente);

            } catch (SQLException sqlIntegrityConstraintViolationException) {
                utente = utenteDAO.findUtenteByEmail(email);

                if (!utente.getRuolo().equals("anonimo")) {
                    System.out.println("Utente esiste gia nel sistema");
                    request.setAttribute("email", email);
                    request.setAttribute("errorMessage", "Email indicata risulta registrata nel sistema, creare account per favore o effettuare log in.");
                    request.getRequestDispatcher("/View/logInPage.jsp").forward(request, response);
                    return;
                }
            }

        }

        utente = utenteDAO.findUtenteByEmail(email);


        List<ProdottoNelCarrello> prodottiNelCarrello = carrello.getProdotti();

        Map<String, Integer> prodottiAcquistati = new HashMap<>();
        for (ProdottoNelCarrello prodottoNelCarrello : prodottiNelCarrello) {
            prodottiAcquistati.put(prodottoNelCarrello.getProdotto().getNome(), prodottoNelCarrello.getQuantita_selezionata());
            prodottoDAO.decrementProdottoIfPossible(prodottoNelCarrello.getProdotto(), prodottoNelCarrello.getQuantita_selezionata());
        }

        Gson gson = new Gson();
        String prodottiAcquistatiJson = gson.toJson(prodottiAcquistati);

        Ordine ordine = new Ordine();
        ordine.setUtente_id(utente.getUtente_id());
        ordine.setData_ordine(LocalDateTime.now());
        ordine.setStato_ordine("COMPLETATO");
        ordine.setStato_pagamento("PAGATO");
        ordine.setTotale(carrello.getTotal());
        ordine.setIndirizzo_spedizione(fullAddress);
        if (request.getParameter("note") == null) ordine.setNote("Nessuna nota particolare.");
        else ordine.setNote(request.getParameter("note"));
        ordine.setProdotti_acquistati(prodottiAcquistatiJson);

        try {
            ordineDAO.saveOrdine(ordine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ordine = ordineDAO.findLastOrderByUserId(utente.getUtente_id());

        request.setAttribute("ordine", ordine);
        request.getRequestDispatcher("/View/orderSuccessfulPage.jsp").forward(request, response);

    }

    private boolean isValidEmail(String email) {

        // This method checks if the provided email is valid
        // A regular expression (regex) for validating email addresses.
        // It checks if the email contains alphanumeric characters, '+', '_', '.', or '-' before the '@' symbol,
        // followed by any character after the '@' symbol.
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Creates a Pattern object by compiling a regular expression stored in the EMAIL_REGEX variable
        // This Pattern object can then be used for matching email addresses against the defined pattern in EMAIL_REGEX
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        // It checks if the email is not null and if it matches the pattern
        // If both conditions are true, it returns true; otherwise, it returns false
        return email != null && pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneNumberRE = "^\\+(?:[0-9]●?){6,14}[0-9]$";

//        ^           Assert position at the beginning of the string.
//        \+          Match a literal "+" character.
//        (?:         Group but don't capture:
//        [0-9]       Match a digit.
//        \x20        Match a space character
//        ?           between zero and one time.
//        )           End the noncapturing group.
//        {6,14}      Repeat the group between 6 and 14 times.
//        [0-9]       Match a digit.
//        $           Assert position at the end of the string.

        Pattern pattern = Pattern.compile(phoneNumberRE);

        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
}
