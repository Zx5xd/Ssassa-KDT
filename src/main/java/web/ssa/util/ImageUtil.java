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
        boolean result = ImageIO.write(image, "webp", baos);
        if (!result) {
            throw new IOException("WebP 형식으로 저장할 수 없습니다.");
        }

        return baos.toByteArray(); // webp 이미지 byte[]
    }

    public static MultipartFile toMultipartFile(byte[] webpBytes, String originalFilenameWithoutExt) {
        return new ByteArrayMultipartFile(
                webpBytes,
                "images",  // form field name
                originalFilenameWithoutExt + ".webp",
                "image/webp"
        );
    }
}

