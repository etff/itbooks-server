package com.example.itbooks.user.dto;

import com.example.itbooks.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 응답.
 */
@Getter
@NoArgsConstructor
public class UserResponse {

    /**
     * 사용자 식별자.
     */
    private Long id;
    /**
     * 사용자 이름.
     */
    private String name;
    /**
     * 사용자 이메일.
     */
    private String email;

    @Builder
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * 사용자 정보를 사용자 응답 정보로 생성한다.
     */
    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
