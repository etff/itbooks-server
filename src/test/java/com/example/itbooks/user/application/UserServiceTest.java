package com.example.itbooks.user.application;

import com.example.itbooks.global.utils.CustomPasswordEncoder;
import com.example.itbooks.user.domain.Role;
import com.example.itbooks.user.domain.User;
import com.example.itbooks.user.dto.UserModificationData;
import com.example.itbooks.user.dto.UserRegistrationData;
import com.example.itbooks.user.dto.UserResponse;
import com.example.itbooks.user.infra.RoleRepository;
import com.example.itbooks.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    private static final String GIVEN_EMAIL_ADDRESS = "existed@example.com";
    private static final String GIVEN_USER_NAME = "USER1";
    private static final String UPDATE_USER_NAME = GIVEN_USER_NAME + "_UPDATED";
    private static final Long GIVEN_USER_ID = 1L;

    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private UserService userService;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder(passwordEncoder);
        userService = new UserService(
                userRepository, roleRepository, customPasswordEncoder);
    }

    @Test
    void registerUser() {
        // given
        UserRegistrationData registrationData = UserRegistrationData.builder()
                .email("tester@example.com")
                .name("Tester")
                .password("test")
                .build();
        User user = User.builder()
                .id(GIVEN_USER_ID)
                .name("Tester")
                .email("tester@example.com")
                .password("test")
                .build();
        given(userRepository.save(any()))
                .willReturn(user);
        // when
        UserResponse userResponse = userService.registerUser(registrationData);

        // then
        assertThat(userResponse.getId()).isEqualTo(GIVEN_USER_ID);
        verify(userRepository).save(any(User.class));
        verify(roleRepository).save(any(Role.class));
    }


    @Test
    void updateUserWithExistedId() {
        // given
        given(userRepository.findByIdAndDeletedIsFalse(GIVEN_USER_ID))
                .willReturn(Optional.of(
                        User.builder()
                                .id(GIVEN_USER_ID)
                                .email(GIVEN_EMAIL_ADDRESS)
                                .name(GIVEN_USER_NAME)
                                .password("test")
                                .build()));
        UserModificationData modificationData = UserModificationData.builder()
                .name(UPDATE_USER_NAME)
                .build();

        // when
        UserResponse user = userService.updateUser(GIVEN_USER_ID, modificationData);

        // then
        assertThat(user.getId()).isEqualTo(GIVEN_USER_ID);
        assertThat(user.getName()).isEqualTo(UPDATE_USER_NAME);
        verify(userRepository).findByIdAndDeletedIsFalse(GIVEN_USER_ID);
    }

    @Test
    void deleteUserWithExistedId() {
        // given
        User user = User.builder()
                .deleted(false)
                .build();
        given(userRepository.findByIdAndDeletedIsFalse(anyLong()))
                .willReturn(Optional.of(user));

        // when
        userService.deleteUser(anyLong());

        // then
        assertThat(user.isDeleted()).isTrue();
    }

    @Test
    void getUser() {
        User user = User.builder()
                .id(GIVEN_USER_ID)
                .email(GIVEN_EMAIL_ADDRESS)
                .name(GIVEN_USER_NAME)
                .password("test")
                .build();
        given(userRepository.findByIdAndDeletedIsFalse(anyLong()))
                .willReturn(Optional.of(user));

        UserResponse userResponse = userService.getUser(GIVEN_USER_ID);

        assertAll(
                () -> assertThat(userResponse.getId()).isEqualTo(GIVEN_USER_ID),
                () -> assertThat(userResponse.getEmail()).isEqualTo(GIVEN_EMAIL_ADDRESS),
                () -> assertThat(userResponse.getName()).isEqualTo(GIVEN_USER_NAME),
                () -> assertThat(userResponse.isAuth()).isTrue()
        );
    }
}
