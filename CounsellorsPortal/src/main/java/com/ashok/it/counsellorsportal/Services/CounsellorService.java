package com.ashok.it.counsellorsportal.Services;

import com.ashok.it.counsellorsportal.Model.Counsellor;
import com.ashok.it.counsellorsportal.Dto.DashboardResponseDto;

public interface CounsellorService {
    
    boolean saveCounsellor(Counsellor counsellor);
    
    Counsellor login(String email, String pwd);
    
    DashboardResponseDto getDashboardInfo(Integer counsellorId);
}
