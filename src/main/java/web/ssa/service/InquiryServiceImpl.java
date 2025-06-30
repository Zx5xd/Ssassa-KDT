package web.ssa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.ssa.entity.Inquiry.Inquiry;
import web.ssa.repository.InquiryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    @Override
    public void saveInquiry(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    @Override
    public Inquiry getInquiryById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteInquiry(Long id) {
        inquiryRepository.deleteById(id);
    }



}