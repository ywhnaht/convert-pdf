package model.bean;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String password_hash;
    private Timestamp created_at;

    public User() {
    }

    public User(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }

    public User(int id, String username, String password_hash, Timestamp created_at) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.created_at = created_at;
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
    
    public String getPasswordHash() {
        return password_hash;
    }
    
    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }
    
    public Timestamp getCreatedAt() {
        return created_at;
    }
    
    public void setCreatedAt(Timestamp create_at) {
        this.created_at = create_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + password_hash + '\'' +
                ", createdAt=" + created_at +
                '}';
    }
}
