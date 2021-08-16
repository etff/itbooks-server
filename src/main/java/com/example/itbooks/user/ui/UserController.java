package com.example.itbooks.user.ui;

import com.example.itbooks.user.application.UserService;
import com.example.itbooks.user.dto.UserRegistrationData;
import com.example.itbooks.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 주어진 사용자 정보를 등록한다.
     *
     * @param dto 사용자 등록 명세서
     * @return 사용자 식별자
     */
    @PostMapping
    public ResponseEntity<UserResponse> registerMember(@Valid @RequestBody UserRegistrationData dto) {
        final Long userId = userService.registerUser(dto);
        final UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .build();
        return ResponseEntity.created(URI.create("/users/" + userId)).body(userResponse);
    }

}
