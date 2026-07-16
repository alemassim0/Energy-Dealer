package energy_dealer.application.energydealer.Model.DAOImpl;

import energy_dealer.application.energydealer.Model.DAO.OrdineDAO;
import energy_dealer.application.energydealer.Model.Entity.Ordine;
import energy_dealer.application.energydealer.Model.Service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAOImpl implements OrdineDAO {

    private Connection connection;

    public OrdineDAOImpl(){
        try{
            DBConnection db = new DBConnection();
            connection = db.getConnection();

        } catch (SQLException sqlException) {
            System.out.println("Failed to create OrdineDAOImpl");
            sqlException.printStackTrace();
        }
    }

    @Override
    public Ordine findOrdineId(int ordine_id) {
        return null;
    }

    @Override
    public List<Ordine> findAllOrdine() {
        String QUERY = "SELECT * FROM ordine";
        List<Ordine> ordini = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            // Execute the query and store the result in a ResultSet object
            ResultSet resultSet = statement.executeQuery();
            // If a row is found, create and return a Utente object with the retrieved data
            while (resultSet.next()) {
                Ordine ordine = new Ordine();
                ordine.setOrdine_id(resultSet.getInt("ordine_id"));
                ordine.setUtente_id(resultSet.getInt("utente_id"));
                ordine.setData_ordine(resultSet.getObject("data_ordine", LocalDateTime.class));
                ordine.setStato_ordine(resultSet.getString("stato_ordine"));
                ordine.setStato_pagamento(resultSet.getString("stato_pagamento"));
                ordine.setTotale(resultSet.getDouble("totale"));
                ordine.setIndirizzo_spedizione(resultSet.getString("indirizzo_spedizione"));
                ordine.setNote(resultSet.getString("note"));
                ordine.setProdotti_acquistati(resultSet.getString("prodotti_acquistati"));

                ordini.add(ordine);
            }
        } catch (SQLException e) {
            // If an SQLException occurs during the execution of the query, it is caught and wrapped in a RuntimeException
            throw new RuntimeException(e);
        }
        return ordini;
    }

    @Override
    public void saveOrdine(Ordine ordine) throws SQLException {
        // Query SQL per aggiornare una riga dalla tabella 'prodotto' dove 'prodotto_id' corrisponde
        String QUERY = "INSERT INTO ordine(utente_id, data_ordine, stato_ordine, stato_pagamento, totale, indirizzo_spedizione, note, prodotti_acquistati) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        PreparedStatement statement = connection.prepareStatement(QUERY);
        // Imposta i parametri con i valori del oggetto prodotto dato in impasto al metodo
        statement.setInt(1, ordine.getUtente_id());
        statement.setObject(2, ordine.getData_ordine());
        statement.setString(3, ordine.getStato_ordine());
        statement.setString(4, ordine.getStato_pagamento());
        statement.setDouble(5, ordine.getTotale());
        statement.setString(6, ordine.getIndirizzo_spedizione());
        statement.setString(7, ordine.getNote());
        statement.setString(8, ordine.getProdotti_acquistati());

        // Esegue la query di aggiornamento
        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void updateOrdine(Ordine ordine) {

    }

    @Override
    public void deleteOrdine(Ordine ordine) {

    }

    @Override
    public Ordine findLastOrderByUserId(int utenteId) {
        String QUERY = "select * from ordine where utente_id = ? order by ordine_id desc limit 1;";

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setObject(1, utenteId);

            // Execute the query and store the result in a ResultSet object
            ResultSet resultSet = statement.executeQuery();
            // If a row is found, create and return a Utente object with the retrieved data
            if (resultSet.next()) {
                Ordine ordine = new Ordine();

                ordine.setOrdine_id(resultSet.getInt("ordine_id"));
                ordine.setUtente_id(resultSet.getInt("utente_id"));
                ordine.setData_ordine(resultSet.getObject("data_ordine", LocalDateTime.class));
                ordine.setStato_ordine(resultSet.getString("stato_ordine"));
                ordine.setStato_pagamento(resultSet.getString("stato_pagamento"));
                ordine.setTotale(resultSet.getDouble("totale"));
                ordine.setIndirizzo_spedizione(resultSet.getString("indirizzo_spedizione"));
                ordine.setNote(resultSet.getString("note"));

                return ordine;
            }
        } catch (SQLException e) {
            // If an SQLException occurs during the execution of the query, it is caught and wrapped in a RuntimeException
            throw new RuntimeException(e);
        }
        return null;
    }
}
