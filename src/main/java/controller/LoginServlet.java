package controller;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.User;
import model.bo.UserBO;

	@WebServlet("/Login")
	public class LoginServlet extends HttpServlet{
		  private static final long serialVersionUID = 1;
		  
		  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			    req.getRequestDispatcher("Login.jsp").forward(req, resp);
			}
		    
		    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		    	resp.setCharacterEncoding("UTF-8");
				req.setCharacterEncoding("UTF-8");
		    	String destination;
		    	
		    	String username = req.getParameter("username");
		    	String password = req.getParameter("password");
		    	HttpSession session = req.getSession();

		    	var userBO = new UserBO();
		    	try {
					String hashedPass = userBO.hashPassword(password);
					User user = userBO.authenticate(username, hashedPass);

		    		if(user!= null) {
		    			session.setAttribute("loggedin", true);
		    			session.setAttribute("user", user);
		    			destination = "/upload.jsp";
		    			RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
		    			rd.forward(req, resp);
		    		}
		    		else {
		    			destination = "/Login.jsp";
		    			session.setAttribute("loggedin", false);
		    			session.setAttribute("error-massage","Tên đăng nhập hoặc mật khẩu không đúng");
		    			RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
		    			rd.forward(req, resp);
		    		}
		    	} catch(Exception e) {
		    		 e.printStackTrace();
		    	}
		    	
		    }
		
	
	}