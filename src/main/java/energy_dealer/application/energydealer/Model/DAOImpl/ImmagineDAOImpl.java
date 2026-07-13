package energy_dealer.application.energydealer.Model.DAOImpl;

import energy_dealer.application.energydealer.Model.DAO.ImmagineDAO;
import energy_dealer.application.energydealer.Model.Entity.Immagine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImmagineDAOImpl implements ImmagineDAO {

    private DBConnection db = new DBConnection();
    private Connection connection;

    public ImmagineDAOImpl() {
        try {
            connection = db.getConnection();
        } catch (SQLException sqlException){
            System.out.println("Failed to initialize immagine DAO");
            sqlException.printStackTrace();
        }
    }

    /**
     * Metodo helper che estrae la parte del codice che si ripeteva,
     * in particolare ritorna il risultato della query (Immagine o null) in base alla query passata in input
     *
     * @param query Query da eseguire nel db
     *
     * @return Immagine or Null : Ritorna immagine se esiste o null
     * */
    private Immagine getImmagine(String query){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Viene anche spostato il cursore
            if (resultSet.next()) {
                return new Immagine(
                        resultSet.getInt("immagine_id"),
                        resultSet.getInt("prodotto_id"),
                        resultSet.getString("url")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Immagine findImmagineId(int immagine_id) throws SQLException {

        final String QUERY = "SELECT * FROM immagine WHERE immagine_id=" + immagine_id;

        return getImmagine(QUERY);
    }

    @Override
    public Immagine findImmagineByProdottoId(int prodotto_id) throws SQLException {

        final String QUERY = "SELECT * FROM immagine WHERE prodotto_id = " + prodotto_id;

        return getImmagine(QUERY);
    }

    @Override
    public List<Immagine> findAllImmagine() {
        return List.of();
    }

    @Override
    public void saveImmagine(Immagine immagine) {

    }

    @Override
    public void updateImmagine(Immagine immagine) {

    }

    @Override
    public void deleteImmagine(Immagine immagine) {

    }

    public List<Immagine> fetchPaginatedImages(int offset) {
        final String QUERY = "SELECT * FROM immagine limit 10 offset ?";

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setInt(1, offset);

            ResultSet resultSet = statement.executeQuery();

            List<Immagine> immagini = new ArrayList<>();

            while(resultSet.next()) {
                immagini.add(new Immagine(
                        resultSet.getInt("immagine_id"),
                        resultSet.getInt("prodotto_id"),
                        resultSet.getString("url"))
                );
            }
            return immagini;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
