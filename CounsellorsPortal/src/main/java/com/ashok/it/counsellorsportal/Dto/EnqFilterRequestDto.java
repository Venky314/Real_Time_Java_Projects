package com.ashok.it.counsellorsportal.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnqFilterRequestDto {
    
    private Integer courseId;
    
    private String enqStatus;
    
    private String classMode;
}
