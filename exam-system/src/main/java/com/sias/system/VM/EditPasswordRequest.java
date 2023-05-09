package com.sias.system.VM;

import lombok.Data;

@Data
public class EditPasswordRequest {
    private String password;
    private String newPassword;
    private String confirmPassword;
}