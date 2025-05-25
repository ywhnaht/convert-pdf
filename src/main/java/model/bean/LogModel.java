package model.bean;

public class LogModel {
    private int id;
    private String action;
    private String created_at;
    private int user_id;
    private int file_id;
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }   
    public int getFile_id() {
        return file_id;
    }
    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public LogModel(int id, String action, String created_at, int user_id, int file_id) {
        this.id = id;
        this.action = action;
        this.created_at = created_at;
        this.user_id = user_id;
        this.file_id = file_id;
    }
}