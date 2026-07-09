package energy_dealer.application.energydealer.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import energy_dealer.application.energydealer.Model.Entity.Carrello;
import energy_dealer.application.energydealer.Model.Service.ProdottoService;
import java.io.IOException;

@WebServlet(name = "HomePageServlet", value = "/HomePage")
public class HomePageServlet extends HttpServlet {

    private ProdottoService prodottoService;

    public void init() {
        prodottoService = new ProdottoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) == null)
            request.getSession();

        if (request.getSession(false).getAttribute("carrello") == null)
            request.getSession().setAttribute("carrello", new Carrello());

        prodottoService.getProductsForHomePage(request, response);

        getServletContext().getRequestDispatcher("/View/homePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){

    }
}