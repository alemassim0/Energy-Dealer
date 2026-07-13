package energy_dealer.application.energydealer.Model.DAOImpl;

import energy_dealer.application.energydealer.Model.DAO.UtenteDAO;
import energy_dealer.application.energydealer.Model.Entity.Utente;
import energy_dealer.application.energydealer.Model.Service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOImpl implements UtenteDAO {

    private Connection connection;

    public UtenteDAOImpl() {
        try {
            DBConnection db = new DBConnection();
            connection = db.getConnection();
        } catch (SQLException sqlException) {
            System.out.println("Failed to create UtenteDAOImpl");
            sqlException.printStackTrace();
        }
    }

    @Override
    public Utente findUtenteById(int utente_id) {
        // Query SQL per selezionare tutte le colonne dalla tabella 'utente' dove 'utente_id' corrisponde all'input
        String QUERY = "SELECT * FROM utente WHERE utente_id = ?";

        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            // Imposta il primo parametro del prepared statement con l'utente_id di input
            statement.setInt(1, utente_id);
            // Esegue la query e ottiene il result set
            ResultSet resultSet = statement.executeQuery();

            // Se non ci sono risultati, restituisce null (utente non trovato)
            if (!resultSet.next()) {
                return null;
            }

            // Crea un nuovo oggetto Utente per memorizzare i dati recuperati
            Utente utente = new Utente();
            // Imposta ogni campo dell'oggetto Utente utilizzando i dati dal result set
            utente.setUtente_id(resultSet.getInt("utente_id"));
            utente.setEmail(resultSet.getString("email"));
            utente.setPassword(resultSet.getString("password"));
            utente.setRuolo(resultSet.getString("ruolo"));
            utente.setNome(resultSet.getString("nome"));
            utente.setCognome(resultSet.getString("cognome"));
            utente.setNumero_telefono(resultSet.getString("numero_telefono"));
            utente.setIndirizzo_spedizione(resultSet.getString("indirizzo_spedizione"));

            // Restituisce l'oggetto Utente popolato
            return utente;
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nel trovare l'utente con ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Utente findUtenteByEmail(String email){
        // Query SQL per selezionare tutte le colonne dalla tabella 'utente' dove 'email' corrisponde all'input
        String QUERY = "SELECT * FROM utente WHERE email = ?";

        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            // Imposta il primo parametro del prepared statement con l'utente_id di input
            statement.setString(1, email);
            // Esegue la query e ottiene il result set
            ResultSet resultSet = statement.executeQuery();

            // Se non ci sono risultati, restituisce null (utente non trovato)
            if (!resultSet.next()) {
                return null;
            }

            // Crea un nuovo oggetto Utente per memorizzare i dati recuperati
            Utente utente = new Utente();

            // Imposta ogni campo dell'oggetto Utente utilizzando i dati dal result set
            utente.setUtente_id(resultSet.getInt("utente_id"));
            utente.setEmail(resultSet.getString("email"));
            utente.setPassword(resultSet.getString("password"));
            utente.setRuolo(resultSet.getString("ruolo"));
            utente.setNome(resultSet.getString("nome"));
            utente.setCognome(resultSet.getString("cognome"));
            utente.setNumero_telefono(resultSet.getString("numero_telefono"));
            utente.setIndirizzo_spedizione(resultSet.getString("indirizzo_spedizione"));

            // Restituisce l'oggetto Utente popolato
            return utente;
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nel trovare l'utente per Email: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Utente> findAllUtente() {
        String QUERY = "SELECT * FROM utente";
        List<Utente> utenti = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            // Execute the query and store the result in a ResultSet object
            ResultSet resultSet = statement.executeQuery();
            // If a row is found, create and return a Utente object with the retrieved data
            while (resultSet.next()) {
                Utente utente = new Utente();
                utente.setEmail(resultSet.getString("email"));
                utente.setPassword(resultSet.getString("password"));
                utente.setRuolo(resultSet.getString("ruolo"));
                utente.setUtente_id(resultSet.getInt("utente_id"));
                utente.setNome(resultSet.getString("nome"));
                utente.setCognome(resultSet.getString("cognome"));
                utente.setNumero_telefono(resultSet.getString("numero_telefono"));
                utente.setIndirizzo_spedizione(resultSet.getString("indirizzo_spedizione"));

                utenti.add(utente);
            }
        } catch (SQLException e) {
            // If an SQLException occurs during the execution of the query, it is caught and wrapped in a RuntimeException
            throw new RuntimeException(e);
        }
        return utenti;
    }

    @Override
    public Utente getUtenteEmailAndPasswordByEmail(String email) {
        // SQL query to select the user details by email
        String QUERY = "SELECT email, password, ruolo FROM utente WHERE email = ?";

        // Ensure the Connection and PreparedStatement resources are closed automatically after the try block is executed
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            // Set the first parameter (denoted by ? in the query) to the provided email value
            statement.setString(1, email);

            // Execute the query and store the result in a ResultSet object
            ResultSet resultSet = statement.executeQuery();

            // If a row is found, create and return an Utente object with the retrieved data
            if (resultSet.next()) {
                Utente utente = new Utente();
                utente.setEmail(resultSet.getString("email"));
                utente.setPassword(resultSet.getString("password"));
                utente.setRuolo(resultSet.getString("ruolo"));
                return utente;
            } else {
                // If no rows were returned, return null
                return null;
            }
        } catch (SQLException e) {
            // If an SQLException occurs during the execution of the query, it is caught and wrapped in a RuntimeException
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUtente(Utente utente) throws SQLException {

        // This string defines an SQL query to insert a new row into the users table with the columns email and password
        String QUERY = "INSERT INTO utente (ruolo, nome, cognome, email, numero_telefono, indirizzo_spedizione, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // This statement ensures that the Connection and PreparedStatement resources are closed automatically after the try block is executed
        // connection is obtained from the DBConnection instance
        // statement is a PreparedStatement created using the SQL query
        PreparedStatement statement = connection.prepareStatement(QUERY);

        // Sets the first parameter (denoted by the first ? in the query) to the provided
        // email value from the utente object
        statement.setString(1, utente.getRuolo());
        statement.setString(2, utente.getNome());
        statement.setString(3, utente.getCognome());
        statement.setString(4, utente.getEmail());
        statement.setString(5, utente.getNumero_telefono());
        statement.setString(6, utente.getIndirizzo_spedizione());
        statement.setString(7, utente.getPassword());

        // Executes the update query to insert the new user into the database
        // This method is used for SQL statements that modify the database (INSERT, UPDATE, DELETE)
        statement.executeUpdate();
    }

    @Override
    public boolean isEmailExists(String email) {

        // This string defines an SQL query to count the number of rows in the users table where the email column matches a given value
        String QUERY = "SELECT COUNT(*) FROM utente WHERE email = ?";

        // This statement ensures that the Connection and PreparedStatement resources are closed automatically after the try block is executed
        // connection is obtained from the DBConnection instance
        // statement is a PreparedStatement created using the SQL query
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            // Sets the first parameter (denoted by ? in the query) to the provided email value
            statement.setString(1, email);

            // Executes the query and stores the result in a ResultSet object
            ResultSet resultSet = statement.executeQuery();

            // Moves the cursor to the first row of the ResultSet
            // Retrieves the integer value from the first column of the result set (COUNT(*))
            // If the value is greater than 0, it means the email exists in the database, so it returns true
            // Otherwise, it returns false
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            } else {

                // If no rows were returned, the email does not exist in the database, so it returns false
                return false;
            }
        } catch (SQLException e) {

            // If an SQLException occurs during the execution of the query,
            // it is caught and wrapped in a RuntimeException, which is then thrown
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUtente(Utente utente) throws SQLException {
        // This string defines an SQL query to insert a new row into the users table with the columns email and password
        String QUERY = "UPDATE utente set ruolo = ?, nome = ?, cognome = ?, email = ?, numero_telefono = ?, indirizzo_spedizione = ?, password = ? where utente_id = ?";

        // This statement ensures that the Connection and PreparedStatement resources are closed automatically after the try block is executed
        // connection is obtained from the DBConnection instance
        // statement is a PreparedStatement created using the SQL query
        PreparedStatement statement = connection.prepareStatement(QUERY);

        // Sets the first parameter (denoted by the first ? in the query) to the provided email value from the utente object
        statement.setString(1, utente.getRuolo());
        statement.setString(2, utente.getNome());
        statement.setString(3, utente.getCognome());
        statement.setString(4, utente.getEmail());
        statement.setString(5, utente.getNumero_telefono());
        statement.setString(6, utente.getIndirizzo_spedizione());
        statement.setString(7, utente.getPassword());
        statement.setInt(8, utente.getUtente_id());

        // Executes the update query to insert the new user into the database
        // This method is used for SQL statements that modify the database (INSERT, UPDATE, DELETE)
        statement.executeUpdate();
    }

    @Override
    public void deleteUtente(Utente utente) {
        String queryDeleteUserOrders = "DELETE FROM ordine WHERE utente_id = ?";
        String queryDeleteUserPaymentMethods = "DELETE FROM metodo_pagamento WHERE utente_id = ?";

        // Query SQL per eliminare una riga dalla tabella 'utente' dove 'utente_id' corrisponde
        String queryDeleteUser = "DELETE FROM utente WHERE utente_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(queryDeleteUserOrders);
            // Imposta il primo parametro del prepared statement con l'utente_id dell'oggetto Utente di input
            statement.setInt(1, utente.getUtente_id());
            // Esegue la query di aggiornamento (eliminazione degli ordini che referenziano l'utente)
            statement.executeUpdate();

            statement = connection.prepareStatement(queryDeleteUserPaymentMethods);
            // Imposta il primo parametro del prepared statement con l'utente_id dell'oggetto Utente di input
            statement.setInt(1, utente.getUtente_id());
            // Esegue la query di aggiornamento (eliminazione dei metodi di pagamento del utente)
            statement.executeUpdate();

            statement = connection.prepareStatement(queryDeleteUser);
            // Imposta il primo parametro del prepared statement con l'utente_id dell'oggetto Utente di input
            statement.setInt(1, utente.getUtente_id());
            // Esegue la query di aggiornamento (eliminazione del utente)
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nell'eliminazione dell'utente: " + e.getMessage(), e);
        }
    }
}
