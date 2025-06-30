package web.ssa.service;

import web.ssa.entity.Inquiry.Inquiry;

import java.util.List;

public interface InquiryService {
    void saveInquiry(Inquiry inquiry);
    List<Inquiry> getAllInquiries();
    Inquiry getInquiryById(Long id);
    void deleteInquiry(Long id);

}