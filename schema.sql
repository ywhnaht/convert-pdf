
CREATE DATABASE IF NOT EXISTS ltmang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ltmang;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    stored_filename VARCHAR(255) NOT NULL,
    type CHAR(100) NOT NULL,
    status ENUM('pending', 'processing', 'done', 'error') DEFAULT 'pending',
    result_path VARCHAR(255),
    input_url VARCHAR(255),
    output_url VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(100),
    file_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE SET NULL
);

INSERT INTO users (username, password_hash) VALUES
('alice', '$2a$10$abcdefghijklmnopqrstuv'), 
('bob',   '$2a$10$1234567890abcdefghijklm');

INSERT INTO files (user_id, original_filename, stored_filename, status, type, input_url, output_url, created_at, updated_at) VALUES
(1, 'sample1.pdf', '20240601_1_sample1.pdf', 'done', 'pdf', 'https://example.com/sample1.pdf', 'https://example.com/sample1.docx', NOW(), NOW()),
(2, 'report.pdf', '20240601_2_report.pdf', 'pending', 'pdf', 'https://example.com/report.pdf', NULL, NOW(), NULL);

INSERT INTO logs (user_id, action, file_id) VALUES
(1, 'upload', 1),
(1, 'convert', 1),
(2, 'upload', 2);