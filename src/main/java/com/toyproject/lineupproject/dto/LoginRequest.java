package com.toyproject.lineupproject.dto;

public record LoginRequest(
        String email,
        String password
) {
    public static LoginRequest of(
            String email,
            String password
    ) {
        return new LoginRequest(email, password);
    }

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
}
