package com.example.itbooks.user.dto;


import com.example.itbooks.user.domain.UserSupplier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 사용자 등록 명세서.
 */
@Getter
@NoArgsConstructor
public class UserRegistrationData implements UserSupplier {
    /**
     * 사용자 이메일.
     */
    @Email
    @Size(min = 3)
    private String email;

    /**
     * 사용자 이름.
     */
    @NotBlank
    private String name;

    /**
     * 사용자 비밀번호.
     */
    @NotBlank
    @Size(min = 4, max = 1024)
    private String password;

    @Builder
    public UserRegistrationData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
