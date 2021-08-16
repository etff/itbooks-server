package com.example.itbooks.auth.ui;

import com.example.itbooks.auth.application.AuthenticationService;
import com.example.itbooks.auth.dto.AuthRequestDto;
import com.example.itbooks.auth.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 사용자 인증 요청을 처리합니다.
 */
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    /**
     * 로그인 처리를 합니다.
     *
     * @param authRequestDto 로그인 요청 정보
     * @return 토큰 응답
     */
    @PostMapping
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        String email = authRequestDto.getEmail();
        String password = authRequestDto.getPassword();

        final String accessToken = authenticationService.login(email, password);
        final TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }
}
