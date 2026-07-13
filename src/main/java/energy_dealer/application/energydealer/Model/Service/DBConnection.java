package energy_dealer.application.energydealer.Model.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Declares constant variables URL, USER, and PASSWORD to store the database connection details
    // such as the URL of the database, username, and password
    private final String URL = "jdbc:mysql://localhost:3306/drinks";
    private final String USER = "dealer";
    private final String PASSWORD = "my_password";

    // Defines a method named getConnection that returns a Connection object
    // representing a connection to the database
    // It declares to throw SQLException if an error occurs during the connection process
    public Connection getConnection() throws SQLException {

        // In the try block, attempts to load the MySQL database driver class com.mysql.cj.jdbc.Driver
        // using Class.forName() method
        // This step is necessary to register the JDBC driver with the DriverManager class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // If the driver class is successfully loaded, establishes a connection
            // to the database using the DriverManager.getConnection() method,
            // passing the database URL, username, and password
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {

            // If the database driver class is not found (ClassNotFoundException occurs),
            // it catches the exception and throws an SQLException with an error message indicating
            // the failure to load the database driver
            throw new SQLException("Unable to load database driver", e);
        }
    }
}
