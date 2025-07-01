package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ssa.entity.Inquiry.Inquiry;
import web.ssa.entity.member.User;
import web.ssa.service.InquiryService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        return "Inquiry/list";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "Inquiry/write";
    }

    @PostMapping("/write")
    public String writeInquiry(@ModelAttribute Inquiry inquiry,
                               @RequestParam("file") MultipartFile file,
                               HttpSession session) throws IOException {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        if (!file.isEmpty()) {
            // 파일 저장 디렉토리 (static 아래)
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // 폴더 없으면 생성

            // 파일명 중복 방지를 위한 UUID 추가
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();
            String filePath = uploadDir + "/" + fileName;

            // 파일 저장
            file.transferTo(new File(filePath));

            // Inquiry 엔티티에 파일 정보 저장 (브라우저에서 접근 가능하게 경로 지정)
            inquiry.setFileName(fileName);
            inquiry.setFilePath("/uploads/" + fileName);
        }

        // 사용자 및 기본 정보 설정
        inquiry.setUsername(loginUser.getName());
        inquiry.setCreatedAt(LocalDateTime.now());
        inquiry.setStatus("PENDING");
        inquiry.setHasReply(false);

        inquiryService.saveInquiry(inquiry);
        return "redirect:/inquiry/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        return "Inquiry/detail";
    }

    @PostMapping("/reply/{id}")
    public String replyToInquiry(@PathVariable Long id,
                                 @RequestParam("adminComment") String adminComment) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        inquiry.setAdminComment(adminComment);
        inquiry.setHasReply(true);
        inquiry.setStatus("ANSWERED");
        inquiryService.saveInquiry(inquiry);
        return "redirect:/inquiry/detail/" + id;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !inquiry.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }
        model.addAttribute("inquiry", inquiry);
        return "Inquiry/edit";
    }

    @PostMapping("/edit/{id}")
    public String editInquiry(@PathVariable Long id,
                              @ModelAttribute Inquiry updateInquiry,
                              HttpSession session) {
        Inquiry original = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !original.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }

        original.setTitle(updateInquiry.getTitle());
        original.setContent(updateInquiry.getContent());
        inquiryService.saveInquiry(original);
        return "redirect:/inquiry/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteInquiry(@PathVariable Long id, HttpSession session) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !inquiry.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }
        inquiryService.deleteInquiry(id);
        return "redirect:/inquiry/list";
    }
}