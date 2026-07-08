package energy_dealer.application.energydealer.Model.Entity;


public class Prodotto {
    private int prodotto_id;
    private int categoria_id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita_inventario;
    private String categoriaNome;

    public Prodotto(int prodotto_id, int categoria_id, String nome, String descrizione, double prezzo, int quantita_inventario) {
        this.prodotto_id = prodotto_id;
        this.categoria_id = categoria_id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita_inventario = quantita_inventario;
    }

    public Prodotto(){}

    public Prodotto(int quantita_inventario) {
        this.quantita_inventario = quantita_inventario;
    }

    public int getProdotto_id() {
        return prodotto_id;
    }

    public void setProdotto_id(int prodotto_id) {
        this.prodotto_id = prodotto_id;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita_inventario() {
        return quantita_inventario;
    }

    public void setQuantita_inventario(int quantita_inventario) {
        this.quantita_inventario = quantita_inventario;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "prodotto_id=" + prodotto_id +
                ", categoria_id=" + categoria_id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", quantita_inventario=" + quantita_inventario +
                '}';
    }
}