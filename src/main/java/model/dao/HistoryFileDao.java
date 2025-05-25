package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Files;
import util.DBConnection;

public class HistoryFileDao {
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    public List<Files> getFiles(int user_id) throws SQLException {
        List<Files> files = new ArrayList<>();
        String sql = "SELECT * FROM files WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt =  conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Files file = new Files(rs.getInt("id"), rs.getString("original_filename"), rs.getString("stored_filename"), rs.getString("type"));
                    file.setInputUrl(rs.getString("input_url"));
                    file.setOutputUrl(rs.getString("output_url"));
                    file.setStatus(rs.getString("status"));
                    file.setCreatedAt(rs.getTimestamp("created_at"));
                    file.setUpdatedAt(rs.getTimestamp("updated_at"));
                    file.setUserId(rs.getInt("user_id"));
                    files.add(file);
                }   
            }
        }   
        return files;
    }

    public String DownloadFile(int file_id) throws SQLException {
        String sql = "SELECT * FROM files WHERE id = ? AND status = 'done'";
        try (Connection conn = getConnection();
             PreparedStatement stmt =  conn.prepareStatement(sql)) {
            stmt.setInt(1, file_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString("output_url");
        }
    }

    public void DeleteFile(int file_id) throws SQLException {
        String sql = "DELETE FROM files WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt =  conn.prepareStatement(sql)) {
            stmt.setInt(1, file_id);
            stmt.executeUpdate();
        }
    }
}
