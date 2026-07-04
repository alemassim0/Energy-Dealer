package energy_dealer.application.energydealer.Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import energy_dealer.application.energydealer.Model.Service.LogOutService;
import java.io.IOException;

@WebServlet(name = "LogOut", value = "/user/LogOut")
public class LogOutController extends HttpServlet {

    private LogOutService logOutService;

    public void init() {
        logOutService = new LogOutService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Checks if the user is logged in by calling the isLoggedIn method from logOutService,
        // passing the HttpServletRequest object request as an argument
        if (logOutService.isLoggedIn(request)) {
            // If the user is logged in (isLoggedIn returns true), calls the logOutUser method from logOutService,
            // Handles the logout process, which involves invalidating the session and clearing relevant cookie
            logOutService.logOutUser(request, response);
        } else {
            // If the user is not logged in (isLoggedIn returns false), execution proceeds to the else block
            // Redirects the user to the login page
            // This happens when the user tries to access the logout functionality without being logged in
            response.sendRedirect(request.getContextPath() + "/user/LogInPage");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}