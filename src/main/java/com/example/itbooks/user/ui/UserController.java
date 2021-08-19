package com.example.itbooks.user.ui;

import com.example.itbooks.user.application.UserService;
import com.example.itbooks.user.dto.UserModificationData;
import com.example.itbooks.user.dto.UserRegistrationData;
import com.example.itbooks.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        final UserResponse userResponse = userService.registerUser(dto);
        return ResponseEntity.created(URI.create("/users/" + userResponse.getId())).body(userResponse);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 수정하고, 해당 사용자를 리턴한다.
     *
     * @param id               사용자 식별자
     * @param modificationData 사용자 수정 명세서
     * @return 수정된 사용자
     * @throws AccessDeniedException
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UserModificationData modificationData
    ) throws AccessDeniedException {
        UserResponse userResponse = userService.updateUser(id, modificationData);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * 주어진 식별자에 해당하는 사용자를 삭제한다.
     *
     * @param id 사용자 식별자
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
