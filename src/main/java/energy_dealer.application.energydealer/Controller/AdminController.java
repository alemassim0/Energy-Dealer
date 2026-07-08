package energy_dealer.application.energydealer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.Service.AdminService;
import java.io.IOException;

@WebServlet(name = "AdminPageServlet", value = "/admin/AdminPage")
public class AdminController extends HttpServlet {
    // Dichiara una variabile di istanza privata di tipo AdminService
    private AdminService adminService;

    // Sovrascrive il metodo init() di HttpServlet
    @Override
    public void init() throws ServletException {
        // Inizializza adminService creando una nuova istanza di AdminService
        adminService = new AdminService();
    }

    // Sovrascrive il metodo doGet per gestire le richieste HTTP GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chiama il metodo handleGetRequest di adminService e memorizza il risultato
        String whichAdminPage = adminService.handleGetRequest(request, response);

        // Se viene restituito un nome di pagina (non null)
        if (whichAdminPage != null) {
            // Inoltra la richiesta alla pagina JSP appropriata nella directory AdminViews
            getServletContext().getRequestDispatcher("/View/AdminViews/" + whichAdminPage).forward(request, response);
        }
    }

    // Sovrascrive il metodo doPost per gestire le richieste HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chiama il metodo handlePostRequest di adminService e memorizza il risultato
        String result = adminService.handlePostRequest(request, response);

        // Se viene restituito un nome di pagina (non null)
        if (result != null) {
            // Inoltra la richiesta alla pagina JSP appropriata nella directory AdminViews
            getServletContext().getRequestDispatcher("/View/AdminViews/" + result).forward(request, response);
        }
    }
}
