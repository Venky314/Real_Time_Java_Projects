package com.ashok.it.counsellorsportal.Services;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import com.ashok.it.counsellorsportal.Repository.EnquiryRepo;
import com.ashok.it.counsellorsportal.Dto.EnqFilterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnqServiceImpl implements EnqService {

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Override
    public boolean addEnquiry(Enquiry enq, Integer counsellorId) {
        enq.setCounsellorId(counsellorId);
        enq.setEnqStatus("New");
        Enquiry savedEnquiry = enquiryRepo.save(enq);
        return savedEnquiry.getEnqId() != null;
    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId) {
        return enquiryRepo.findByCounsellorId(counsellorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilters(EnqFilterRequestDto reqDto, Integer counsellorId) {
        Integer courseId = reqDto.getCourseId();
        String enqStatus = reqDto.getEnqStatus();
        String classMode = reqDto.getClassMode();
        
        if (courseId == null && (enqStatus == null || enqStatus.isEmpty()) 
            && (classMode == null || classMode.isEmpty())) {
            return getAllEnquiries(counsellorId);
        }
        
        return enquiryRepo.findByFilters(counsellorId, courseId, enqStatus, classMode);
    }

    @Override
    public Enquiry editEnquiry(Integer enqId) {
        Optional<Enquiry> findById = enquiryRepo.findById(enqId);
        return findById.orElse(null);
    }

    @Override
    public boolean updateEnquiry(Enquiry enquiry) {
        Optional<Enquiry> findById = enquiryRepo.findById(enquiry.getEnqId());
        if (findById.isPresent()) {
            Enquiry existingEnq = findById.get();
            existingEnq.setStuName(enquiry.getStuName());
            existingEnq.setStuPhno(enquiry.getStuPhno());
            existingEnq.setClassMode(enquiry.getClassMode());
            existingEnq.setCourseId(enquiry.getCourseId());
            existingEnq.setEnqStatus(enquiry.getEnqStatus());
            enquiryRepo.save(existingEnq);
            return true;
        }
        return false;
    }
}
