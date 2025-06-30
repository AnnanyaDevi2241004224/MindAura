package com.MindAura.MindAura.payLoad;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
