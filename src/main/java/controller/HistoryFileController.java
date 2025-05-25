package controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import model.bean.User;
import model.bean.Files;
import model.bo.HistoryFileBO;

@WebServlet("/historyFile")
public class HistoryFileController extends HttpServlet {
    private HistoryFileBO historyFileBO = new HistoryFileBO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        // Nếu request từ AJAX (yêu cầu JSON data)
        if ("data".equals(action)) {
            handleAjaxRequest(request, response);
        } 
        // Nếu request thông thường (hiển thị JSP page)
        else {
            handlePageRequest(request, response);
        }
    }
    
    /**
     * Xử lý request AJAX trả về JSON data
     */
    private void handleAjaxRequest(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            if(user == null){
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
            List<Files> files = historyFileBO.getFiles(user.getId());
            // Apply filters if provided
            files = applyFilters(files, request);
            Gson gson = new Gson();
            String jsonData = gson.toJson(files);
            System.out.println(files.size());
            System.out.println(jsonData);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonData);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Có lỗi xảy ra khi lấy dữ liệu\"}");
        }
    }
    
    /**
     * Xử lý request hiển thị JSP page
     */
    private void handlePageRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher("/views/historyFilePage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải trang");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    /**
     * Apply filters to file list
     */
    private List<Files> applyFilters(List<Files> files, HttpServletRequest request) {
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String type = request.getParameter("type");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        
        return files.stream()
            .filter(file -> {
                // Filter by search term
                if (search != null && !search.trim().isEmpty()) {
                    if (!file.getOriginalFilename().toLowerCase().contains(search.toLowerCase())) {
                        return false;
                    }
                }
                
                // Filter by status
                if (status != null && !status.trim().isEmpty()) {
                    if (!status.equals(file.getStatus())) {
                        return false;
                    }
                }
                
                // Filter by type
                if (type != null && !type.trim().isEmpty()) {
                    if (!type.equals(file.getType())) {
                        return false;
                    }
                }
                
                // Filter by date range
                if (dateFrom != null && !dateFrom.trim().isEmpty()) {
                    if (file.getCreatedAt().before(java.sql.Date.valueOf(dateFrom))) {
                        return false;
                    }
                }
                
                if (dateTo != null && !dateTo.trim().isEmpty()) {
                    if (file.getCreatedAt().after(java.sql.Date.valueOf(dateTo))) {
                        return false;
                    }
                }
                
                return true;
            })
            .collect(Collectors.toList());
    }
}