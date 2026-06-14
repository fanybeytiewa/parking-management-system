package bg.tu_varna.sit.ps.project24621627;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:h2:./data/parkingdb";
    private static final String USER = "h2_user";
    private static final String PASSWORD = "1627";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

