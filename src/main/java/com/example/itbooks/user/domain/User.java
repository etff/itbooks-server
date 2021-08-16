package com.example.itbooks.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 사용자 정보.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    /**
     * 사용자 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이메일.
     */
    private String email;

    /**
     * 사용자 이름.
     */
    private String name;

    /**
     * 사용자 비밀번호.
     */
    private String password;

    /**
     * 삭제된 사용자라면 true, 아니면 false.
     */
    private boolean deleted;

    @Builder
    public User(Long id, String email, String name, String password, boolean deleted) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.deleted = deleted;
    }

    public static User of(String name, String email, String password) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .deleted(false)
                .build();
    }

    /**
     * 사용자의 정보를 갱신합니다.
     *
     * @param source 사용자 갱신 정보
     */
    public void changeWith(User source) {
        name = source.name;
    }

    /**
     * 사용자 정보를 삭제했다고 표시합니다.
     */
    public void destroy() {
        deleted = true;
    }
}

