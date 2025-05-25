package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.bean.Files;
import util.DBConnection;

public class FilesDAO {
    public int insertFile(int userId, String originalFilename, String storedFilename, String inputUrl, String type) {
        String sql = "INSERT INTO files (user_id, original_filename, stored_filename, input_url, type, status, created_at) VALUES (?, ?, ?, ?, ?, 'pending', NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, originalFilename);
            stmt.setString(3, storedFilename);
            stmt.setString(4, inputUrl);
            stmt.setString(5, type);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean updateFileStatus(int fileId, String status) {
        String sql = "UPDATE files SET status = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, fileId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateFileStatusAndOutput(int fileId, String status, String outputUrl) {
        String sql = "UPDATE files SET status = ?, output_url = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setString(2, outputUrl);
            stmt.setInt(3, fileId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Files getFileById(int fileId) {
        String sql = "SELECT * FROM files WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, fileId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Files file = new Files();
                    file.setId(rs.getInt("id"));
                    file.setUserId(rs.getInt("user_id"));
                    file.setOriginalFilename(rs.getString("original_filename"));
                    file.setStoredFilename(rs.getString("stored_filename"));
                    file.setInputUrl(rs.getString("input_url"));
                    file.setOutputUrl(rs.getString("output_url"));
                    file.setStatus(rs.getString("status"));
                    file.setCreatedAt(rs.getTimestamp("created_at"));
                    file.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return file;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
