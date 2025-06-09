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
    public List<Inquiry> getAll() {
        return inquiryRepository.findAll();
    }

    @Override
    public Inquiry getById(Long id) {
        return inquiryRepository.findById(id).orElse(null);
    }

    @Override
    public Inquiry save(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }
}