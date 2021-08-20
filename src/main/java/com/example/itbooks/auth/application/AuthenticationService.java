package com.example.itbooks.auth.application;

import com.example.itbooks.auth.infra.JwtUtil;
import com.example.itbooks.global.utils.CustomPasswordEncoder;
import com.example.itbooks.user.domain.Role;
import com.example.itbooks.user.domain.User;
import com.example.itbooks.user.infra.RoleRepository;
import com.example.itbooks.user.infra.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 회원 인증을 처리한다.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private static final String WRONG_DATA = "잘못된 정보를 입력하였습니다.";
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomPasswordEncoder passwordEncoder;

    /**
     * 올바른 회원 정보라면 인증 토큰을 리턴하고, 그렇지 않으면 예외를 던집니다.
     *
     * @param email    회원 이메일
     * @param password 회원 비밀번호
     * @return 인증 토큰 문자열
     * @throws LoginFailException 로그인에 실패했을 경우
     */
    public String login(String email, String password) {
        final User user = findUserByEmail(email);

        if (!passwordEncoder.isPasswordMatch(password, user)) {
            throw new LoginFailException(WRONG_DATA);
        }
        return createToken(user.getId());
    }

    /**
     * 토큰을 파싱하여 사용자 아이디를 리턴한다.
     *
     * @param accessToken 인증 토큰
     * @return 사용자 id
     */
    public Long parseToken(String accessToken) {
        Claims claims = jwtUtil.decode(accessToken);
        return claims.get("userId", Long.class);
    }

    /**
     * 주어진 사용자 id에 해당하는 권한 목록을 리턴한다.
     *
     * @param userId 주어진 회원 id
     * @return 회원 권한 목록
     */
    public List<Role> roles(Long userId) {
        return roleRepository.findAllByUserId(userId);
    }

    private String createToken(Long userId) {
        return jwtUtil.createToken(userId);
    }

    private User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new LoginFailException(WRONG_DATA));
    }
}

