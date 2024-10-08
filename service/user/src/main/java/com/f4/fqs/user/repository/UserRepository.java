package com.f4.fqs.user.repository;

import com.f4.fqs.user.model.RootUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RootUser, Long> {

    boolean existsByEmail(String email);

    Optional<RootUser> findByEmail(String email);
}
