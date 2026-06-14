package bg.tu_varna.sit.ps.project24621627;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS parking_spaces (
                number INT PRIMARY KEY NOT NULL,
                floor INT NOT NULL,
                type VARCHAR(50) NOT NULL,
                access VARCHAR(50) NOT NULL,
                busy BOOLEAN NOT NULL,
                notes VARCHAR(255)
            )
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
