package model.bean;

import java.util.Date;

public class FileModel {
    private int id;
    private String original_filename;
    private String stored_filename;
    private String status;
    private String result_path;
    private Date created_at;
    private Date updated_at;
    private int user_id;
    private String type;
    private String input_url;
    private String output_url;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getId() {
        return id;
    }
    public String getStored_filename() {
        return stored_filename;
    }
    public void setStored_filename(String stored_filename) {
        this.stored_filename = stored_filename;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOriginal_filename() {
        return original_filename;
    }
    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }   
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getResult_path() {
        return result_path;
    }
    public void setResult_path(String result_path) {
        this.result_path = result_path;
    }

    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
    public String getInput_url() {
        return input_url;
    }
    public void setInput_url(String input_url) {
        this.input_url = input_url;
    }
    public String getOutput_url() {
        return output_url;
    }
    public void setOutput_url(String output_url) {
        this.output_url = output_url;
    }
    
    public FileModel(int id, String original_filename, String stored_filename, String status, String result_path, Date created_at, Date updated_at , int user_id, String type, String input_url, String output_url   ) {
        this.id = id;
        this.original_filename = original_filename;
        this.stored_filename = stored_filename;
        this.status = status;   
        this.result_path = result_path;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_id = user_id;
        this.type = type;
        this.input_url = input_url;
        this.output_url = output_url;
    }
    
}