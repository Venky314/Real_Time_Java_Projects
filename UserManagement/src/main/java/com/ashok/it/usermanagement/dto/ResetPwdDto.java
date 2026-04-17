package com.ashok.it.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPwdDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
