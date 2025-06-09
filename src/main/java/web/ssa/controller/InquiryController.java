package web.ssa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.entity.Inquiry.Inquiry;
import web.ssa.service.InquiryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry") // 💡 URI는 소문자 사용 (브라우저 주소창에서 요청할 때)
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의사항 목록 페이지
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("inquiryList", inquiryService.getAll());
        return "Inquiry/list"; // 💡 JSP 물리 경로: /WEB-INF/views/Inquiry/list.jsp
    }

    // 문의 작성 폼
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("inquiry", new Inquiry());
        return "Inquiry/write"; // 💡 JSP: /WEB-INF/views/Inquiry/write.jsp
    }

    // 문의 작성 처리
    @PostMapping("/write")
    public String writeSubmit(@ModelAttribute Inquiry inquiry) {
        inquiryService.save(inquiry);
        return "redirect:/inquiry/list"; // 💡 URI로 redirect (소문자)
    }

    // 상세 조회
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryService.getById(id);
        model.addAttribute("inquiry", inquiry);
        return "Inquiry/detail"; // 💡 JSP: /WEB-INF/views/Inquiry/detail.jsp
    }
}