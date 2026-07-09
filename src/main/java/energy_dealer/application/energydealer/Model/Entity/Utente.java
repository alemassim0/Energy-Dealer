package energy_dealer.application.energydealer.Model.Entity;

public class Utente {

    private int utente_id;
    private String ruolo;
    private String nome;
    private String cognome;
    private String email;
    private String numero_telefono;
    private String indirizzo_spedizione;
    private String password;

    public Utente() {

    }

    public Utente(int utente_id, String password, String indirizzo_spedizione, String email, String numero_telefono, String cognome, String nome, String ruolo) {
        this.utente_id = utente_id;
        this.password = password;
        this.indirizzo_spedizione = indirizzo_spedizione;
        this.email = email;
        this.numero_telefono = numero_telefono;
        this.cognome = cognome;
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public int getUtente_id() {
        return utente_id;
    }

    public void setUtente_id(int utente_id) {
        this.utente_id = utente_id;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public String getIndirizzo_spedizione() {
        return indirizzo_spedizione;
    }

    public void setIndirizzo_spedizione(String indirizzo_spedizione) {
        this.indirizzo_spedizione = indirizzo_spedizione;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "utente_id=" + utente_id +
                ", ruolo='" + ruolo + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", numero_telefono='" + numero_telefono + '\'' +
                ", indirizzo_spedizione='" + indirizzo_spedizione + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

