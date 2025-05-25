
package controller; 
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.HistoryFileBO;

@WebServlet("/deleteFile")
public class DeleteFileController extends HttpServlet {
    private HistoryFileBO historyFileBO = new HistoryFileBO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int file_id = Integer.parseInt(request.getParameter("file_id"));
            historyFileBO.DeleteFile(file_id);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Xóa file thành công\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Có lỗi xảy ra khi xóa file\"}");
        }
    }
}