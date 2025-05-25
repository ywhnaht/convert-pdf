package model.bean;

import java.util.List;

public class UserModel {
    private int id;
    private String username;
    private String password_hash;
    private String created_at;
    private List<FileModel> files;
    public List<FileModel> getFiles() {
        return files;
    }
    public void setFiles(List<FileModel> files) {
        this.files = files;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public UserModel(int id, String username, String password_hash, String created_at) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.created_at = created_at;
    }
    
}
