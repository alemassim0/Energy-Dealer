package energy_dealer.application.energydealer.Model.DAO;

import energy_dealer.application.energydealer.Model.Entity.Categoria;

import java.util.List;

public interface CategoriaDAO {

    Categoria findCategoriaId(int categoria_id);
    Categoria findCategoriaNome(String nome);

    List<Categoria> findAllCategoria();

    void saveCategoria(Categoria categoria);

    void updateCategoria(Categoria categoria);

    void deleteCategoria(Categoria categoria);
}
