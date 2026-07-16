package energy_dealer.application.energydealer.Model.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogOutService {

    public void logOutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Retrieves the current session associated with the request
        // The false parameter ensures that a new session is not created if one does not exist
        HttpSession session = request.getSession(false);
        // Checks if a session exists
        if (session != null) {
            // Invalidates (closes) the current session, effectively logging out the user by removing their session data
            session.invalidate();
        }

        // Retrieves all cookies sent by the client in the request
        Cookie[] cookies = request.getCookies();
        // Checks if no cookies are present
        if (cookies == null) {
            // Redirects the user to the login page
            response.sendRedirect(request.getContextPath() + "/user/LogInPage");
            // Exits the method early, ensuring no further processing occurs
            return;
        }

        // Iterates through each cookie in the cookies array received from the client
        for (Cookie cookie : cookies) {
            // Checks if the current cookie's name is not "userEmail"
            if (!"userEmail".equals(cookie.getName())) {
                // Skips to the next iteration of the loop if the condition is true
                continue;
            }

            // Sets the maximum age of the "userEmail" cookie to 0, effectively expiring (deleting) it
            cookie.setMaxAge(0);

            // Sets the path of the cookie to "/" (root path) to ensure it applies to the entire application
            cookie.setPath("/");

            // Adds the modified cookie to the response, instructing the client's browser to delete the "userEmail" cookie
            response.addCookie(cookie);
        }

        // Redirects the user to the login page after logging out
        response.sendRedirect(request.getContextPath() + "/user/LogInPage");
    }

    public boolean isLoggedIn(HttpServletRequest request) {
        // Gets the current session without creating a new one if it doesn't exist
        HttpSession currentSession = request.getSession(false);

        // Initializes a boolean flag to track if the user is logged in
        boolean isLoggedIn = false;

        // Checks if the session exists (currentSession != null) and if it contains the attribute "userEmail"
        // If true, sets isLoggedIn to true
        if (currentSession != null && currentSession.getAttribute("userEmail") != null) {
            isLoggedIn = true;
        }

        // Retrieves all cookies from the user's request
        Cookie[] cookies = request.getCookies();

        //  Checks if isLoggedIn is still false (user is not logged in) and if there are cookies (cookies != null)
        if (!isLoggedIn && cookies != null) {
            // Loop through each cookie
            for (Cookie cookie : cookies) {
                // Checks if a cookie named "userEmail" exists
                if ("userEmail".equals(cookie.getName())) {
                    // If found, sets isLoggedIn to true (isLoggedIn = true;) and breaks out of the loop
                    isLoggedIn = true;
                    break;
                }
            }
        }
        return isLoggedIn;
    }
}
