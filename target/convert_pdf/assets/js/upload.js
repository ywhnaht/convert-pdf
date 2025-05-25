// Global variables
let statusCheckInterval;
let currentFileId = null;

// DOM Elements
const uploadForm = document.getElementById('uploadForm');
const fileInput = document.getElementById('fileInput');
const uploadArea = document.getElementById('uploadArea');
const filePreview = document.getElementById('filePreview');
const submitBtn = document.getElementById('submitBtn');
const submitText = document.getElementById('submitText');
const uploadSpinner = document.getElementById('uploadSpinner');
const statusContainer = document.getElementById('statusContainer');
const statusContent = document.getElementById('statusContent');

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeEventListeners();
});

function initializeEventListeners() {
    // Form submission
    uploadForm.addEventListener('submit', handleFormSubmit);
    
    // File input change
    fileInput.addEventListener('change', handleFileSelect);
    
    // Drag and drop
    uploadArea.addEventListener('dragover', handleDragOver);
    uploadArea.addEventListener('dragleave', handleDragLeave);
    uploadArea.addEventListener('drop', handleDrop);
    
    // Remove file
    document.getElementById('removeFile').addEventListener('click', removeSelectedFile);
    
    // Cleanup on page unload
    window.addEventListener('beforeunload', cleanup);
}

// Form submission handler
function handleFormSubmit(e) {
    const file = fileInput.files[0];
    
    if (!validateFile(file)) {
        e.preventDefault();
        return;
    }
    
    showUploadLoading(true);
}

// File validation
function validateFile(file) {
    if (!file) {
        showMessage('Vui lòng chọn file PDF để upload', 'error');
        return false;
    }
    
    if (file.size > 50 * 1024 * 1024) {
        showMessage('File quá lớn! Vui lòng chọn file nhỏ hơn 50MB', 'error');
        return false;
    }
    
    if (!file.name.toLowerCase().endsWith('.pdf')) {
        showMessage('Vui lòng chọn file PDF', 'error');
        return false;
    }
    
    return true;
}

// File selection handler
function handleFileSelect(e) {
    const file = e.target.files[0];
    if (file) {
        displayFilePreview(file);
    }
}

// Display file preview
function displayFilePreview(file) {
    const fileName = document.getElementById('fileName');
    const fileSize = document.getElementById('fileSize');
    
    fileName.textContent = file.name;
    fileSize.textContent = formatFileSize(file.size);
    
    filePreview.style.display = 'block';
    uploadArea.style.padding = '20px';
}

// Remove selected file
function removeSelectedFile() {
    fileInput.value = '';
    filePreview.style.display = 'none';
    uploadArea.style.padding = '40px 20px';
}

// Format file size
function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

// Drag and drop handlers
function handleDragOver(e) {
    e.preventDefault();
    uploadArea.classList.add('dragover');
}

function handleDragLeave(e) {
    e.preventDefault();
    uploadArea.classList.remove('dragover');
}

function handleDrop(e) {
    e.preventDefault();
    uploadArea.classList.remove('dragover');
    
    const files = e.dataTransfer.files;
    if (files.length > 0) {
        const file = files[0];
        if (validateFile(file)) {
            fileInput.files = files;
            displayFilePreview(file);
        }
    }
}

// Show upload loading state
function showUploadLoading(show) {
    if (show) {
        submitBtn.disabled = true;
        submitText.textContent = 'Đang upload...';
        uploadSpinner.style.display = 'inline-block';
    } else {
        submitBtn.disabled = false;
        submitText.textContent = 'Chuyển đổi ngay';
        uploadSpinner.style.display = 'none';
    }
}

// Start status checking
function startStatusChecking(fileId) {
    currentFileId = fileId;
    statusContainer.style.display = 'block';
    statusContent.innerHTML = createStatusHTML('pending', 'Đang chuẩn bị xử lý...', null, null);
    
    // Clear existing interval
    if (statusCheckInterval) {
        clearInterval(statusCheckInterval);
    }
    
    // Start checking status every 2 seconds
    statusCheckInterval = setInterval(() => {
        checkFileStatus(fileId);
    }, 2000);
    
    // Initial check
    checkFileStatus(fileId);
}

// Check file status
function checkFileStatus(fileId) {
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
    
    fetch(`${contextPath}/status?fileId=${fileId}`)
        .then(response => response.json())
        .then(data => {
            updateStatusDisplay(data);
            
            // Stop checking if done or error
            if (data.status === 'done' || data.status === 'error') {
                clearInterval(statusCheckInterval);
                showUploadLoading(false);
            }
        })
        .catch(error => {
            console.error('Error checking status:', error);
            statusContent.innerHTML = createStatusHTML('error', 'Lỗi khi kiểm tra trạng thái', null, null);
        });
}

// Update status display
function updateStatusDisplay(data) {
    let message, progress;
    
    switch(data.status) {
        case 'pending':
            message = 'File đang chờ xử lý...';
            progress = 25;
            break;
        case 'processing':
            message = 'Đang chuyển đổi PDF sang Word...';
            progress = 75;
            break;
        case 'done':
            message = 'Chuyển đổi hoàn tất!';
            progress = 100;
            break;
        case 'error':
            message = 'Có lỗi xảy ra trong quá trình chuyển đổi!';
            progress = 0;
            break;
        default:
            message = 'Trạng thái không xác định';
            progress = 0;
    }
    
    statusContent.innerHTML = createStatusHTML(data.status, message, data.outputUrl, data.originalFilename);
}

// Create status HTML
function createStatusHTML(status, message, outputUrl, originalFilename) {
    let progressWidth = 0;
    switch(status) {
        case 'pending': progressWidth = 25; break;
        case 'processing': progressWidth = 75; break;
        case 'done': progressWidth = 100; break;
        case 'error': progressWidth = 0; break;
    }
    
    let html = `
        <div class="status-container status-${status}">
            <div style="display: flex; align-items: center; margin-bottom: 10px;">
                ${status === 'processing' ? '<div class="loading-spinner" style="display: inline-block; margin-right: 10px;"></div>' : ''}
                <strong>${message}</strong>
            </div>
            <div class="progress-bar">
                <div class="progress-fill" style="width: ${progressWidth}%"></div>
            </div>
    `;
    
    if (status === 'done' && outputUrl) {
        html += `
            <div style="margin-top: 20px; text-align: center;">
                <p><strong>File đã sẵn sàng để tải xuống!</strong></p>
                <a href="${outputUrl}" class="download-btn" target="_blank">
                    📥 Tải file Word (.docx)
                </a>
                <p style="font-size: 14px; color: #7f8c8d; margin-top: 10px;">
                    File gốc: ${originalFilename || 'document.pdf'}
                </p>
            </div>
        `;
    }
    
    if (status === 'error') {
        html += `
            <div style="margin-top: 20px; text-align: center;">
                <p style="margin-bottom: 15px;">Vui lòng thử lại hoặc kiểm tra file PDF của bạn.</p>
                <button onclick="location.reload()" class="submit-btn" style="width: auto; padding: 10px 20px;">
                    Thử lại
                </button>
            </div>
        `;
    }
    
    html += '</div>';
    return html;
}

// Show message
function showMessage(text, type) {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    // Create new message
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = text;
    
    // Insert after header
    const header = document.querySelector('.header');
    header.insertAdjacentElement('afterend', messageDiv);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.remove();
        }
    }, 5000);
}

// Cleanup function
function cleanup() {
    if (statusCheckInterval) {
        clearInterval(statusCheckInterval);
    }
}