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

import model.bo.FilesBO;
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
 
        List<String> structuredParagraphs = new ArrayList<>();
        String[] rawParagraphs = text.split("\\n\\s*\\n");
        
        for (String paragraph : rawParagraphs) {
            paragraph = paragraph.trim();
            if (!paragraph.isEmpty()) {
               
                if (paragraph.matches("^[A-Z][^.!?]*[.!?]$")) {
                   
                    structuredParagraphs.add(paragraph);
                } else if (paragraph.matches("^[0-9]+\\..*")) {
                   
                    structuredParagraphs.add(paragraph);
                } else if (paragraph.matches("^[•\\-\\*].*")) {
                  
                    structuredParagraphs.add(paragraph);
                } else {
                   
                    structuredParagraphs.add(paragraph);
                }
            }
        }
        
        return structuredParagraphs;
    }

    public void process() {
        FilesBO filesBO = new FilesBO();
        File tempPdfFile = null;
        File tempDocxFile = null;
        
        try {
            filesBO.updateFileStatus(fileId, "processing");

            tempPdfFile = File.createTempFile("input_", ".pdf");
            try (InputStream in = new URL(pdfCloudUrl).openStream()) {
                Files.copy(in, tempPdfFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            
            PDDocument document = PDDocument.load(tempPdfFile);
            List<String> structuredParagraphs = extractStructuredText(document);
            document.close();

     
            tempDocxFile = File.createTempFile("output_", ".docx");
            XWPFDocument docx = new XWPFDocument();
            
            for (String paragraph : structuredParagraphs) {
                XWPFParagraph para = docx.createParagraph();
                
             
                if (paragraph.matches("^[0-9]+\\..*")) {
                  
                    para.setNumID(BigInteger.valueOf(1));
                    para.setAlignment(ParagraphAlignment.LEFT);
                } else if (paragraph.matches("^[•\\-\\*].*")) {
              
                    para.setNumID(BigInteger.valueOf(2));
                    para.setAlignment(ParagraphAlignment.LEFT);
                } else if (paragraph.matches("^[A-Z][^.!?]*[.!?]$")) {
               
                    para.setAlignment(ParagraphAlignment.CENTER);
                } else {
             
                    para.setAlignment(ParagraphAlignment.LEFT);
                }
                
                XWPFRun run = para.createRun();
                run.setText(paragraph);
            }
            

            try (FileOutputStream out = new FileOutputStream(tempDocxFile)) {
                docx.write(out);
            }
            docx.close();


            CloudStorageService cloudService = CloudStorageService.getInstance();
            String docxFilename = convertToDocxFilename(storedFilename);
            String outputCloudUrl = cloudService.uploadFile(tempDocxFile, "pdf-converter/output/", docxFilename);
            
         
            filesBO.updateFileStatusAndOutput(fileId, "done", outputCloudUrl, docxFilename);

        } catch (Exception e) {
            filesBO.updateFileStatus(fileId, "error");
            e.printStackTrace();
        } finally {
         
            if (tempPdfFile != null && tempPdfFile.exists()) {
                tempPdfFile.delete();
            }
            if (tempDocxFile != null && tempDocxFile.exists()) {
                tempDocxFile.delete();
            }
        }
    }

    private String convertToDocxFilename(String originalFilename) {
        if (originalFilename.toLowerCase().endsWith(".pdf")) {
            return originalFilename.substring(0, originalFilename.length() - 4) + ".docx";
        }
        return originalFilename + ".docx";
    }
}