let statusCheckIntervals = new Map(); 
let currentFileIds = new Set();


const uploadForm = document.getElementById('uploadForm');
const fileInput = document.getElementById('fileInput');
const uploadArea = document.getElementById('uploadArea');
const filePreview = document.getElementById('filePreview');
const submitBtn = document.getElementById('submitBtn');
const submitText = document.getElementById('submitText');
const uploadSpinner = document.getElementById('uploadSpinner');
const statusContainer = document.getElementById('statusContainer');
const statusContent = document.getElementById('statusContent');


document.addEventListener('DOMContentLoaded', function() {
    initializeEventListeners();
});

function initializeEventListeners() {

    uploadForm.addEventListener('submit', handleFormSubmit);
    
    fileInput.addEventListener('change', handleFileSelect);
    
    uploadArea.addEventListener('dragover', handleDragOver);
    uploadArea.addEventListener('dragleave', handleDragLeave);
    uploadArea.addEventListener('drop', handleDrop);
    
    document.getElementById('removeFile').addEventListener('click', removeSelectedFile);
    
    window.addEventListener('beforeunload', cleanup);
}

function handleFormSubmit(e) {
    const files = fileInput.files;
    
    if (files.length === 0) {
        e.preventDefault();
        showMessage('Vui lòng chọn ít nhất một file PDF để upload', 'error');
        return;
    }
    
    for (let i = 0; i < files.length; i++) {
        if (!validateFile(files[i])) {
            e.preventDefault();
            return;
        }
    }
    
    showUploadLoading(true);
}

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


function handleFileSelect(e) {
    const files = e.target.files;
    if (files.length > 0) {
        displayFilesPreview(files);
    }
}

function displayFilesPreview(files) {
    filePreview.style.display = 'block';
    uploadArea.style.padding = '20px';
    
    filePreview.innerHTML = '';
    
    Array.from(files).forEach((file, index) => {
        const fileItem = document.createElement('div');
        fileItem.className = 'file-item';
        fileItem.innerHTML = `
            <span class="file-icon">📄</span>
            <div class="file-details">
                <span class="file-name">${file.name}</span>
                <span class="file-size">${formatFileSize(file.size)}</span>
            </div>
            <button type="button" class="remove-file" data-index="${index}">×</button>
        `;
        filePreview.appendChild(fileItem);
    });

    document.querySelectorAll('.remove-file').forEach(button => {
        button.addEventListener('click', (e) => {
            const index = parseInt(e.target.dataset.index);
            removeFileByIndex(index);
        });
    });
}


function removeSelectedFile() {
    fileInput.value = '';
    filePreview.style.display = 'none';
    uploadArea.style.padding = '40px 20px';
}

function removeFileByIndex(index) {
    const dt = new DataTransfer();
    const files = fileInput.files;
    
    for (let i = 0; i < files.length; i++) {
        if (i !== index) {
            dt.items.add(files[i]);
        }
    }
    
    fileInput.files = dt.files;
    
    if (fileInput.files.length === 0) {
        filePreview.style.display = 'none';
        uploadArea.style.padding = '40px 20px';
    } else {
        displayFilesPreview(fileInput.files);
    }
}


function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}


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
        const validFiles = Array.from(files).filter(file => validateFile(file));
        
        if (validFiles.length > 0) {
            const dt = new DataTransfer();
            validFiles.forEach(file => dt.items.add(file));
            fileInput.files = dt.files;
            displayFilesPreview(fileInput.files);
        }
    }
}


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

function startStatusChecking(fileId) {
    currentFileIds.add(fileId);
    statusContainer.style.display = 'block';
    
    let fileStatusDiv = document.getElementById(`status-${fileId}`);
    if (!fileStatusDiv) {
        fileStatusDiv = document.createElement('div');
        fileStatusDiv.id = `status-${fileId}`;
        fileStatusDiv.className = 'file-status';
        statusContent.appendChild(fileStatusDiv);
    }
    
    fileStatusDiv.innerHTML = createStatusHTML('pending', 'Đang chuẩn bị xử lý...', null, null);
    
    if (statusCheckIntervals.has(fileId)) {
        clearInterval(statusCheckIntervals.get(fileId));
    }
    
    const interval = setInterval(() => {
        checkFileStatus(fileId);
    }, 2000);
    
    statusCheckIntervals.set(fileId, interval);
    
    checkFileStatus(fileId);
}

function checkFileStatus(fileId) {
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
    
    fetch(`${contextPath}/status?fileId=${fileId}`)
        .then(response => response.json())
        .then(data => {
            updateStatusDisplay(fileId, data);
            
            if (data.status === 'done' || data.status === 'error') {
                clearInterval(statusCheckIntervals.get(fileId));
                statusCheckIntervals.delete(fileId);
                currentFileIds.delete(fileId);
                
                if (currentFileIds.size === 0) {
                    showUploadLoading(false);
                }
            }
        })
        .catch(error => {
            console.error('Error checking status:', error);
            const fileStatusDiv = document.getElementById(`status-${fileId}`);
            if (fileStatusDiv) {
                fileStatusDiv.innerHTML = createStatusHTML('error', 'Lỗi khi kiểm tra trạng thái', null, null);
            }
        });
}

function updateStatusDisplay(fileId, data) {
    const fileStatusDiv = document.getElementById(`status-${fileId}`);
    if (!fileStatusDiv) return;
    
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
    
    fileStatusDiv.innerHTML = createStatusHTML(data.status, message, data.outputUrl, data.originalFilename);
}


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


function showMessage(text, type) {
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = text;
    
    const header = document.querySelector('.header');
    header.insertAdjacentElement('afterend', messageDiv);
    
    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.remove();
        }
    }, 5000);
}

function cleanup() {
    statusCheckIntervals.forEach(interval => clearInterval(interval));
    statusCheckIntervals.clear();
    currentFileIds.clear();
}