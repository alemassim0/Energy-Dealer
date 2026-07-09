package energy_dealer.application.energydealer.Model.Entity;

import java.time.LocalDateTime;

public class Ordine {

    private int ordine_id;
    private int utente_id;
    private LocalDateTime data_ordine;
    private String stato_ordine;
    private String stato_pagamento;
    private double totale;
    private String indirizzo_spedizione;
    private String note;
    private String prodotti_acquistati;

    public Ordine(int ordine_id, int utente_id, LocalDateTime data_ordine, String stato_ordine, String stato_pagamento, double totale, String indirizzo_spedizione, String note, String prodotti_aquistati) {
        this.ordine_id = ordine_id;
        this.utente_id = utente_id;
        this.data_ordine = data_ordine;
        this.stato_ordine = stato_ordine;
        this.stato_pagamento = stato_pagamento;
        this.totale = totale;
        this.indirizzo_spedizione = indirizzo_spedizione;
        this.note = note;
        this.prodotti_acquistati = prodotti_aquistati;
    }

    public Ordine(){

    }

    public int getOrdine_id() {
        return ordine_id;
    }

    public void setOrdine_id(int ordine_id) {
        this.ordine_id = ordine_id;
    }

    public int getUtente_id() {
        return utente_id;
    }

    public void setUtente_id(int utente_id) {
        this.utente_id = utente_id;
    }

    public LocalDateTime getData_ordine() {
        return data_ordine;
    }

    public void setData_ordine(LocalDateTime data_ordine) {
        this.data_ordine = data_ordine;
    }

    public String getStato_ordine() {
        return stato_ordine;
    }

    public void setStato_ordine(String stato_ordine) {
        this.stato_ordine = stato_ordine;
    }

    public String getStato_pagamento() {
        return stato_pagamento;
    }

    public void setStato_pagamento(String stato_pagamento) {
        this.stato_pagamento = stato_pagamento;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getIndirizzo_spedizione() {
        return indirizzo_spedizione;
    }

    public void setIndirizzo_spedizione(String indirizzo_spedizione) {
        this.indirizzo_spedizione = indirizzo_spedizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProdotti_acquistati() {
        return prodotti_acquistati;
    }

    public void setProdotti_acquistati(String prodotti_aquistati) {
        this.prodotti_acquistati = prodotti_aquistati;
    }
}
