package web.ssa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class IndexController {

    @GetMapping({ "/", "/index" })
    public String index() {
        return "shop/index";
    }

    // 405 Method Not Allowed 에러 핸들러
    @RequestMapping("/error/405")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleMethodNotAllowed(Model model) {
        model.addAttribute("error", "지원하지 않는 HTTP 메서드입니다.");
        model.addAttribute("message", "GET 요청이 필요한 페이지에 POST 요청을 보내거나, 그 반대의 경우입니다.");
        return "error/405";
    }
}