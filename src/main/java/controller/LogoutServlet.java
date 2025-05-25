package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
    }
}