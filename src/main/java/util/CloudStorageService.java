package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.util.Map;

public class CloudStorageService {
    private static CloudStorageService instance;
    private Cloudinary cloudinary;
    
    private CloudStorageService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "deayvqofe",
            "api_key", "223642514264139", 
            "api_secret", "qn6GIkT_8Bamt_OVqErHEpA9AuQ"));
    }
    
    public static synchronized CloudStorageService getInstance() {
        if (instance == null) instance = new CloudStorageService();
        return instance;
    }
    
    @SuppressWarnings("unchecked")
    public String uploadFile(File file, String folder, String filename) throws Exception {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file, 
            ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "raw",
                "type", "upload",
                "public_id", filename
            )
        );
        String url = (String) uploadResult.get("secure_url");
        System.out.println("Cloudinary public URL: " + url);
        return url;
    }
    
    public String getDownloadUrl(String publicId) {
        return cloudinary.url()
            .resourceType("raw")
            .type("upload") 
            .secure(true)
            .generate(publicId);
    }
}