package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.FilesBO;
import model.bean.Files;
import com.google.gson.Gson;

@WebServlet("/status")
public class StatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String fileIdStr = request.getParameter("fileId");
        if (fileIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing fileId\"}");
            return;
        }
        
        try {
            int fileId = Integer.parseInt(fileIdStr);
            FilesBO filesBO = new FilesBO();
            Files file = filesBO.getFileById(fileId);
            
            if (file == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"File not found\"}");
                return;
            }
            
            // Tạo response JSON
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.status = file.getStatus();
            statusResponse.originalFilename = file.getOriginalFilename();
            statusResponse.outputUrl = file.getOutputUrl();
            
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(statusResponse));
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid fileId\"}");
        }
    }
    
    private static class StatusResponse {
        String status;
        String originalFilename;
        String outputUrl;
    }
}