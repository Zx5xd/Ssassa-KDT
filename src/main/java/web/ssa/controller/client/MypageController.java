package web.ssa.controller.client;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ssa.entity.member.User;
import web.ssa.service.member.MemberService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") +
            "/src/main/webapp/resources/uploads/";

    /** 마이페이지 메인 화면 */
    @GetMapping("/mypage")
    public String showMypage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null)
            return "redirect:/login";

        model.addAttribute("user", user);
        String formattedDate = user.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        model.addAttribute("formattedCreatedAt", formattedDate);

        return "client/mypage";
    }

    /** 수정 폼 이동 */
    @GetMapping("/mypage/edit")
    public String editForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null)
            return "redirect:/login";

        model.addAttribute("user", user);
        return "client/mypageEdit";
    }

    /** 회원정보 및 프로필 이미지 수정 */
    @PostMapping("/mypage/update")
    public String updateMypage(@RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("nickname") String nickname,
            @RequestParam("phone") String phone,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            HttpSession session,
            Model model) {
        System.out.println("[ MypageController ] updateMypage");

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isEmpty())
            return "redirect:/login";

        User user = userOpt.get();

        // 닉네임 중복 검사
        if (!user.getNickname().equals(nickname) &&
                memberService.existsByUsername(nickname)) {
            model.addAttribute("user", user);
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "client/mypageEdit";
        }

        user.setNickname(nickname);
        user.setPhone(phone);

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }

        System.out.println("[ MypageController ] password ");

        // 이미지가 있다면 새로 저장
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                System.out.println("[ MypageController ] profileImage : " + profileImage);
                System.out.println("[ MypageController ] UPLOAD_DIR : " + UPLOAD_DIR);

                // 파일 크기 검증 (5MB 제한)
                long maxSize = 5 * 1024 * 1024; // 5MB
                if (profileImage.getSize() > maxSize) {
                    model.addAttribute("user", user);
                    model.addAttribute("error", "파일 크기가 5MB를 초과합니다. 더 작은 이미지를 선택해주세요.");
                    return "client/mypageEdit";
                }

                // 파일 타입 검증
                String contentType = profileImage.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    model.addAttribute("user", user);
                    model.addAttribute("error", "이미지 파일만 업로드 가능합니다.");
                    return "client/mypageEdit";
                }

                File dir = new File(UPLOAD_DIR);
                if (!dir.exists())
                    dir.mkdirs();

                System.out.println("[ MypageController ] dir : " + dir);

                // 기존 이미지 삭제
                if (user.getProfileImage() != null) {
                    String oldFilename = user.getProfileImage()
                            .substring(user.getProfileImage().lastIndexOf("/") + 1);
                    File oldFile = new File(UPLOAD_DIR + oldFilename);
                    Files.deleteIfExists(oldFile.toPath());
                }

                // 새 이미지 저장
                String newFilename = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
                File destFile = new File(UPLOAD_DIR + newFilename);
                profileImage.transferTo(destFile);
                user.setProfileImage("/image/serve/" + newFilename);
                System.out.println("[ MypageController ] 새 이미지 저장 완료");

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("user", user);
                model.addAttribute("error", "프로필 이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
                return "client/mypageEdit";
            }
        }

        // DB 갱신 및 세션 갱신
        memberService.updateUser(user);
        session.setAttribute("loginUser", user);
        return "redirect:/mypage";
    }

    /** 프로필 이미지 삭제 */
    @PostMapping("/mypage/profile/delete")
    public String deleteProfileImage(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null || user.getProfileImage() == null)
            return "redirect:/mypage/edit";

        // 서비스에서 파일 삭제 + DB null 처리
        memberService.removeProfileImage(user.getEmail());

        // 세션 유저도 반영
        user.setProfileImage(null);
        session.setAttribute("loginUser", user);

        return "redirect:/mypage/edit";
    }

    /** 회원 탈퇴 */
    @PostMapping("/withdraw")
    public String withdraw(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null)
            return "redirect:/login";

        memberService.withdrawUser(user.getEmail());
        session.invalidate();
        return "redirect:/login";
    }
}
