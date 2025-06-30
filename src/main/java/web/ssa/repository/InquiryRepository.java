package web.ssa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.Inquiry.Inquiry;


public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}