package com.ashok.it.counsellorsportal.Services;

import com.ashok.it.counsellorsportal.Model.Counsellor;
import com.ashok.it.counsellorsportal.Repository.CounsellorRepo;
import com.ashok.it.counsellorsportal.Repository.EnquiryRepo;
import com.ashok.it.counsellorsportal.Dto.DashboardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounsellorServiceImpl implements CounsellorService {

    @Autowired
    private CounsellorRepo counsellorRepo;

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Override
    public boolean saveCounsellor(Counsellor counsellor) {
        if (counsellorRepo.existsByEmail(counsellor.getEmail())) {
            return false;
        }
        counsellorRepo.save(counsellor);
        return true;
    }

    @Override
    public Counsellor login(String email, String pwd) {
        Optional<Counsellor> byEmailAndPwd = counsellorRepo.findByEmailAndPwd(email, pwd);
        Counsellor counsellor = byEmailAndPwd.orElse(null);
        if (counsellor != null) {
            return counsellor;
        }
        return null;
    }

    @Override
    public DashboardResponseDto getDashboardInfo(Integer counsellorId) {
        DashboardResponseDto dashboard = new DashboardResponseDto();
        
        Long totalEnquiries = enquiryRepo.countTotalEnquiries(counsellorId);
        Long openEnquiries = enquiryRepo.countOpenEnquiries(counsellorId);
        Long enrolledEnquiries = enquiryRepo.countEnrolledEnquiries(counsellorId);
        Long lostEnquiries = enquiryRepo.countLostEnquiries(counsellorId);
        
        dashboard.setTotalEnquiries(totalEnquiries);
        dashboard.setOpenEnquiries(openEnquiries);
        dashboard.setEnrolledEnquiries(enrolledEnquiries);
        dashboard.setLostEnquiries(lostEnquiries);
        
        return dashboard;
    }
}
