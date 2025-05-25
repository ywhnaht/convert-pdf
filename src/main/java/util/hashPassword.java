package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashPassword {
      public static String hashPasswordSHA256(String password_hash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password_hash.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Không thể hash mật khẩu", e);
        }
    }
    
}
