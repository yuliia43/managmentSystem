package com.training.RepAgency.repository;

import com.training.RepAgency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void updatePasswordById(String password, Long userId);

    @Query(value = "SELECT user.* from user right join  user_roles on user.id=user_roles.user_id inner join role on user_roles.roles_id=role.id where role.name=:role",
    nativeQuery = true)
    Optional<List<User>>findByRole(@Param("role")String role);

    Optional<Long>findIdByEmail(String email);


}


