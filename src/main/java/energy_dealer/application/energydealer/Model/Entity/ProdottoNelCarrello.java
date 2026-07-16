package energy_dealer.application.energydealer.Model.Entity;

public class ProdottoNelCarrello {
    private Prodotto prodotto;
    private int quantita_selezionata;

    public ProdottoNelCarrello(Prodotto prodotto, int quantita_selezionata) {
        this.prodotto = prodotto;
        this.quantita_selezionata = quantita_selezionata;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita_selezionata() {
        return quantita_selezionata;
    }

    public void setQuantita_selezionata(int quantita_selezionata) {
        this.quantita_selezionata = quantita_selezionata;
    }

    @Override
    public String toString() {
        return "ProdottoNelCarrello{" +
                "prodotto=" + prodotto +
                ", quantita_selezionata=" + quantita_selezionata +
                '}';
    }
}
