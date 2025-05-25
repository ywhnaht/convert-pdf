package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/Register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        
        String validationError = validateInput(username, password, confirmPassword);
        if (validationError != null) {
            forwardWithError(req, resp, "/views/Register.jsp", validationError);
            return;
        }

        UserBO userBO = new UserBO();
        
        try {
            if (userBO.isUsernameExists(username)) {
                forwardWithError(req, resp, "/views/Register.jsp", "Tên đăng nhập đã tồn tại!");
                return;
            }
            
            String hashedPassword = userBO.hashPassword(password);
            User newUser = new User(username, hashedPassword);

            boolean success = userBO.createUser(newUser);
            
            if (success) {
                // Sử dụng sendRedirect thay vì forward khi đăng ký thành công
                resp.sendRedirect(req.getContextPath() + "/views/Login.jsp");
            } else {
                forwardWithError(req, resp, "/views/Register.jsp", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại!");
            }
            
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            forwardWithError(req, resp, "/views/Register.jsp", "Lỗi hệ thống. Vui lòng thử lại sau!");
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