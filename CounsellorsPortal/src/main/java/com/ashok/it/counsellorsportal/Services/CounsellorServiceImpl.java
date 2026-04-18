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

    @Override
    public Counsellor getCounsellorById(Integer counsellorId) {
        Optional<Counsellor> findById = counsellorRepo.findById(counsellorId);
        return findById.orElse(null);
    }

    @Override
    public boolean updateProfile(Counsellor counsellor, String currentPassword, String newPassword, String confirmPassword) {
        try {
            Optional<Counsellor> existingOpt = counsellorRepo.findById(counsellor.getCounsellorId());
            if (!existingOpt.isPresent()) {
                return false;
            }
            
            Counsellor existing = existingOpt.get();
            
            // If password change is requested
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                // Validate current password
                if (currentPassword == null || currentPassword.trim().isEmpty()) {
                    return false;
                }
                
                // Verify current password matches
                if (!existing.getPwd().equals(currentPassword)) {
                    return false;
                }
                
                // Validate new passwords match
                if (!newPassword.equals(confirmPassword)) {
                    return false;
                }
                
                // Update password
                existing.setPwd(newPassword);
            }
            
            // Update other fields
            existing.setName(counsellor.getName());
            existing.setPhno(counsellor.getPhno());
            
            counsellorRepo.save(existing);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error updating profile: " + e.getMessage());
            return false;
        }
    }
}
