package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import model.dao.FilesDAO;

public class ConvertJob {
    private int fileId;
    private String pdfCloudUrl;
    private String storedFilename;

    public ConvertJob(int fileId, String pdfCloudUrl, String storedFilename) {
        this.fileId = fileId;
        this.pdfCloudUrl = pdfCloudUrl;
        this.storedFilename = storedFilename;
    }

    private List<String> extractStructuredText(PDDocument document) throws Exception {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        
        // Phân tích và cấu trúc lại text
        List<String> structuredParagraphs = new ArrayList<>();
        String[] rawParagraphs = text.split("\\n\\s*\\n");
        
        for (String paragraph : rawParagraphs) {
            paragraph = paragraph.trim();
            if (!paragraph.isEmpty()) {
                // Xử lý các trường hợp đặc biệt
                if (paragraph.matches("^[A-Z][^.!?]*[.!?]$")) {
                    // Đoạn văn bản thông thường
                    structuredParagraphs.add(paragraph);
                } else if (paragraph.matches("^[0-9]+\\..*")) {
                    // Danh sách đánh số
                    structuredParagraphs.add(paragraph);
                } else if (paragraph.matches("^[•\\-\\*].*")) {
                    // Danh sách bullet points
                    structuredParagraphs.add(paragraph);
                } else {
                    // Các trường hợp khác, giữ nguyên
                    structuredParagraphs.add(paragraph);
                }
            }
        }
        
        return structuredParagraphs;
    }

    public void process() {
        FilesDAO filesDAO = new FilesDAO();
        File tempPdfFile = null;
        File tempDocxFile = null;
        
        try {
            filesDAO.updateFileStatus(fileId, "processing");

            // Tải PDF từ cloud về temp
            tempPdfFile = File.createTempFile("input_", ".pdf");
            try (InputStream in = new URL(pdfCloudUrl).openStream()) {
                Files.copy(in, tempPdfFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            // Đọc và xử lý PDF
            PDDocument document = PDDocument.load(tempPdfFile);
            List<String> structuredParagraphs = extractStructuredText(document);
            document.close();

            // Tạo file DOCX với định dạng phù hợp
            tempDocxFile = File.createTempFile("output_", ".docx");
            XWPFDocument docx = new XWPFDocument();
            
            for (String paragraph : structuredParagraphs) {
                XWPFParagraph para = docx.createParagraph();
                
                // Xác định định dạng đoạn văn
                if (paragraph.matches("^[0-9]+\\..*")) {
                    // Đoạn văn đánh số
                    para.setNumID(BigInteger.valueOf(1));
                    para.setAlignment(ParagraphAlignment.LEFT);
                } else if (paragraph.matches("^[•\\-\\*].*")) {
                    // Đoạn văn bullet point
                    para.setNumID(BigInteger.valueOf(2));
                    para.setAlignment(ParagraphAlignment.LEFT);
                } else if (paragraph.matches("^[A-Z][^.!?]*[.!?]$")) {
                    // Đoạn văn thông thường
                    para.setAlignment(ParagraphAlignment.CENTER);
                } else {
                    // Các trường hợp khác
                    para.setAlignment(ParagraphAlignment.LEFT);
                }
                
                XWPFRun run = para.createRun();
                run.setText(paragraph);
            }
            
            // Lưu file DOCX
            try (FileOutputStream out = new FileOutputStream(tempDocxFile)) {
                docx.write(out);
            }
            docx.close();

            // Upload DOCX lên cloud
            CloudStorageService cloudService = CloudStorageService.getInstance();
            String outputCloudUrl = cloudService.uploadFile(tempDocxFile, "pdf-converter/output");
            
            // Cập nhật database với URL output
            filesDAO.updateFileStatusAndOutput(fileId, "done", outputCloudUrl);

        } catch (Exception e) {
            filesDAO.updateFileStatus(fileId, "error");
            e.printStackTrace();
        } finally {
            // Dọn dẹp file tạm
            if (tempPdfFile != null && tempPdfFile.exists()) {
                tempPdfFile.delete();
            }
            if (tempDocxFile != null && tempDocxFile.exists()) {
                tempDocxFile.delete();
            }
        }
    }
}