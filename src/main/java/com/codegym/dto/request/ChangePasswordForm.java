package com.codegym.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}