package com.example.itbooks.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 수정 명세서.
 */
@Getter
@NoArgsConstructor
public class UserModificationData {
    /**
     * 사용자 이름.
     */
    @NotBlank
    private String name;

    @Builder
    public UserModificationData(String name) {
        this.name = name;
    }
}
