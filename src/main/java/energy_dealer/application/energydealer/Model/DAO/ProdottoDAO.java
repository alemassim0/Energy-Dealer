package energy_dealer.application.energydealer.Model.DAO;

import energy_dealer.application.energydealer.Model.Entity.*;

import java.sql.SQLException;
import java.util.List;

public interface ProdottoDAO {

    Prodotto findProdottoId(int prodotto_id) throws SQLException;

    List<Prodotto> findAllProdotto();

    void saveProdotto(Prodotto prodotto);

    void updateProdotto(Prodotto prodotto);

    void deleteProdotto(Prodotto prodotto);

    Prodotto getProdottoByIdDescrizioneNomeCategoria(int id, String nome, int categoria);
    List<Prodotto> fetchPaginatedProdotti(int offset);
}
