package energy_dealer.application.energydealer.Controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.DAOImpl.CategoriaDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ImmagineDAOImpl;
import energy_dealer.application.energydealer.Model.DAOImpl.ProdottoDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.Categoria;
import energy_dealer.application.energydealer.Model.Entity.Immagine;
import energy_dealer.application.energydealer.Model.Entity.Prodotto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/immagini")
public class ProductPaginationController extends HttpServlet {
    private ImmagineDAOImpl immagineDAO;
    private ProdottoDAOImpl prodottoDAO;
    private CategoriaDAOImpl categoriaDAO;

    public ProductPaginationController() {
        immagineDAO = new ImmagineDAOImpl();
        prodottoDAO = new ProdottoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int page;
        int offset;

        // se il parametro page non era stato trovato, prende la prima pagina (offset 0)
        if (request.getParameter("page") == null) page = 0;
        else page = Integer.parseInt(request.getParameter("page"));

        // se era stata selezionata la prima pagina o una pagina negativa (per qualsiasi motivo) offset diventa 0
        if (page <= 1) offset = 0;
        // altrimenti si calcola offset facendo page -1 perche deve iniziare da 0
        else offset = (page - 1) * 10;

        // lista delle immagini paginate
        List<Immagine> immagini = immagineDAO.fetchPaginatedImages(offset);
        List<Prodotto> prodotti = prodottoDAO.fetchPaginatedProdotti(offset);
        List<Categoria> categorie = categoriaDAO.findAllCategoria();

        // oggetto per convertire un oggetto java in un JSON valido
        Gson gson = new Gson();

        // Map che contiene la lunghezza della pagina ritornata e le immagini paginate
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> body = new HashMap<>();

        body.put("immagini", immagini);
        body.put("prodotti", prodotti);
        body.put("categorie", categorie);

        payload.put("size", String.valueOf(immagini.size()));
        payload.put("body", body);

        // conversione in JSON
        String json = gson.toJson(payload);

        // scrive nel body della response l'oggetto JSON
        writeBody(response, json);
    }

    /**
     * Scrive nel corpo della response un oggetto Json ricevuto come parametro. Se per qualsiasi motivo non riesce a scrivere
     * ritorna Status code 500.
     *
     * @param response oggetto response in cui inserire il json o lo status code di errore
     * @param json oggetto json da inserire nella response
     * */
    public static void writeBody(HttpServletResponse response, String json) {
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(json);

        } catch (IOException e) {
            System.out.println("Errore nella scrittura del corpo nel CarrelloService [POST]");
            response.setStatus(500);
            throw new RuntimeException(e);

        } finally {
            // chiude il writer
            if(out != null) out.flush();
        }
    }
}
