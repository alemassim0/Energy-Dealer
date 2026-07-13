package energy_dealer.application.energydealer.Model.DAOImpl;

import energy_dealer.application.energydealer.Model.DAO.CategoriaDAO;
import energy_dealer.application.energydealer.Model.Entity.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    DBConnection db = new DBConnection();
    private Connection connection;

    public CategoriaDAOImpl() {
        try {
            connection = db.getConnection();
        } catch (SQLException sqlException) {
            System.out.println("Failed to create categoriaDaoImpl");
            sqlException.printStackTrace();
        }
    }

    @Override
    public Categoria findCategoriaId(int categoria_id) {
        final String QUERY = "SELECT * FROM categoria WHERE categoria_id=" + categoria_id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            // Non viene soltanto visto se il prodotto fa parte della categoria, ma viene anche spostato il cursore
            if (resultSet.next()) {
                Categoria categoria = new Categoria(
                        resultSet.getInt("categoria_id"),
                        resultSet.getString("nome")
                );
                return categoria;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Categoria findCategoriaNome(String nome) {
        String QUERY = "SELECT * FROM categoria WHERE nome = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Categoria(resultSet.getInt("categoria_id"), resultSet.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Categoria con nome " + nome + " non trovata");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Categoria> findAllCategoria() {
        final String QUERY = "SELECT * FROM categoria";
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);

            ResultSet resultSet = statement.executeQuery();

            List<Categoria> categorie = new ArrayList<>();

            while (resultSet.next()) {
                categorie.add(new Categoria(
                        resultSet.getInt("categoria_id"),
                        resultSet.getString("nome")));
            }
            return categorie;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveCategoria(Categoria categoria) {

    }

    @Override
    public void updateCategoria(Categoria categoria) {

    }

    @Override
    public void deleteCategoria(Categoria categoria) {

    }
}
