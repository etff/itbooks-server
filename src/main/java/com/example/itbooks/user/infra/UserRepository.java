package com.example.itbooks.user.infra;

import com.example.itbooks.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deleted = false ")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    Optional<User> findByIdAndDeletedIsFalse(Long id);
}
