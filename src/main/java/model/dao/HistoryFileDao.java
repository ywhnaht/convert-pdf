package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.FileModel;
import util.DBConnection;

public class HistoryFileDao {
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
    
    public List<FileModel> getFiles(int user_id) throws SQLException {
        List<FileModel> files = new ArrayList<>();
        String sql = "SELECT * FROM files WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt =  conn.prepareStatement(sql)) {
            stmt.setInt(1, user_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FileModel file = new FileModel(rs.getInt("id"), rs.getString("original_filename"), rs.getString("stored_filename"), rs.getString("status"), rs.getString("result_path"),  rs.getDate("created_at"), rs.getDate("updated_at"), rs.getInt("user_id"), rs.getString("type"), rs.getString("input_url"), rs.getString("output_url"));
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
