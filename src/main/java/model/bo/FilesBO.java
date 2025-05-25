package model.bo;

import model.bean.Files;
import model.dao.FilesDAO;

public class FilesBO {
    private FilesDAO filesDAO;

    public FilesBO() {
        filesDAO = new FilesDAO();
    }

    public Files getFileById(int file_id) {
        return filesDAO.getFileById(file_id);
    }

    public int insertFile(int user_id, String original_filename, String stored_filename, String input_url, String type) {
        return filesDAO.insertFile(user_id, original_filename, stored_filename, input_url, type);
    }

    public void updateFileStatus(int file_id, String status) {
        filesDAO.updateFileStatus(file_id, status);
    }

    public void updateFileStatusAndOutput(int file_id, String status, String result_path) {
        filesDAO.updateFileStatusAndOutput(file_id, status, result_path);
    }
    
}
