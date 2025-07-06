package web.ssa.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {
    static {
        ImageIO.scanForPlugins(); // TwelveMonkeys 플러그인 등록
    }

    public static byte[] convertToWebP(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            throw new IOException("이미지를 읽을 수 없습니다.");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // WebP 형식으로 변환 시도
        boolean result = ImageIO.write(image, "webp", baos);
        if (!result) {
            // WebP 변환 실패 시 JPEG로 대체
            System.out.println("WebP 변환 실패, JPEG로 대체합니다.");
            baos.reset();
            result = ImageIO.write(image, "jpeg", baos);
            if (!result) {
                throw new IOException("이미지 형식 변환에 실패했습니다.");
            }
        }

        return baos.toByteArray();
    }

    public static MultipartFile toMultipartFile(byte[] imageBytes, String originalFilenameWithoutExt) {
        // 파일 확장자 결정 (WebP 변환 성공 여부에 따라)
        String extension = "webp";
        String contentType = "image/webp";
        
        // JPEG로 대체된 경우 확장자 변경
        if (imageBytes.length > 0 && imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            extension = "jpg";
            contentType = "image/jpeg";
        }
        
        return new ByteArrayMultipartFile(
                imageBytes,
                "images",  // form field name
                originalFilenameWithoutExt + "." + extension,
                contentType
        );
    }
}

