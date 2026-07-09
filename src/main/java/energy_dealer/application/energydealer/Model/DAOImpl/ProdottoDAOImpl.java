package energy_dealer.application.energydealer.Model.DAOImpl;

import energy_dealer.application.energydealer.Model.DAO.ProdottoDAO;
import energy_dealer.application.energydealer.Model.Entity.Prodotto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAOImpl implements ProdottoDAO {

    private Connection connection;

    public ProdottoDAOImpl() {
        try {
            DBConnection db = new DBConnection();
            connection = db.getConnection();
        } catch (SQLException sqlException) {
            System.out.println("Failed to create prodottoDAOImpl");
            sqlException.printStackTrace();
        }
    }

    @Override
    public Prodotto findProdottoId(int prodotto_id) throws SQLException{
        // Definisce la query SQL per selezionare un prodotto specifico dal database
        final String QUERY = "SELECT * FROM prodotto WHERE prodotto_id=" +  prodotto_id;

        try {
            // Crea un oggetto Statement per eseguire la query SQL
            Statement statement = connection.createStatement();

            // Esegue la query e ottiene il risultato in un ResultSet
            ResultSet resultSet = statement.executeQuery(QUERY);

            // Verifica se c'è un risultato e sposta il cursore alla prima riga
            if (resultSet.next()) {
                // Crea e restituisce un nuovo oggetto Prodotto con i dati dal database
                return new Prodotto(
                        resultSet.getInt("prodotto_id"),
                        resultSet.getInt("categoria_id"),
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getDouble("prezzo"),
                        resultSet.getInt("quantita_inventario")
                );
            }
        } catch (SQLException e) {
            // In caso di errore SQL, lancia un'eccezione RuntimeException
            throw new RuntimeException(e);
        }

        // Se non viene trovato alcun prodotto, restituisce null
        return null;
    }

    @Override
    public List<Prodotto> findAllProdotto() {
        final String QUERY = "SELECT p.*, c.nome AS categoria_nome FROM prodotto p JOIN categoria c ON p.categoria_id = c.categoria_id = c.categoria_id";
        List<Prodotto> prodotti = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                Prodotto prodotto = new Prodotto(
                    resultSet.getInt("prodotto_id"),
                    resultSet.getInt("categoria_id"),
                    resultSet.getString("nome"),
                    resultSet.getString("descrizione"),
                    resultSet.getDouble("prezzo"),
                    resultSet.getInt("quantita_inventario")
                );
                prodotto.setCategoriaNome(resultSet.getString("categoria_nome"));
                prodotti.add(prodotto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    @Override
    public void saveProdotto(Prodotto prodotto) {
        // Query SQL per aggiornare una riga dalla tabella 'prodotto' dove 'prodotto_id' corrisponde
        String QUERY = "INSERT INTO prodotto(categoria_id, nome, descrizione, prezzo, quantita_inventario) VALUES (?, ?, ?, ?, ?)";

        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            // Imposta i parametri con i valori del oggetto prodotto dato in impasto al metodo
            statement.setInt(1, prodotto.getCategoria_id());
            statement.setString(2, prodotto.getNome());
            statement.setString(3, prodotto.getDescrizione());
            statement.setDouble(4, prodotto.getPrezzo());
            statement.setInt(5, prodotto.getQuantita_inventario());

            // Esegue la query di aggiornamento
            statement.executeUpdate();
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nell salvataggio del prodotto: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateProdotto(Prodotto prodotto) {
        // Query SQL per aggiornare una riga dalla tabella 'prodotto' dove 'prodotto_id' corrisponde
        String QUERY = "UPDATE prodotto set categoria_id = ?, nome = ?, descrizione = ?, prezzo = ?, quantita_inventario = ? where prodotto_id = ?";

        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement(QUERY)) {
            // Imposta i parametri con i valori del oggetto prodotto dato in impasto al metodo
            statement.setInt(1, prodotto.getCategoria_id());
            statement.setString(2, prodotto.getNome());
            statement.setString(3, prodotto.getDescrizione());
            statement.setDouble(4, prodotto.getPrezzo());
            statement.setInt(5, prodotto.getQuantita_inventario());
            statement.setInt(6, prodotto.getProdotto_id());

            // Esegue la query di aggiornamento
            statement.executeUpdate();
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nell'aggiornamento del prodotto: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProdotto(Prodotto prodotto) {
        // Query SQL per eliminare tutte le righe dalla tabella 'immagine' dove 'prodotto_id' corrisponde
        String query_immagine = "DELETE FROM immagine where prodotto_id = ?";

        // Query SQL per eliminare una riga dalla tabella 'prodotto' dove 'prodotto_id' corrisponde
        String query_prodotto = "DELETE FROM prodotto where prodotto_id = ?";

        // Non uso try-with-resources ma apro e chiudo manualmente
        try {
            PreparedStatement statement = connection.prepareStatement(query_immagine);
            // Imposta i parametri con i valori dell'oggetto prodotto dato in pasto al metodo
            statement.setInt(1, prodotto.getProdotto_id());

            // Esegue prima la query per eliminare le immagini relative al prodotto (fk)
            statement.executeUpdate();

            // Prepara e esegue la query per eliminare il prodotto
            statement = connection.prepareStatement(query_prodotto);
            statement.setInt(1, prodotto.getProdotto_id());
            statement.executeUpdate();

            // Chiude la risorsa
            statement.close();
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nella eliminazione del prodotto: " + e.getMessage(), e);
        }
    }

    @Override
    public Prodotto getProdottoByIdDescrizioneNomeCategoria(int id, String nome, int categoria) {
        String QUERY = "SELECT * FROM prodotto WHERE prodotto_id = ? AND nome = ? AND categoria_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            statement.setInt(1, id);
            statement.setString(2, nome);
            statement.setInt(3, categoria);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            System.out.println(resultSet.getInt(1));

            return new Prodotto(resultSet.getInt("prodotto_id"),
                    resultSet.getInt("categoria_id"),
                    resultSet.getString("nome"),
                    resultSet.getString("descrizione"),
                    resultSet.getDouble("prezzo"),
                    resultSet.getInt("quantita_inventario"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Prodotto> fetchPaginatedProdotti(int offset) {
        final String QUERY = "SELECT * FROM prodotto limit 10 offset ?";

        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setInt(1, offset);

            ResultSet resultSet = statement.executeQuery();

            List<Prodotto> prodotti = new ArrayList<>();

            while (resultSet.next()) {
                prodotti.add(new Prodotto(
                        resultSet.getInt("prodotto_id"),
                        resultSet.getInt("categoria_id"),
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getDouble("prezzo"),
                        resultSet.getInt("quantita_inventario")));
            }
            return prodotti;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean decrementProdottoIfPossible(Prodotto prodotto, int quantita_selezionata) {
        String queryFindProduct = "SELECT quantita_inventario from prodotto where prodotto_id = ?";

        // Query SQL per aggiornare una riga dalla tabella 'prodotto' dove 'prodotto_id' corrisponde
        String queryUpdateProdotto = "UPDATE prodotto set quantita_inventario = ? where prodotto_id = ?";


        // Blocco try-with-resources per chiudere automaticamente il PreparedStatement
        try {
            PreparedStatement statement = connection.prepareStatement(queryFindProduct);
            statement.setInt(1, prodotto.getProdotto_id());

            ResultSet resultSet = statement.executeQuery();
            Prodotto selectedProdotto = null;

            if (resultSet.next()) {
                selectedProdotto = new Prodotto(resultSet.getInt("quantita_inventario"));
            }

            if (selectedProdotto == null){
                System.out.println("Prodotto da decrementare non era stato trovato nel db. id prodotto[" + prodotto.getProdotto_id() + "]");
                return false;
            }

            if (selectedProdotto.getQuantita_inventario() < quantita_selezionata){
                System.out.println("Impossibile decrementare quantita prodotto. Quantita selezionata maggiore della quantita disponibile. id prodotto[" + prodotto.getProdotto_id() + "]");
                return false;
            }

            prodotto.setQuantita_inventario(selectedProdotto.getQuantita_inventario() - quantita_selezionata);

            statement = connection.prepareStatement(queryUpdateProdotto);

            // Imposta i parametri con i valori dell'oggetto prodotto dato in pasto al metodo
            statement.setInt(1, prodotto.getQuantita_inventario());
            statement.setInt(2, prodotto.getProdotto_id());

            // Esegue la query di aggiornamento
            statement.executeUpdate();
        } catch (SQLException e) {
            // Se si verifica un'eccezione SQL, lancia una RuntimeException con un messaggio di errore
            throw new RuntimeException("Errore nell'aggiornamento del prodotto: " + e.getMessage(), e);
        }

        return true;
    }
}
