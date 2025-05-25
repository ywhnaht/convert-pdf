package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.bean.User;
import model.bo.FilesBO;
import util.ConvertJob;
import util.ConvertQueue;
import util.CloudStorageService;

@WebServlet("/upload")
@MultipartConfig(maxFileSize = 50 * 1024 * 1024) // 50MB max
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;
        FilesBO filesBO = new FilesBO();
        int fileId = -1;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");  

        if (user == null) {
            message = "Vui lòng đăng nhập để upload file";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/views/Login.jsp").forward(request, response);
            return;
        }  

        try {
            Part filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                throw new Exception("Vui lòng chọn file để upload");
            }
            
            String originalFilename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if (!originalFilename.toLowerCase().endsWith(".pdf")) {
                throw new Exception("Chỉ chấp nhận file PDF");
            }
            
            String storedFilename = originalFilename;

            // Tạo file tạm để upload lên cloud
            String tempDir = System.getProperty("java.io.tmpdir");
            File tempFile = new File(tempDir, storedFilename);
            filePart.write(tempFile.getAbsolutePath());

            // Upload lên cloud
            CloudStorageService cloudService = CloudStorageService.getInstance();
            String cloudUrl = cloudService.uploadFile(tempFile, "pdf-converter/input", storedFilename);
            
            // Xóa file tạm
            tempFile.delete();
           
            int userId = user.getId();
            fileId = filesBO.insertFile(userId, originalFilename, storedFilename, cloudUrl, "docx");

            if (fileId > 0) {
                ConvertQueue.getInstance().addJob(new ConvertJob(fileId, cloudUrl, storedFilename));
                message = "Upload thành công! File đang được chuyển đổi. Vui lòng chờ...";
            } else {
                message = "Upload thất bại. Vui lòng thử lại.";
            }
        } catch (Exception e) {
            message = "Có lỗi xảy ra: " + e.getMessage();
            e.printStackTrace();
        }
        
        request.setAttribute("message", message);
        request.setAttribute("fileId", fileId);
        request.getRequestDispatcher("/views/upload.jsp").forward(request, response);
    }
}