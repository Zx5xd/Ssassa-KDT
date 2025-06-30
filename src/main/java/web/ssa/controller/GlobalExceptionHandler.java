package web.ssa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 405 Method Not Allowed 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, Model model) {
        model.addAttribute("error", "지원하지 않는 HTTP 메서드입니다.");
        model.addAttribute("message",
                "요청한 메서드: " + ex.getMethod() +
                        ", 지원되는 메서드: " + String.join(", ", ex.getSupportedMethods()));
        return "error/405";
    }

    // 파일 업로드 크기 초과 처리
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("error", "파일 업로드 크기 초과");
        model.addAttribute("message", "업로드하려는 파일의 크기가 허용된 최대 크기를 초과했습니다. (최대 10MB)");
        return "error/413";
    }

    // 404 Not Found 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("error", "서버 오류가 발생했습니다.");
        model.addAttribute("message", "잠시 후 다시 시도해주세요.");
        return "error/500";
    }
}