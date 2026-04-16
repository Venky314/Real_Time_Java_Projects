package com.ashok.it.counsellorsportal.Services;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import com.ashok.it.counsellorsportal.Dto.EnqFilterRequestDto;

import java.util.List;

public interface EnqService {
    
    boolean addEnquiry(Enquiry enq, Integer counsellorId);
    
    List<Enquiry> getAllEnquiries(Integer counsellorId);
    
    List<Enquiry> getEnquiriesWithFilters(EnqFilterRequestDto reqDto, Integer counsellorId);
    
    Enquiry editEnquiry(Integer enqId);
    
    boolean updateEnquiry(Enquiry enquiry);
}
