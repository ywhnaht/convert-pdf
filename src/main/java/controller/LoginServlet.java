package controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.User;
import model.bo.UserBO;

	@WebServlet("/Login")
	public class LoginServlet extends HttpServlet{
		  private static final long serialVersionUID = 1;
		  
		  @Override
		  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			    req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
			}
		    
		    @Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			HttpSession session = req.getSession();

			var userBO = new UserBO();
			try {
				String hashedPass = userBO.hashPassword(password);
				User user = userBO.authenticate(username, hashedPass);

				if(user != null) {
					session.setAttribute("loggedin", true);
					session.setAttribute("user", user);
					resp.sendRedirect(req.getContextPath() + "/upload");
				} else {
					session.setAttribute("loggedin", false);
					session.setAttribute("error-massage", "Tên đăng nhập hoặc mật khẩu không đúng");
					req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
				}
			} catch(Exception e) {
				e.printStackTrace();
				session.setAttribute("error-massage", "Có lỗi xảy ra. Vui lòng thử lại!");
				req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
			}
		}
	}