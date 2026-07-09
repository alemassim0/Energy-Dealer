package energy_dealer.application.energydealer.Model.Entity;

public class Categoria {

    private int categoria_id;
    private String nome;

    public Categoria(int categoria_id, String nome) {
        this.categoria_id = categoria_id;
        this.nome = nome;
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
}
