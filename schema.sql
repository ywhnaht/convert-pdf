CREATE DATABASE IF NOT EXISTS ltmang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ltmang;

CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    stored_filename VARCHAR(255) NOT NULL,
    status ENUM('pending', 'processing', 'done', 'error') DEFAULT 'pending',
    result_path VARCHAR(255),
    word_count INT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(100),
    file_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE SET NULL,
    FOREIGN KEY (file_id) REFERENCES Files(id) ON DELETE SET NULL
);

INSERT INTO User (username, password_hash) VALUES
('alice', '$2a$10$abcdefghijklmnopqrstuv'), -- bcrypt hash giả lập
('bob',   '$2a$10$1234567890abcdefghijklm');

INSERT INTO Files (user_id, original_filename, stored_filename, status, result_path, word_count, uploaded_at, processed_at) VALUES
(1, 'sample1.pdf', '20240601_1_sample1.pdf', 'done', 'converted/sample1.docx', 1200, NOW(), NOW()),
(2, 'report.pdf', '20240601_2_report.pdf', 'pending', NULL, NULL, NOW(), NULL);

INSERT INTO Logs (user_id, action, file_id) VALUES
(1, 'upload', 1),
(1, 'convert', 1),
(2, 'upload', 2);