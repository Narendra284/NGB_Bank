package ngb_bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ngb";
    private static final String USER = "root"; // change to your MySQL username
    private static final String PASSWORD = "9881"; // change to your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
