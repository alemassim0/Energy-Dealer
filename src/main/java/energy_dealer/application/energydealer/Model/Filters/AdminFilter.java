package energy_dealer.application.energydealer.Model.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import energy_dealer.application.energydealer.Model.Service.LogOutService;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    LogOutService logOutService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logOutService = new LogOutService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get session and user role
        HttpSession session = httpRequest.getSession(false);

        String userRole = null;
        if (session != null) {
            userRole = (String) session.getAttribute("userRole");
        }

        // URIs
        String loginURI = httpRequest.getContextPath() + "/user/LogInPage";
        String requestURI = httpRequest.getRequestURI();

        boolean isLoginRequest = requestURI.equals(loginURI);
        boolean isAdminRequest = requestURI.startsWith(httpRequest.getContextPath() + "/admin");

        // Check if user is logged in
        boolean isLoggedIn = logOutService.isLoggedIn(httpRequest);

        if (!isLoggedIn) {
            if (!isLoginRequest) {
                // If the user is not logged in and is not requesting the login page, redirect to the login page
                httpResponse.sendRedirect(loginURI);
                return;
            }
            // If the user is not logged in and is requesting the login page, allow the request
            chain.doFilter(request, response);
            return;
        }

        // User is logged in
        if (isLoginRequest) {
            // If the user is logged in and requesting the login page, redirect to the homepage
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/HomePage");
            return;
        }

        if (isAdminRequest && !"ADMIN".equals(userRole)) {
            // If the user is requesting an admin page and is not an admin, redirect to the homepage
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/HomePage");
            return;
        }

        // Otherwise, continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

