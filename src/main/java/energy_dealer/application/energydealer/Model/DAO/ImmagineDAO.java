package energy_dealer.application.energydealer.Model.DAO;

import energy_dealer.application.energydealer.Model.Entity.Immagine;

import java.sql.SQLException;
import java.util.List;

public interface ImmagineDAO {

    Immagine findImmagineId(int immagine_id) throws SQLException;
    Immagine findImmagineByProdottoId(int immagine_id) throws SQLException;

    List<Immagine> findAllImmagine();

    void saveImmagine(Immagine immagine);

    void updateImmagine(Immagine immagine);

    void deleteImmagine(Immagine immagine);
}
