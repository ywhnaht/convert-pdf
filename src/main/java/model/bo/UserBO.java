package model.bo;

import java.sql.SQLException;

import model.bean.*;
import model.dao.*;
import util.hashPassword;

public class UserBO {

    UserDAO userDAO = new UserDAO();
    hashPassword hashPass = new hashPassword();
    
    public boolean isUsernameExists(String username) throws ClassNotFoundException, SQLException{
        return userDAO.isUsernameExists(username);
    }

    public boolean createUser(User user) throws  ClassNotFoundException, SQLException{
        return userDAO.createUser(user);
    }

    public User getUserByUsername(String username) throws ClassNotFoundException, SQLException{
        return userDAO.getUserByUsername(username);
    }

    public User authenticate(String username, String password) throws ClassNotFoundException, SQLException{
        return userDAO.authenticate(username, password);
    }

    public String hashPassword(String hash_password) throws ClassNotFoundException, SQLException{
        return hashPass.hashPasswordSHA256(hash_password);
    }

}
