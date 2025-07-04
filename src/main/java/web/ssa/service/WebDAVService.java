package web.ssa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import web.ssa.util.FileUtil;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class WebDAVService {

    @Value("${webdav.url:http://192.168.1.100:5005/webdav}")
    private String webdavUrl;

    @Value("${webdav.username:admin}")
    private String username;

    @Value("${webdav.password:}")
    private String password;

    @Value("${webdav.upload-folder:uploads}")
    private String uploadFolder;

    private RestTemplate restTemplate;

    public WebDAVService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 파일을 WebDAV 서버에 업로드 (HTTP PUT)
     * 
     * @param file 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 업로드 실패 시
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 파일명을 UUID로 변경
        String originalFilename = file.getOriginalFilename();
        String newFileName = FileUtil.changeFileNameToHash(originalFilename);

        // WebDAV URL 생성
        // String webdavFileUrl = webdavUrl + "/" + uploadFolder + "/" + newFileName;
        String webdavFileUrl = webdavUrl + "/" + uploadFolder + "/" + newFileName;
        System.out.println("Uploading to WebDAV: " + webdavFileUrl);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Basic Auth 설정
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);

        // HTTP 엔티티 생성
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        // PUT 요청 실행
        ResponseEntity<String> response = restTemplate.exchange(
                webdavFileUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        // 응답 확인
        if (response.getStatusCode() != HttpStatus.CREATED && response.getStatusCode() != HttpStatus.OK) {
            throw new IOException("WebDAV 업로드 실패: " + response.getStatusCode());
        }

        return webdavFileUrl;
    }

    /**
     * WebDAV 서버에서 파일 삭제 (HTTP DELETE)
     * 
     * @param fileUrl 삭제할 파일의 URL
     * @throws IOException 삭제 실패 시
     */
    public void deleteFile(String fileUrl) throws IOException {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        // Basic Auth 설정
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);

        // HTTP 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // DELETE 요청 실행
        ResponseEntity<String> response = restTemplate.exchange(
                fileUrl,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        // 응답 확인
        if (response.getStatusCode() != HttpStatus.NO_CONTENT && response.getStatusCode() != HttpStatus.OK) {
            throw new IOException("WebDAV 삭제 실패: " + response.getStatusCode());
        }
    }

    /**
     * WebDAV 서버에서 파일 존재 여부 확인 (HTTP HEAD)
     * 
     * @param fileUrl 확인할 파일의 URL
     * @return 파일 존재 여부
     */
    public boolean fileExists(String fileUrl) {
        try {
            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();

            // Basic Auth 설정
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);

            // HTTP 엔티티 생성
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // HEAD 요청 실행
            ResponseEntity<String> response = restTemplate.exchange(
                    fileUrl,
                    HttpMethod.HEAD,
                    requestEntity,
                    String.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }
}