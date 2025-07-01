package web.ssa.util;

import java.util.UUID;

import java.security.MessageDigest;

public class FileUtil {

    /**
     * 원본 파일명에서 확장자를 유지한 채, 파일명을 UUID(32자리, 하이픈 없음)로 변경합니다.
     * 
     * @param originalFilename 원본 파일명 (예: cat.jpg)
     * @return UUID로 변경된 파일명 (예: 123e4567e89b12d3a456426614174000.jpg)
     */
    public static String changeFileNameToUUID(String originalFilename) {
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("파일명이 비어있습니다.");
        }
        String ext = "";
        int dotIdx = originalFilename.lastIndexOf('.');
        if (dotIdx != -1) {
            ext = originalFilename.substring(dotIdx); // .jpg
        }
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 하이픈 없는 32자리
        return uuid + ext;
    }

    /**
     * SHA-256 해시 기반 64자리 파일명 생성 (확장자 유지)
     * 
     * @param originalFilename 원본 파일명 (예: cat.webp)
     * @return 64자리 해시 + 확장자 (예: f92176e0...c729633a.webp)
     */
    public static String changeFileNameToHash(String originalFilename) {
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("파일명이 비어있습니다.");
        }
        String ext = "";
        int dotIdx = originalFilename.lastIndexOf('.');
        if (dotIdx != -1) {
            ext = originalFilename.substring(dotIdx); // .webp 등
        }
        try {
            // 파일명 + 현재시간을 조합해서 해시 생성 (충돌 방지)
            String base = originalFilename + System.nanoTime();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString() + ext; // 64자리 해시 + 확장자
        } catch (Exception e) {
            throw new RuntimeException("해시 생성 실패", e);
        }
    }

}