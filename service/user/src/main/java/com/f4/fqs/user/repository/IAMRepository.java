package com.f4.fqs.user.repository;

import com.f4.fqs.user.model.IAMUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAMRepository extends JpaRepository<IAMUser, Long> {

    List<IAMUser> findAllByRootUser_Id(Long groupId);

    Optional<IAMUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
