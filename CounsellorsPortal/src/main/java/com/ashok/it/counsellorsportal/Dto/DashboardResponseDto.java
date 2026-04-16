package com.ashok.it.counsellorsportal.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto {
    
    private Long totalEnquiries;
    
    private Long openEnquiries;
    
    private Long enrolledEnquiries;
    
    private Long lostEnquiries;
}
