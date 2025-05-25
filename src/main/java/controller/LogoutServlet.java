package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Logout(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Logout(req, resp);
    }
    
    private void Logout(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        req.setAttribute("success", "Đăng xuất thành công!");
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }
}