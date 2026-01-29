package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.agriconnect.util.DBConnection;

public class DatabaseChecker {

    public static boolean checkCredentials(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            AlertUtil.showError("DB error: " + e.getMessage());
            return false;
        }
    }

    public static String getUserRole(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("role");
            return "unknown";
        } catch (Exception e) {
            AlertUtil.showError("DB error: " + e.getMessage());
            return "unknown";
        }
    }
}
