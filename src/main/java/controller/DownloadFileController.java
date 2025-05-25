package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.bo.HistoryFileBO;
@WebServlet("/downloadFile")
public class DownloadFileController extends HttpServlet {
    private HistoryFileBO historyFileBO = new HistoryFileBO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int file_id = Integer.parseInt(request.getParameter("file_id"));
            String file_url = historyFileBO.DownloadFile(file_id);
            Gson gson = new Gson();
            String jsonData = gson.toJson(file_url);
            System.out.println(jsonData);
            response.setContentType("application/json");
            // response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonData);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Có lỗi xảy ra khi tải file\"}");
        }
    }
}