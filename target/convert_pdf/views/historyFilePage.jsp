<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/shared.css">
                <title>Lịch Sử Xử Lý File</title>
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }

                    body {
                        font-family: 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif;
                        background: linear-gradient(135deg, #a8e6cf, #7fcdcd);
                        min-height: 100vh;
                        padding: 20px;
                        -webkit-font-smoothing: antialiased;
                        -moz-osx-font-smoothing: grayscale;
                    }

                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 15px;
                        box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
                        overflow: hidden;
                        backdrop-filter: blur(5px);
                        border: 1px solid rgba(255, 255, 255, 0.3);
                    }

                    .header {
                        background: linear-gradient(135deg, #1a5276, #154360);
                        color: white;
                        padding: 30px;
                        text-align: center;
                        position: relative;
                        overflow: hidden;
                    }

                    .header::before {
                        content: '';
                        position: absolute;
                        top: -50%;
                        left: -50%;
                        width: 200%;
                        height: 200%;
                        background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 0%, transparent 70%);
                        animation: rotate 20s linear infinite;
                    }

                    @keyframes rotate {
                        0% {
                            transform: rotate(0deg);
                        }

                        100% {
                            transform: rotate(360deg);
                        }
                    }

                    .header h1 {
                        font-size: 2.5rem;
                        margin-bottom: 10px;
                        position: relative;
                        z-index: 1;
                    }

                    .header p {
                        font-size: 1.1rem;
                        opacity: 0.9;
                        position: relative;
                        z-index: 1;
                    }

                    .main-content {
                        padding: 30px;
                    }

                    .stats-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                        gap: 20px;
                        margin-bottom: 30px;
                    }

                    .stat-card {
                        background: rgba(255, 255, 255, 0.85);
                        border-radius: 12px;
                        padding: 25px;
                        text-align: center;
                        transition: all 0.3s ease;
                        border: 1px solid rgba(26, 82, 118, 0.1);
                        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                        backdrop-filter: blur(5px);
                    }

                    .stat-card:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 15px 30px rgba(26, 82, 118, 0.1);
                        background: rgba(255, 255, 255, 0.95);
                    }

                    .stat-number {
                        font-size: 2.5rem;
                        font-weight: bold;
                        color: #1a5276;
                        margin-bottom: 10px;
                    }

                    .stat-label {
                        color: #5d6d7e;
                        font-size: 1rem;
                        font-weight: 500;
                    }

                    .filters {
                        background: rgba(248, 249, 249, 0.85);
                        padding: 20px;
                        border-radius: 12px;
                        margin-bottom: 30px;
                        display: flex;
                        gap: 15px;
                        flex-wrap: wrap;
                        align-items: center;
                        border: 1px solid rgba(26, 82, 118, 0.1);
                        backdrop-filter: blur(5px);
                    }

                    .filter-group {
                        display: flex;
                        flex-direction: column;
                        gap: 5px;
                    }

                    .filter-group label {
                        font-size: 0.9rem;
                        font-weight: 600;
                        color: #1a5276;
                    }

                    .filter-input {
                        padding: 10px 15px;
                        border: 1px solid rgba(26, 82, 118, 0.2);
                        border-radius: 8px;
                        font-size: 0.9rem;
                        transition: all 0.3s ease;
                        background: rgba(255, 255, 255, 0.9);
                    }

                    .filter-input:focus {
                        outline: none;
                        border-color: #1a5276;
                        box-shadow: 0 0 0 3px rgba(26, 82, 118, 0.1);
                    }

                    .btn {
                        background: linear-gradient(135deg, #1a5276, #154360);
                        color: white;
                        border: none;
                        padding: 12px 25px;
                        border-radius: 8px;
                        cursor: pointer;
                        font-weight: 600;
                        transition: all 0.3s ease;
                        display: inline-flex;
                        align-items: center;
                        gap: 8px;
                    }

                    .btn:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 10px 20px rgba(26, 82, 118, 0.2);
                        background: linear-gradient(135deg, #154360, #1a5276);
                    }

                    .file-list {
                        background: rgba(255, 255, 255, 0.9);
                        border-radius: 12px;
                        overflow: hidden;
                        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                        border: 1px solid rgba(26, 82, 118, 0.1);
                        backdrop-filter: blur(5px);
                    }

                    .file-item {
                        display: grid;
                        grid-template-columns: auto 1fr auto auto auto auto;
                        gap: 20px;
                        padding: 20px;
                        border-bottom: 1px solid rgba(242, 244, 244, 0.7);
                        align-items: center;
                        transition: all 0.3s ease;
                    }

                    .file-item:hover {
                        background: rgba(248, 249, 249, 0.7);
                    }

                    .file-item:last-child {
                        border-bottom: none;
                    }

                    .file-icon {
                        width: 40px;
                        height: 40px;
                        border-radius: 8px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        font-weight: bold;
                        color: white;
                        font-size: 0.8rem;
                    }

                    .file-icon.pdf {
                        background: #e74c3c;
                    }

                    .file-icon.docx {
                        background: #3498db;
                    }

                    .file-icon.txt {
                        background: #2ecc71;
                    }

                    .file-icon.other {
                        background: #95a5a6;
                    }

                    .file-info h3 {
                        font-size: 1.1rem;
                        color: #1a5276;
                        margin-bottom: 5px;
                    }

                    .file-info p {
                        color: #5d6d7e;
                        font-size: 0.9rem;
                    }

                    .status-badge {
                        padding: 6px 12px;
                        border-radius: 20px;
                        font-size: 0.8rem;
                        font-weight: 600;
                        text-transform: uppercase;
                    }

                    .status-done {
                        background: #e8f8f5;
                        color: #27ae60;
                    }

                    .status-processing {
                        background: #fef9e7;
                        color: #f39c12;
                    }

                    .status-error {
                        background: #fdedec;
                        color: #e74c3c;
                    }

                    .status-pending {
                        background: #f4f6f6;
                        color: #7f8c8d;
                    }

                    .file-date {
                        color: #5d6d7e;
                        font-size: 0.9rem;
                    }

                    .file-size {
                        color: #5d6d7e;
                        font-size: 0.9rem;
                        font-weight: 500;
                    }

                    .action-buttons {
                        display: flex;
                        gap: 10px;
                    }

                    .btn-small {
                        padding: 8px 15px;
                        font-size: 0.8rem;
                        border-radius: 6px;
                    }

                    .btn-download {
                        background: linear-gradient(135deg, #27ae60, #2ecc71);
                    }

                    .btn-download:hover {
                        background: linear-gradient(135deg, #219653, #27ae60);
                    }

                    .btn-delete {
                        background: linear-gradient(135deg, #e74c3c, #c0392b);
                    }

                    .btn-delete:hover {
                        background: linear-gradient(135deg, #c0392b, #e74c3c);
                    }

                    .empty-state {
                        text-align: center;
                        padding: 60px 20px;
                        color: #5d6d7e;
                    }

                    .empty-state svg {
                        width: 80px;
                        height: 80px;
                        margin-bottom: 20px;
                        opacity: 0.5;
                        color: #bdc3c7;
                    }

                    .loading-indicator {
                        display: none;
                        text-align: center;
                        padding: 20px;
                    }

                    .loading-spinner {
                        border: 4px solid rgba(0, 0, 0, 0.1);
                        border-radius: 50%;
                        border-top: 4px solid #1a5276;
                        width: 40px;
                        height: 40px;
                        animation: spin 1s linear infinite;
                        margin: 0 auto;
                    }

                    @keyframes spin {
                        0% {
                            transform: rotate(0deg);
                        }

                        100% {
                            transform: rotate(360deg);
                        }
                    }

                    .error-message {
                        color: #c0392b;
                        background: #fdedec;
                        padding: 15px;
                        border-radius: 8px;
                        margin-bottom: 20px;
                        text-align: center;
                        border: 1px solid #f5b7b1;
                    }

                    /* Responsive styles */
                    @media (max-width: 768px) {
                        body {
                            padding: 10px;
                        }

                        .container {
                            border-radius: 12px;
                        }

                        .header {
                            padding: 20px;
                        }

                        .header h1 {
                            font-size: 2rem;
                        }

                        .header p {
                            font-size: 1rem;
                        }

                        .main-content {
                            padding: 20px;
                        }

                        .stats-grid {
                            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                            gap: 15px;
                        }

                        .stat-card {
                            padding: 20px;
                        }

                        .stat-number {
                            font-size: 2rem;
                        }

                        .filters {
                            flex-direction: column;
                            align-items: stretch;
                            gap: 15px;
                        }

                        .filter-group {
                            width: 100%;
                        }

                        .filter-input {
                            width: 100%;
                        }

                        .file-item {
                            grid-template-columns: auto 1fr;
                            gap: 15px;
                            padding: 15px;
                        }

                        .file-info {
                            grid-column: 1 / -1;
                        }

                        .status-badge,
                        .file-date,
                        .file-size {
                            grid-column: 1 / -1;
                            justify-self: start;
                            margin-top: 10px;
                        }

                        .action-buttons {
                            grid-column: 1 / -1;
                            justify-content: flex-start;
                            margin-top: 15px;
                        }

                        .btn-small {
                            padding: 10px 15px;
                            font-size: 0.9rem;
                        }
                    }

                    /* Scrollbar styling */
                    ::-webkit-scrollbar {
                        width: 8px;
                    }

                    ::-webkit-scrollbar-track {
                        background: rgba(255, 255, 255, 0.3);
                    }

                    ::-webkit-scrollbar-thumb {
                        background: #1a5276;
                        border-radius: 4px;
                    }

                    ::-webkit-scrollbar-thumb:hover {
                        background: #154360;
                    }
                </style>

            </head>

            <body class="has-header">
                <jsp:include page="../shared/header.jsp" />
                <div class="container">
                    <div class="header">
                        <h1>📁 Lịch Sử Xử Lý File</h1>
                        <p>Quản lý và theo dõi tất cả các file đã được xử lý</p>
                    </div>

                    <div class="main-content">
                        <!-- Thống kê -->
                        <div class="stats-grid" id="statsContainer">
                            <!-- Nội dung sẽ được cập nhật bằng JavaScript -->
                        </div>

                        <!-- Bộ lọc -->
                        <form id="filterForm">
                            <div class="filters">
                                <div class="filter-group">
                                    <label for="searchInput">Tìm kiếm</label>
                                    <input type="text" class="filter-input" id="searchInput" name="search"
                                        placeholder="Nhập tên file..." value="">
                                </div>
                                <div class="filter-group">
                                    <label for="statusFilter">Trạng thái</label>
                                    <select class="filter-input" id="statusFilter" name="status">
                                        <option value="">Tất cả</option>
                                        <option value="done" ${param.status eq 'done' ? 'selected' : '' }>Hoàn thành
                                        </option>
                                        <option value="processing" ${param.status eq 'processing' ? 'selected' : '' }>
                                            Đang
                                            xử lý</option>
                                        <option value="error" ${param.status eq 'error' ? 'selected' : '' }>Thất bại
                                        </option>
                                        <option value="pending" ${param.status eq 'pending' ? 'selected' : '' }>Chưa
                                            xử lý
                                        </option>
                                    </select>
                                </div>
                                <div class="filter-group">
                                    <label for="typeFilter">Loại file</label>
                                    <select class="filter-input" id="typeFilter" name="type">
                                        <option value="">Tất cả</option>
                                        <option value="pdf" ${param.type eq 'pdf' ? 'selected' : '' }>PDF</option>
                                        <option value="doc" ${param.type eq 'doc' ? 'selected' : '' }>DOC/DOCX
                                        </option>
                                        <option value="txt" ${param.type eq 'txt' ? 'selected' : '' }>TXT</option>
                                    </select>
                                </div>
                                <div class="filter-group">
                                    <label for="dateFromFilter">Từ ngày</label>
                                    <input type="date" class="filter-input" id="dateFromFilter" name="dateFrom"
                                        value="">
                                </div>
                                <div class="filter-group">
                                    <label for="dateToFilter">Đến ngày</label>
                                    <input type="date" class="filter-input" id="dateToFilter" name="dateTo" value="">
                                </div>
                                <button type="button" class="btn" id="refreshBtn">
                                    🔄 Làm mới
                                </button>
                            </div>
                        </form>

                        <!-- Thông báo lỗi -->
                        <div id="errorContainer" class="error-message" style="display: none;"></div>

                        <!-- Loading indicator -->
                        <div id="loadingIndicator" class="loading-indicator">
                            <div class="loading-spinner"></div>
                            <p>Đang tải dữ liệu...</p>
                        </div>

                        <!-- Danh sách file -->
                        <div class="file-list" id="fileListContainer">
                            <!-- Nội dung sẽ được cập nhật bằng JavaScript -->
                        </div>
                    </div>
                </div>
                <jsp:include page="../shared/footer.jsp" />

                <script>
                    // Hàm định dạng ngày tháng
                    function formatDate(dateString) {
                        if (!dateString) return 'Chưa có ngày';
                        const date = new Date(dateString);
                        return date.toLocaleDateString('vi-VN');
                    }

                    // Hàm hiển thị loading
                    function showLoading(show) {
                        document.getElementById('loadingIndicator').style.display = show ? 'block' : 'none';
                    }

                    // Hàm hiển thị lỗi
                    function showError(message) {
                        const errorContainer = document.getElementById('errorContainer');
                        if (message) {
                            errorContainer.textContent = message;
                            errorContainer.style.display = 'block';
                        } else {
                            errorContainer.style.display = 'none';
                        }
                    }

                    // Hàm tải dữ liệu từ server
                    async function loadData() {
                        showLoading(true);
                        showError(null);

                        try {
                            const formData = new FormData(document.getElementById('filterForm'));
                            const params = new URLSearchParams(formData);

                            const response = await fetch('historyFile?action=data&' + params.toString(), {
                                method: 'GET',
                                headers: {
                                    'Accept': 'application/json'
                                }
                            });

                            if (!response.ok) {
                                throw new Error(`HTTP error! status: ${response.status}`);
                            }

                            const data = await response.json();
                            console.log(data);
                            updateUI(data);
                        } catch (error) {
                            console.error('Error loading data:', error);
                            showError('Có lỗi xảy ra khi tải dữ liệu: ' + error.message);
                            document.getElementById('fileListContainer').innerHTML = `
                    <div class="empty-state">
                        <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1"
                                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z">
                            </path>
                        </svg>
                        <h3>Có lỗi xảy ra khi tải dữ liệu</h3>
                        <p>Vui lòng thử lại sau</p>
                    </div>
                `;
                        } finally {
                            showLoading(false);
                        }
                    }

                    // Hàm cập nhật giao diện với dữ liệu mới
                    function updateUI(files) {
                        // Cập nhật thống kê
                        updateStatistics(files);

                        // Cập nhật danh sách file
                        updateFileList(files);
                    }

                    // Hàm cập nhật phần thống kê
                    // Hàm mã hóa HTML để tránh XSS
                    function escapeHtml(unsafe) {
                        if (!unsafe) return '';
                        return String(unsafe)
                            .replace(/&/g, "&amp;")
                            .replace(/</g, "&lt;")
                            .replace(/>/g, "&gt;")
                            .replace(/"/g, "&quot;")
                            .replace(/'/g, "&#039;");
                    }

                    // Hàm định dạng ngày
                    function formatDate(dateString) {
                        if (!dateString) return 'N/A';
                        try {
                            const date = new Date(dateString);
                            return isNaN(date) ? 'Invalid Date' : date.toLocaleDateString('vi-VN', {
                                year: 'numeric',
                                month: '2-digit',
                                day: '2-digit'
                            });
                        } catch (error) {
                            console.error('Error formatting date:', dateString, error);
                            return 'Invalid Date';
                        }
                    }

                    // Hàm cập nhật thống kê
                    function updateStatistics(files) {
                        if (!files || !Array.isArray(files)) {
                            document.getElementById('statsContainer').innerHTML = `
            <div class="stat-card">
                <div class="stat-number">0</div>
                <div class="stat-label">Tổng số file</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">0</div>
                <div class="stat-label">Đã hoàn thành</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">0</div>
                <div class="stat-label">Đang xử lý</div>
            </div>
         
        `;
                            return;
                        }

                        const totalFiles = files.length;
                        const completedFiles = files.filter(f => f.status === 'done').length;
                        const processingFiles = files.filter(f => f.status === 'processing').length;
                        const failedFiles = files.filter(f => f.status === 'error').length;
                        const totalSize = files.reduce((sum, f) => sum + (f.word_count || 0) * 0.000001, 0);

                        document.getElementById('statsContainer').innerHTML = `
        <div class="stat-card">
            <div class="stat-number">${totalFiles}</div>
            <div class="stat-label">Tổng số file</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${completedFiles}</div>
            <div class="stat-label">Đã hoàn thành</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${processingFiles}</div>
            <div class="stat-label">Đang xử lý</div>
        </div>
        
    `;
                    }

                    // Hàm cập nhật danh sách file
                    function updateFileList(files) {
                        const fileListContainer = document.getElementById('fileListContainer');
                        if (!fileListContainer) {
                            console.error('fileListContainer not found');
                            return;
                        }

                        if (!files || !Array.isArray(files) || files.length === 0) {
                            fileListContainer.innerHTML = `
            <div class="empty-state">
                <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1"
                        d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z">
                    </path>
                </svg>
                <h3>Không tìm thấy file nào</h3>
                <p>Thử thay đổi bộ lọc để xem thêm kết quả</p>
            </div>
        `;
                            return;
                        }

                        let fileListHTML = '';
                        files.forEach(file => {
                            try {
                                if (!file || !file.id) {
                                    throw new Error('Invalid file data');
                                }

                                const createdAt = formatDate(file.created_at);
                                const updatedAt = formatDate(file.updated_at || null); // Xử lý processed_at có thể null
                                const fileSize = file.word_count ? (file.word_count * 0.000001).toFixed(1) : '0.0';
                                const fileType = escapeHtml(file.type || 'other');
                                const fileName = escapeHtml(file.original_filename || 'Không có tên file');

                                let statusText, statusClass;
                                switch (file.status) {
                                    case 'done':
                                        statusText = 'Hoàn thành';
                                        statusClass = 'done';
                                        break;
                                    case 'processing':
                                        statusText = 'Đang xử lý';
                                        statusClass = 'processing';
                                        break;
                                    case 'error':
                                        statusText = 'Thất bại';
                                        statusClass = 'error';
                                        break;
                                    case 'pending':
                                        statusText = 'Chưa xử lý';
                                        statusClass = 'pending';
                                        break;
                                    default:
                                        statusText = escapeHtml(file.status || 'Không xác định');
                                        statusClass = 'pending';
                                }

                                const downloadButton = file.status === 'done'
                                    ? `<button class="btn btn-small btn-download" onclick="downloadFile('${escapeHtml(String(file.id))}')">⬇️ Tải về</button>`
                                    : '';

                                fileListHTML += `
                <div class="file-item">
                    <div class="file-icon ${fileType}">
                        ${fileType.toUpperCase()}
                    </div>
                    <div class="file-info">
                        <h3>${fileName}</h3>
                        <p>Được tải lên: ${createdAt}</p>
                    </div>
                    <div class="status-badge status-${statusClass}">
                        ${statusText}
                    </div>
                    <div class="file-date">
                        ${updatedAt}
                    </div>
                   
                    <div class="action-buttons">
                        ${downloadButton}
                        <button class="btn btn-small btn-delete" onclick="deleteFile('${escapeHtml(String(file.id))}')">
                            🗑️ Xóa
                        </button>
                    </div>
                </div>
            `;
                            } catch (error) {
                                console.error('Error processing file:', file, error);
                                fileListHTML += `
                <div class="error-message">
                    <h4>Lỗi khi hiển thị file</h4>
                    <p>${escapeHtml(error.message)}</p>
                </div>
            `;
                            }
                        });

                        fileListContainer.innerHTML = fileListHTML;
                    }
                    // Hàm tải file về
                    async function downloadFile(fileId) {
                        try {
                            showLoading(true);
                            const response = await fetch('downloadFile?file_id=' + fileId, {
                                method: 'GET'
                            });
                            const data = await response.json();
                            console.log(data);
                            if (response.ok) {
                                window.location.href = data;
                            } else {
                                throw new Error('Không thể tải file');
                            }
                        } catch (error) {
                            console.error('Error downloading file:', error);
                            showError('Có lỗi xảy ra khi tải file: ' + error.message);
                        } finally {
                            showLoading(false);
                        }
                    }

                    // Hàm xóa file
                    async function deleteFile(fileId) {
                        if (!confirm('Bạn có chắc chắn muốn xóa file này?')) return;

                        try {
                            showLoading(true);
                            const response = await fetch('deleteFile?file_id=' + fileId, {
                                method: 'POST'
                            });

                            if (response.ok) {
                                loadData(); // Tải lại dữ liệu sau khi xóa
                            } else {
                                throw new Error('Không thể xóa file');
                            }
                        } catch (error) {
                            console.error('Error deleting file:', error);
                            showError('Có lỗi xảy ra khi xóa file: ' + error.message);
                        } finally {
                            showLoading(false);
                        }
                    }

                    // Debounce function
                    function debounce(func, wait) {
                        let timeout;
                        return function () {
                            const context = this, args = arguments;
                            clearTimeout(timeout);
                            timeout = setTimeout(() => func.apply(context, args), wait);
                        };
                    }

                    // Sự kiện khi trang được tải
                    document.addEventListener('DOMContentLoaded', function () {
                        // Tải dữ liệu ban đầu
                        loadData();

                        // Thêm sự kiện cho nút làm mới
                        document.getElementById('refreshBtn').addEventListener('click', loadData);

                        // Thêm sự kiện cho các filter (với debounce)
                        const debouncedLoadData = debounce(loadData, 500);
                        document.getElementById('searchInput').addEventListener('input', debouncedLoadData);
                        document.getElementById('statusFilter').addEventListener('change', loadData);
                        document.getElementById('typeFilter').addEventListener('change', loadData);
                        document.getElementById('dateFromFilter').addEventListener('change', loadData);
                        document.getElementById('dateToFilter').addEventListener('change', loadData);
                    });

                    // Smooth scrolling
                    document.documentElement.style.scrollBehavior = 'smooth';
                </script>
            </body>

            </html>