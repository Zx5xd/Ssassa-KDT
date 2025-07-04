package web.ssa.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ssa.entity.Inquiry.Inquiry;
import web.ssa.entity.member.User;
import web.ssa.service.InquiryService;
import web.ssa.service.WebDAVService;
import web.ssa.util.FormatUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;
    private final FormatUtil formatUtil = new FormatUtil();
    @Autowired
    private final WebDAVService webDAVService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        model.addAttribute("formatUtil", formatUtil);
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
            String fileUrl = webDAVService.uploadFile(file);
            String fildImgFileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            inquiry.setFileName(fildImgFileName);
            inquiry.setFilePath(fileUrl);
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
    public String detail(@PathVariable("id") Long id, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        return "Inquiry/detail";
    }

    @PostMapping("/reply/{id}")
    public String replyToInquiry(@PathVariable("id") Long id,
            @RequestParam("adminComment") String adminComment) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        inquiry.setAdminComment(adminComment);
        inquiry.setHasReply(true);
        inquiry.setStatus("ANSWERED");
        inquiryService.saveInquiry(inquiry);
        return "redirect:/inquiry/detail/" + id;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, HttpSession session, Model model) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !inquiry.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }
        model.addAttribute("inquiry", inquiry);
        return "Inquiry/edit";
    }

    @PostMapping("/edit/{id}")
    public String editInquiry(@PathVariable("id") Long id,
            @ModelAttribute Inquiry updateInquiry,
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        Inquiry original = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !original.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }

        if (!file.isEmpty()) {
            String fileUrl;
            try {
                fileUrl = webDAVService.uploadFile(file);
                String fildImgFileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                original.setFileName(fildImgFileName);
                original.setFilePath(fileUrl);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        original.setTitle(updateInquiry.getTitle());
        original.setContent(updateInquiry.getContent());
        inquiryService.saveInquiry(original);
        return "redirect:/inquiry/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteInquiry(@PathVariable("id") Long id, HttpSession session) {
        Inquiry inquiry = inquiryService.getInquiryById(id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !inquiry.getUsername().equals(loginUser.getName())) {
            return "redirect:/inquiry/list";
        }
        inquiryService.deleteInquiry(id);
        return "redirect:/inquiry/list";
    }
}