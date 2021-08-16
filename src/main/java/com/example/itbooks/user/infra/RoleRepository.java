package com.example.itbooks.user.infra;

import com.example.itbooks.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 권한 정보 저장소.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByUserId(Long userId);
}
