package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getParameter("file");
        String type = request.getParameter("type");
        
        if (filename == null || type == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String uploadPath = getServletContext().getRealPath("/uploads/" + type);
        File file = new File(uploadPath, filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (filename.endsWith(".pdf")) {
            response.setContentType("application/pdf");
        } else if (filename.endsWith(".docx")) {
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}