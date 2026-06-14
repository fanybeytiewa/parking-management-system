package bg.tu_varna.sit.ps.project24621627;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceDAO {

    public List<ParkingSpace> findAll() {
        List<ParkingSpace> spaces = new ArrayList<>();
        String sql = "SELECT number, floor, type, access, busy, notes FROM parking_spaces";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                int number = rs.getInt("number");
                int floor = rs.getInt("floor");

                SpaceType type = SpaceType.valueOf(rs.getString("type"));

                String access = rs.getString("access");
                boolean busy = rs.getBoolean("busy");
                String notes = rs.getString("notes");

                spaces.add(new ParkingSpace(number, floor, type, access, busy, notes));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Грешка при зареждане на паркоместата.", e);
        }
        return spaces;
    }

    public ParkingSpace save(ParkingSpace space) {
        String sql = "INSERT INTO parking_spaces(number, floor, type, access, busy, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, space.getNumber());
            ps.setInt(2, space.getFloor());

            ps.setString(3, space.getType().name());
            ps.setString(4, space.getAccess());
            ps.setBoolean(5, space.isBusy());
            ps.setString(6, space.getNotes());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    space.setNumber(rs.getInt(1));
                }
            }
            return space;
        } catch (SQLException e) {
            throw new RuntimeException("Грешка при запис на паркомясто.", e);
        }
    }

    public ParkingSpace update(ParkingSpace space, int oldNumber) {
        String sql = "UPDATE parking_spaces SET number = ?, floor = ?, type = ?, access = ?, busy = ?, notes = ? WHERE number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, space.getNumber());
            ps.setInt(2, space.getFloor());
            ps.setString(3, space.getType().name());
            ps.setString(4, space.getAccess());
            ps.setBoolean(5, space.isBusy());
            ps.setString(6, space.getNotes());
            ps.setInt(7, oldNumber);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Няма намерен разработчик за обновяване.");
            }

            return space;
        } catch (SQLException e) {
            throw new RuntimeException("Грешка при обновяване на паркомясто.", e);
        }
    }

    public void deleteByNumber(int number) {
        String sql = "DELETE FROM parking_spaces WHERE number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, number);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Грешка при изтриване на паркомясто.", e);
        }
    }

    public boolean existsByNumber(int number) {
        String sql = "SELECT COUNT(*) FROM parking_spaces WHERE number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, number);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Грешка при проверка на уникалност на номер.", e);
        }
        return false;
    }
}