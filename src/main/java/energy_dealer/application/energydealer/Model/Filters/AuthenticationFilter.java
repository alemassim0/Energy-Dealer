package energy_dealer.application.energydealer.Model.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import energy_dealer.application.energydealer.Model.Service.LogOutService;

import java.io.IOException;

@WebFilter("/user/*")
public class AuthenticationFilter implements Filter {

    private LogOutService logOutService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logOutService = new LogOutService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Cast the request and response to HTTP-specific types
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Define the URI for the login page
        String loginURI = httpRequest.getContextPath() + "/user/LogInPage";
        String registerURI = httpRequest.getContextPath() + "/user/RegistrazionePage";

        // Determine if the current request is for the login page
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isRegisterRequest = httpRequest.getRequestURI().equals(registerURI);

        // Check if the user is logged in
        boolean isLoggedIn = logOutService.isLoggedIn(httpRequest);

        // Redirect to login page if user is not logged in and is requesting the registration page
        if (!isLoggedIn && isRegisterRequest){
            if (!isRegisterRequest){
                httpResponse.sendRedirect(registerURI);
                return;
            }
            chain.doFilter(request, response);
        }

        // Redirect to login page if user is not logged in and is not requesting the login page
        if (!isLoggedIn && !isLoginRequest) {
            httpResponse.sendRedirect(loginURI);
        }

        // Redirect to the homepage if the user is logged in and is requesting the login page
        else if (isLoggedIn && (isLoginRequest || isRegisterRequest)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/HomePage");
        }
        // Continue with the request if none of the above conditions are met
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}

