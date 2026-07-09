package energy_dealer.application.energydealer.Model.Entity;

public class ArticoloOrdinato {

    private int articolo_ordinato_id;
    private int ordine_id;
    private int prodotto_id;
    private int quantita;
    private double sub_totale;

    public ArticoloOrdinato(int articolo_ordinato_id, int ordine_id, int prodotto_id, int quantita, double sub_totale) {
        this.articolo_ordinato_id = articolo_ordinato_id;
        this.ordine_id = ordine_id;
        this.prodotto_id = prodotto_id;
        this.quantita = quantita;
        this.sub_totale = sub_totale;
    }

    public int getArticolo_ordinato_id() {
        return articolo_ordinato_id;
    }

    public void setArticolo_ordinato_id(int articolo_ordinato_id) {
        this.articolo_ordinato_id = articolo_ordinato_id;
    }

    public int getOrdine_id() {
        return ordine_id;
    }

    public void setOrdine_id(int ordine_id) {
        this.ordine_id = ordine_id;
    }

    public int getProdotto_id() {
        return prodotto_id;
    }

    public void setProdotto_id(int prodotto_id) {
        this.prodotto_id = prodotto_id;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getSub_totale() {
        return sub_totale;
    }

    public void setSub_totale(double sub_totale) {
        this.sub_totale = sub_totale;
    }
}
