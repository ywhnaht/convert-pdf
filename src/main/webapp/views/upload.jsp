<%@ page import="model.bo.FilesBO, model.bean.Files" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PDF to Word Converter</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/shared.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/upload.css">
</head>
<body class="has-header">
    <!-- Include Header -->
    <jsp:include page="../shared/header.jsp" />

    <div class="background">
        <div class="upload-container">
            <div class="upload-card">
                <!-- Header -->
                <div class="header">
                    <div class="icon">📄</div>
                    <h2>PDF TO WORD</h2>
                    <p>Chuyển đổi file PDF sang Word dễ dàng</p>
                </div>

                <!-- Success Message -->
                <% 
                    String message = (String) request.getAttribute("message");
                    if (message != null) { 
                %>
                    <div class="message success" id="uploadMessage"><%= message %></div>
                <% } %>

                <!-- Upload Form -->
                <form action="upload" method="post" enctype="multipart/form-data" id="uploadForm">
                    <div class="upload-area" id="uploadArea">
                        <div class="upload-icon">📎</div>
                        <p class="upload-text">Kéo thả file PDF vào đây hoặc</p>
                        <label for="fileInput" class="upload-btn">Chọn file</label>
                        <input type="file" name="file" id="fileInput" accept="application/pdf,.pdf" required hidden />
                        <p class="file-info">Tối đa 50MB • Chỉ file PDF</p>
                        <div id="filePreview" class="file-preview" style="display: none;">
                            <div class="file-item">
                                <span class="file-icon">📄</span>
                                <div class="file-details">
                                    <span class="file-name" id="fileName"></span>
                                    <span class="file-size" id="fileSize"></span>
                                </div>
                                <button type="button" class="remove-file" id="removeFile">×</button>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="submit-btn" id="submitBtn">
                        <span id="submitText">Chuyển đổi ngay</span>
                        <div class="loading-spinner" id="uploadSpinner"></div>
                    </button>
                </form>

                <!-- Status Container -->
                <div id="statusContainer" class="status-section" style="display: none;">
                    <div id="statusContent"></div>
                </div>

                <!-- Footer -->
                <!-- <div class="footer">
                    <a href="history.jsp" class="footer-link">📋 Lịch sử file</a>
                </div> -->
            </div>
        </div>
    </div>

    <!-- Include Footer -->
    <jsp:include page="../shared/footer.jsp" />

    <script src="<%= request.getContextPath() %>/assets/js/upload.js"></script>

    <% 
    Integer fileId = (Integer) request.getAttribute("fileId");
    if (fileId != null && fileId > 0) { 
    %>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                startStatusChecking(<%= fileId %>);
            });
        </script>
    <% } %>
</body>
</html>