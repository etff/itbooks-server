package com.example.itbooks.user.application;

import com.example.itbooks.global.utils.CustomPasswordEncoder;
import com.example.itbooks.user.domain.Role;
import com.example.itbooks.user.domain.User;
import com.example.itbooks.user.dto.UserRegistrationData;
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
    public Long registerUser(UserRegistrationData dto) {
        final String email = dto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(email);
        }
        final String encodedPassword = passwordEncoder.getEncodedPassword(dto);
        final User user = User.of(dto.getName(), dto.getEmail(), encodedPassword);
        final User saved = userRepository.save(user);
        roleRepository.save(new Role(saved.getId(), "USER"));

        return saved.getId();
    }
}
