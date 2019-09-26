package com.training.RepAgency.repository;

import com.training.RepAgency.entity.Request;
import com.training.RepAgency.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAll();

    Optional<List<Request>> findByCreatorAndStatusNot(String creator, String status);

    Page<Request> findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Request r set r.status = :status where r.id = :id", nativeQuery = true)
    void updateStatusById(@Param("status") String status,
                                   @Param("id") Long id);
    @Transactional
    @Modifying
    @Query(value = "update Request r set r.status = :status, r.reason=:reason where r.id = :id", nativeQuery = true)
    void updateStatusAndReasonById(@Param("status") String status,
                          @Param("id") Long id, @Param("reason") String reason);

    @Query(value = "SELECT r.* FROM trucking.request r inner join trucking.user u on r.master_id=u.id " +
            "where u.email=:email and r.status=:status",
            nativeQuery = true)
    Page<Request> findByStatusAndEmail(@Param("email")String email,
                                                 @Param("status") String status, Pageable pageable);

    Optional<List<Request>> findByCreatorAndStatus(String creator, String status);
}
