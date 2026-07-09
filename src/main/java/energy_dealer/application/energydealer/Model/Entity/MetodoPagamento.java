package energy_dealer.application.energydealer.Model.Entity;

import java.time.LocalDate;

public class MetodoPagamento {

    private int metodo_pagamento_id;
    private int utente_id;
    private String numero_carta;
    private String nome_titolare_carta;
    private LocalDate data_scadenza;
    private String tipo_carta;

    public MetodoPagamento(int metodo_pagamento_id, int utente_id, String numero_carta, String nome_titolare_carta, LocalDate data_scadenza, String tipo_carta) {
        this.metodo_pagamento_id = metodo_pagamento_id;
        this.utente_id = utente_id;
        this.numero_carta = numero_carta;
        this.nome_titolare_carta = nome_titolare_carta;
        this.data_scadenza = data_scadenza;
        this.tipo_carta = tipo_carta;
    }

    public MetodoPagamento(){}

    public int getMetodo_pagamento_id() {
        return metodo_pagamento_id;
    }

    public void setMetodo_pagamento_id(int metodo_pagamento_id) {
        this.metodo_pagamento_id = metodo_pagamento_id;
    }

    public int getUtente_id() {
        return utente_id;
    }

    public void setUtente_id(int utente_id) {
        this.utente_id = utente_id;
    }

    public String getNumero_carta() {
        return numero_carta;
    }

    public void setNumero_carta(String numero_carta) {
        this.numero_carta = numero_carta;
    }

    public String getNome_titolare_carta() {
        return nome_titolare_carta;
    }

    public void setNome_titolare_carta(String nome_titolare_carta) {
        this.nome_titolare_carta = nome_titolare_carta;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public String getTipo_carta() {
        return tipo_carta;
    }

    public void setTipo_carta(String tipo_carta) {
        this.tipo_carta = tipo_carta;
    }

    @Override
    public String toString() {
        return "MetodoPagamento{" +
                "metodo_pagamento_id=" + metodo_pagamento_id +
                ", utente_id=" + utente_id +
                ", numero_carta='" + numero_carta + '\'' +
                ", nome_titolare_carta='" + nome_titolare_carta + '\'' +
                ", data_scadenza=" + data_scadenza +
                ", tipo_carta='" + tipo_carta + '\'' +
                '}';
    }
}
