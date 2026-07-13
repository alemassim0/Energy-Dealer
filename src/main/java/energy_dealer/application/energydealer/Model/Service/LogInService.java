package energy_dealer.application.energydealer.Model.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import energy_dealer.application.energydealer.Model.DAO.UtenteDAO;
import energy_dealer.application.energydealer.Model.DAOImpl.UtenteDAOImpl;
import energy_dealer.application.energydealer.Model.Entity.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LogInService {

    public void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieves the value of the "email" parameter from the HTTP request and assigns it to a String variable named email
        String email = request.getParameter("email");

        // Retrieves the value of the "password" parameter from the HTTP request and assigns it to a String variable named password
        String password = request.getParameter("password");

        // Calls a method isInputValid to check if the provided email and password are correct
        // The ! means "not", so it checks if the input is not valid
        if (!isInputValid(email, password)) {
            // If the input is invalid, it calls handleAuthenticationError to show an error message saying "Invalid email or password"
            handleAuthenticationError(request, response, "Invalid email or password");
            return;
        }

        // If the input is valid, it proceeds to the next step
        try {
            // Calls the authenticateUser method to check if the email and password match a user in the database
            authenticateUser(request, response, email, password);

            // If something goes wrong while checking the database (an SQL exception), it catches that error
        } catch (SQLException e) {
            // If an SQL error happens, it calls handleAuthenticationError to show an error message
            handleAuthenticationError(request, response, "An error occurred while accessing the database");
        }

    }

    private boolean isInputValid(String email, String password) {

        // Calls two methods (isValidEmail and isValidPassword) on the logInService object
        // It passes the email and password variables as arguments to these methods
        // The isValidEmail method checks if the email format is valid,
        // while the isValidPassword method checks if the password meets certain criteria
        // The && operator is used to perform a logical AND operation between the results of the two method calls
        // The method returns true if both the email and password are valid according
        // to the isValidEmail and isValidPassword methods, otherwise, it returns false
        return isValidEmail(email) && isValidPassword(password);
    }

    private void authenticateUser(HttpServletRequest request, HttpServletResponse response, String email, String password) throws ServletException, IOException, SQLException {

        // Creates an instance of the UtenteDAOImpl class, which implements the UtenteDAO interface
        // The UtenteDAO class provides methods for interacting with the database related to user authentication
        UtenteDAO utenteDAO = new UtenteDAOImpl();

        // Checks if the provided email is in the database
        // It takes the email variable as input
        // Condition: If the email does not exist
        if (!utenteDAO.isEmailExists(email)) {
            // Handles the error by showing an error message
            // This runs if the email is not found in the database
            handleAuthenticationError(request, response, "Invalid email or password");
            return;
        }

        Utente utente = utenteDAO.findUtenteByEmail(email);

        if (utente.getRuolo().equals("anonimo")) {
            handleAuthenticationError(request, response, "Il suo account risulta anonimo, registrare un account per favore.");
            return;
        }

        // Retrieves the user details (like email and password) from the database
        // It takes the email variable as input
        utente = utenteDAO.getUtenteEmailAndPasswordByEmail(email);

        // Compares the provided password with the stored password in the database
        // It takes the password variable and the stored password (utente.getPassword()) as inputs
        // Condition: If the password is incorrect
        if (!checkPassword(password, utente.getPassword())) {
            // Handles the error by showing an error message
            // This runs if the password is incorrect
            handleAuthenticationError(request, response, "Invalid email or password");
            return;
        }

        // Logs in the user
        // It takes the request and response objects, and the user object (utente) as inputs
        logInUser(request, response, utente);
    }

    private void logInUser(HttpServletRequest request, HttpServletResponse response, Utente utente) throws IOException {

        // Attempts to retrieve the current session associated with the request
        // The getSession(false) method retrieves the session if it exists, or returns null if there is no current session
        // Passing false as an argument ensures that a new session is not created if one does not already exist
        HttpSession session = request.getSession(false);

        // Checks if a session exists
        // If a session exists (session is not null), it invalidates (terminates) the current session to prevent session fixation attacks
        // session.invalidate() ends the session and releases any associated resources
        if (session != null) {
            session.invalidate();
        }

        // Retrieves all cookies from the HTTP request.
        Cookie[] cookies = request.getCookies();

        // If there are no cookies (the array is null), the function exits early since there are no cookies to process
        if (cookies == null) {
            // No cookies to process
            return;
        }

        // Loops through each cookie in the array.
        for (Cookie cookie : cookies) {

            // Inside the loop, the code checks if the name of the current cookie is "userEmail"
            if (!cookie.getName().equals("userEmail")) {
                // Skip non-matching cookies
                continue;
            }

            // If the cookie name matches "userEmail", the code sets its maximum age to 0, which effectively deletes the cookie
            cookie.setMaxAge(0);

            // Sets the path to "/", ensuring the cookie is deleted for the entire application
            cookie.setPath("/");

            // Adds the modified cookie back to the response, so the browser knows to delete it
            response.addCookie(cookie);
        }

        // Creates a new session for the user
        // The getSession(true) method creates a new session if one does not already exist,
        // or returns the current session if it exists
        // Passing true as an argument ensures that a new session is created if one does not already exist
        session = request.getSession(true);

        // Stores essential user information (in this case, the user's email) in the session
        // The setAttribute method associates a specified value with the specified name in the session
        // Here, the user's email address (utente.getEmail()) is stored with the name "userEmail"
        session.setAttribute("userEmail", utente.getEmail());

        // Stores essential user information (in this case, the user's role) in the session
        // The setAttribute method associates a specified value with the specified name in the session
        // Here, the user's role (utente.getRuolo()) is stored with the name "userEmail"
        session.setAttribute("userRole", utente.getRuolo());

        // Sets the maximum inactive interval for the session to -1,
        // indicating that the session should never expire due to inactivity
        // -1 effectively disables session timeout,
        // ensuring that the session remains active until the user logs out or clears cookies
        session.setMaxInactiveInterval(-1);

        // Set a cookie with the user's email
        // Cookie: A small piece of data stored on the user's computer by the web browser while browsing a website
        // Parameter 1 ("userEmail"): The name of the cookie
        // Parameter 2 (utente.getEmail()): The value of the cookie, which is the user's email in this case
        Cookie userEmailCookie = new Cookie("userEmail", utente.getEmail());

        // Set the cookie to expire in 1 day (24 hours)
        // This sets the maximum age of the cookie in seconds (60 seconds * 60 minutes * 24 hours = 86400 seconds)
        // After this time, the cookie will be deleted by the browser
        userEmailCookie.setMaxAge(60 * 60 * 24);

        // Make the cookie available to the entire application
        // This sets the path of the cookie to the root of the application,
        // making it accessible from all pages and endpoints in the application
        userEmailCookie.setPath("/");

        // Set HttpOnly flag to prevent access by JavaScript
        // This flag ensures that the cookie is not accessible via JavaScript,
        // enhancing security by preventing potential XSS (Cross-Site Scripting) attacks
        userEmailCookie.setHttpOnly(true);

        // Set Secure flag to ensure the cookie is only sent over HTTPS
        // This flag ensures that the cookie is only sent over secure HTTPS connections,
        // protecting it from being intercepted during transmission
        userEmailCookie.setSecure(true);

        // Set SameSite attribute to Lax (or Strict based on needs)
        // This attribute controls whether the cookie is sent with cross-site requests
        // "Lax" means the cookie is not sent with cross-site subrequests,
        // such as calls to load images or frames, but is sent when a user navigates to the URL from an external site (by following a link)
        userEmailCookie.setAttribute("SameSite", "Lax");

        // Add the cookie to the response
        // This adds the configured cookie to the HTTP response, so it will be stored by the user's browser
        response.addCookie(userEmailCookie);

        if ("ADMIN".equals(utente.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/admin/AdminPage");
        } else {
            response.sendRedirect(request.getContextPath() + "/HomePage");
        }
    }

    private void handleAuthenticationError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {

        // Sets an error message attribute in the request object
        // The setAttribute method associates the provided error message (errorMessage) with the name "errorMessage"
        // This allows the error message to be accessed or displayed in the destination JSP page
        request.setAttribute("errorMessage", errorMessage);

        // Forwards the request and response objects to a JSP page for further processing
        // The getRequestDispatcher method obtains a request dispatcher for the specified JSP page ("/View/logInPage.jsp")
        // The forward method then forwards the request and response objects to the specified JSP page
        // This allows the JSP page to handle the request and generate a response, typically to display the error message
        request.getRequestDispatcher("/View/logInPage.jsp").forward(request, response);
    }

    // Method to check if a given password matches a hashed password
    // Takes a plain text password and a hashed password as inputs and returns a boolean
    public boolean checkPassword(String password, String hashedPassword) {

        // BCrypt.checkpw method compares the plain text password with the hashed password
        // Returns true if they match, false otherwise
        return BCrypt.checkpw(password, hashedPassword);
    }

    // This method checks if the provided email is valid
    public boolean isValidEmail(String email) {

        // This method checks if the provided email is valid
        // A regular expression (regex) for validating email addresses.
        // It checks if the email contains alphanumeric characters, '+', '_', '.', or '-' before the '@' symbol,
        // followed by any character after the '@' symbol.
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Creates a Pattern object by compiling a regular expression stored in the EMAIL_REGEX variable
        // This Pattern object can then be used for matching email addresses against the defined pattern in EMAIL_REGEX
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        // It checks if the email is not null and if it matches the pattern
        // If both conditions are true, it returns true; otherwise, it returns false
        return email != null && pattern.matcher(email).matches();
    }

    // This method checks if the provided password is valid
    public boolean isValidPassword(String password) {

        // It compiles the PASSWORD_REGEX into a Pattern object
        // A regular expression (regex) for validating passwords
        // It checks if the password contains at least one digit (?=.*[0-9]),
        // at least one lower case letter (?=.*[a-z]),
        // at least one upper case letter (?=.*[A-Z]),
        // and is at least 8 characters long (.{8,})
        String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

        // creates a Pattern object by compiling a regular expression stored in the PASSWORD_REGEX variable
        // This Pattern object can then be used for matching passwords against the defined pattern in PASSWORD_REGEX
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        // It checks if the password is not null and if it matches the pattern
        // If both conditions are true, it returns true; otherwise, it returns false
        return password != null && pattern.matcher(password).matches();
    }
}
