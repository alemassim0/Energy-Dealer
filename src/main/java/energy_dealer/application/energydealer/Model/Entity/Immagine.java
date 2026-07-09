package energy_dealer.application.energydealer.Model.Entity;

public class Immagine {

    private int immagine_id;
    private int prodotto_id;
    private String url;

    public Immagine(int immagine_id, int prodotto_id, String url) {
        this.immagine_id = immagine_id;
        this.prodotto_id = prodotto_id;
        this.url = url;
    }

    public int getImmagine_id() {
        return immagine_id;
    }

    public void setImmagine_id(int immagine_id) {
        this.immagine_id = immagine_id;
    }

    public int getProdotto_id() {
        return prodotto_id;
    }

    public void setProdotto_id(int prodotto_id) {
        this.prodotto_id = prodotto_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Immagine{" +
                "immagine_id=" + immagine_id +
                ", prodotto_id=" + prodotto_id +
                ", url='" + url + '\'' +
                '}';
    }
}
