package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.ssa.entity.member.User;

@Controller
@RequiredArgsConstructor
public class AdminController {

    // 관리자 메인 페이지 (메뉴 선택 화면)
    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        // 로그인 안했거나, ADMIN 권한이 아니면 로그인 페이지로 이동
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("adminName", loginUser.getName());
        return "admin/admin"; // /WEB-INF/views/admin/admin.jsp
    }

    // 상품 등록 관리 페이지 이동
    @GetMapping("/admin/products")
    public String manageProducts(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/productList"; // /WEB-INF/views/admin/productList.jsp
    }

    // 회원 목록 관리 페이지 이동
    @GetMapping("/admin/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/userList"; // /WEB-INF/views/admin/userList.jsp
    }

    // 환불/문의사항 관리 페이지 이동
    @GetMapping("/admin/inquiries")
    public String manageInquiries(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/inquiryList"; // /WEB-INF/views/admin/inquiryList.jsp
    }

    // 공통: 관리자 권한 확인 메서드
    private boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }
}
