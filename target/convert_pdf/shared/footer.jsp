<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="app-footer">
    <div class="footer-container">
        <div class="footer-content">
            <div class="footer-section">
                <h4>PDF Converter</h4>
                <p>Công cụ chuyển đổi PDF sang Word nhanh chóng và tiện lợi</p>
            </div>
            
            <div class="footer-section">
                <h4>Liên kết</h4>
                <ul class="footer-links">
                    <li><a href="<%= request.getContextPath() %>/views/upload.jsp">Chuyển đổi file</a></li>
                    <li><a href="<%= request.getContextPath() %>/views/history.jsp">Lịch sử</a></li>
                    <li><a href="#" onclick="showHelp()">Hướng dẫn</a></li>
                </ul>
            </div>
            
            <div class="footer-section">
                <h4>Hỗ trợ</h4>
                <ul class="footer-links">
                    <li><a href="#" onclick="showContact()">Liên hệ</a></li>
                    <li><a href="#" onclick="showFAQ()">FAQ</a></li>
                    <li><a href="#" onclick="showPrivacy()">Chính sách</a></li>
                </ul>
            </div>
        </div>
        
        <div class="footer-bottom">
            <p>&copy; 2024 PDF Converter. Tất cả quyền được bảo lưu.</p>
            <div class="footer-tech">
                <span>Powered by Java & JSP</span>
            </div>
        </div>
    </div>
</footer>

<script>
    function showHelp() {
        alert('Hướng dẫn sử dụng:\n\n1. Chọn file PDF cần chuyển đổi\n2. Nhấn "Chuyển đổi ngay"\n3. Chờ quá trình xử lý hoàn tất\n4. Tải file Word về máy');
    }
    
    function showContact() {
        alert('Liên hệ hỗ trợ:\n\nEmail: support@pdfconverter.com\nHotline: 1900 123 456');
    }
    
    function showFAQ() {
        alert('Câu hỏi thường gặp:\n\n• File tối đa bao nhiêu MB? - 50MB\n• Hỗ trợ những định dạng nào? - Chỉ PDF\n• Thời gian xử lý? - 30 giây đến vài phút');
    }
    
    function showPrivacy() {
        alert('Chính sách bảo mật:\n\n• Files được xóa tự động sau 24h\n• Không lưu trữ dữ liệu cá nhân\n• Kết nối được mã hóa SSL');
    }
</script>