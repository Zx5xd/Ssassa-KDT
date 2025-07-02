package web.ssa.controller.client;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
public class ImageServeController {

    // 업로드된 이미지 폴더 절대 경로
    private static final String UPLOAD_DIR = System.getProperty("user.dir")
            + "/src/main/webapp/resources/uploads/";

    // 이미지 요청 시 반환 (예: <img src="/image/serve/sample.jpg">)
    @GetMapping("/image/serve/{filename:.+}")
    public void serveImage(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        File imageFile = new File(UPLOAD_DIR + filename);

        if (!imageFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // MIME 타입 지정 (브라우저에 올바른 포맷으로 전달)
        response.setContentType(getMimeType(filename));

        // 이미지 파일 내용을 응답 스트림에 복사
        try (InputStream in = new FileInputStream(imageFile)) {
            FileCopyUtils.copy(in, response.getOutputStream());
        }
    }

    // 파일 확장자에 따라 적절한 Content-Type 반환
    private String getMimeType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png"))
            return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg"))
            return "image/jpeg";
        if (lower.endsWith(".gif"))
            return "image/gif";
        return "application/octet-stream";
    }
}
