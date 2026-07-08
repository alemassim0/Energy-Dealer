package energy_dealer.application.energydealer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.Service.LogInService;
import java.io.IOException;

@WebServlet(name = "LogInController", value = "/user/LogInPage")
public class LogInController extends HttpServlet {

    private LogInService logInService;

    public void init() {
        logInService = new LogInService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/View/logInPage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logInService.logIn(request, response);
    }
}
