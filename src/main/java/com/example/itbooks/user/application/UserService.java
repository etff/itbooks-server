package com.example.itbooks.user.application;

import com.example.itbooks.global.utils.CustomPasswordEncoder;
import com.example.itbooks.user.domain.Role;
import com.example.itbooks.user.domain.User;
import com.example.itbooks.user.dto.UserModificationData;
import com.example.itbooks.user.dto.UserRegistrationData;
import com.example.itbooks.user.dto.UserResponse;
import com.example.itbooks.user.infra.RoleRepository;
import com.example.itbooks.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomPasswordEncoder passwordEncoder;

    /**
     * 사용자를 등록하고, 등록된 사용자 식별자를 리턴한다.
     *
     * @param dto 등록할 사용자 정보
     * @return 사용자 식별자
     */
    @Transactional
    public UserResponse registerUser(UserRegistrationData dto) throws EmailDuplicationException {
        final String email = dto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(email);
        }
        final String encodedPassword = passwordEncoder.getEncodedPassword(dto);
        final User user = User.of(dto.getName(), dto.getEmail(), encodedPassword);
        final User saved = userRepository.save(user);
        roleRepository.save(new Role(saved.getId(), "USER"));

        return UserResponse.builder()
                .id(saved.getId())
                .build();
    }

    /**
     * 등록된 사용자 정보를 갱신하고, 생신된 정보를 리턴합니다.
     *
     * @param id               등록된 사용자 식별자
     * @param dto 갱신할 사용자 정보
     * @return 갱신된 사용자 정보
     */
    @Transactional
    public UserResponse updateUser(Long id, UserModificationData dto) throws UserNotFoundException {
        User findUser = findUser(id);
        findUser.changeWith(dto.getName());
        return UserResponse.builder()
                .id(id)
                .name(dto.getName())
                .email(findUser.getEmail())
                .build();
    }

    /**
     * 등록된 사용자를 삭제한다.
     *
     * @param id 등록된 사용자 식별자
     */
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = findUser(id);
        user.destroy();
    }

    /**
     * 사용자의 정보를 리턴한다.
     *
     * @param id 등록된 사용자 식별자
     * @return 등록된 사용자 정보
     */
    public UserResponse getUser(Long id) {
        final User user = findUser(id);
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isAuth(true)
                .build();
    }

    private User findUser(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
