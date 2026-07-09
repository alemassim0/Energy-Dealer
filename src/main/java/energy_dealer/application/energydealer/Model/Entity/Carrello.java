package energy_dealer.application.energydealer.Model.Entity;

import java.util.ArrayList;

public class Carrello {
    private ArrayList<ProdottoNelCarrello> prodotti = new ArrayList<>();

    public void addProdotto(ProdottoNelCarrello prodotto){
        prodotti.add(prodotto);
    }

    public ArrayList<ProdottoNelCarrello> getProdotti(){
        return prodotti;
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "prodotti=" + prodotti +
                '}';
    }

    public int size(){
        return prodotti.size();
    }

    public double getTotal(){
        double total = 0.0;

        for(ProdottoNelCarrello prodotto : prodotti){
            total += prodotto.getProdotto().getPrezzo() * prodotto.getQuantita_selezionata();
        }

        return total;
    }
}
