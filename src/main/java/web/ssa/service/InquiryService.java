package web.ssa.service;

import web.ssa.entity.Inquiry.Inquiry;
import java.util.List;

public interface InquiryService {
    List<Inquiry> getAll();
    Inquiry getById(Long id);
    Inquiry save(Inquiry inquiry);
    void delete(Long id); // 👈 이 메서드 선언 추가

}