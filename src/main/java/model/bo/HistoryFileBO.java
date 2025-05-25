package model.bo;

import java.sql.SQLException;
import java.util.List;

import model.bean.FileModel;
import model.dao.HistoryFileDao;

public class HistoryFileBO {
    private HistoryFileDao historyFileDao = new HistoryFileDao();

    public List<FileModel> getFiles(int user_id) throws SQLException {
        return historyFileDao.getFiles(user_id);
    }

    public String DownloadFile(int file_id) throws SQLException {
        return historyFileDao.DownloadFile(file_id);
    }

    public void DeleteFile(int file_id) throws SQLException {
        historyFileDao.DeleteFile(file_id);
    }
}