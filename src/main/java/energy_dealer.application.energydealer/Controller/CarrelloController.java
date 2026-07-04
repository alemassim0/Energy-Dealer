package energy_dealer.application.energydealer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.Service.CarrelloService;

import java.io.IOException;

@WebServlet(name = "CarrelloPageController", value = "/carrello")
public class CarrelloController extends HttpServlet {
    private CarrelloService carrelloService = new CarrelloService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        carrelloService.getProdotti(request, response);
        getServletContext().getRequestDispatcher("/View/carrelloPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        carrelloService.addProdotto(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        carrelloService.deleteProdotto(request, response);
    }
}
