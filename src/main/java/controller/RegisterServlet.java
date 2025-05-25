package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String validationError = validateInput(username, password, confirmPassword);
        if (validationError != null) {
            forwardWithError(req, resp, "Register.jsp", validationError);
            return;
        }

        UserBO userBO = new UserBO();
        
        try {
            if (userBO.isUsernameExists(username)) {
                forwardWithError(req, resp, "Register.jsp", "Tên đăng nhập đã tồn tại!");
                return;
            }
            
            String hashedPassword = userBO.hashPassword(password);
            User newUser = new User(username, hashedPassword);

            boolean success = userBO.createUser(newUser);
            
            if (success) {
                forwardWithSuccess(req, resp, "Login.jsp", "Đăng ký thành công! Vui lòng đăng nhập.");
            } else {
                forwardWithError(req, resp, "Register.jsp", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại!");
            }
            
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            
            forwardWithError(req, resp, "Register.jsp", "Lỗi hệ thống. Vui lòng thử lại sau!");
        }
    }
    
    private String validateInput(String username, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống!";
        }
        
        if (username.length() < 8 || username.length() > 50) {
            return "Tên đăng nhập phải ít nhất 8 ký tự!";
        }
        
        if (password == null || password.isEmpty()) {
            return "Mật khẩu không được để trống!";
        }
        
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự!";
        }
        
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp!";
        }
        
        return null; 
    }
    
    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, 
                                 String page, String errorMessage) throws ServletException, IOException {
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(page).forward(req, resp);
    }
    
    private void forwardWithSuccess(HttpServletRequest req, HttpServletResponse resp, 
                                   String page, String successMessage) throws ServletException, IOException {
        req.setAttribute("success", successMessage);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}