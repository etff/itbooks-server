package com.example.itbooks.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 사용자 인증 요청.
 */
@Getter
@NoArgsConstructor
public class AuthRequestDto {
    /**
     * 이메일.
     */
    @Email
    @Size(min = 3)
    private String email;
    /**
     * 비밀번호.
     */
    @NotBlank
    @Size(min = 4, max = 1024)
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
