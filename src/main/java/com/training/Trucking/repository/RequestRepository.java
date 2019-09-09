package com.training.Trucking.repository;

import com.training.Trucking.dto.RequestDTO;
import com.training.Trucking.entity.Request;
import com.training.Trucking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAll();

    Optional<List<Request>> findByCreator(String creator);

    Optional<List<Request>> findByStatus(String status);

    Optional<String> findCreatorById(Long id);


    @Transactional
    @Modifying
    @Query(value = "update Request r set r.status = :status and r.master_id=:master  and r.reason=:reason  and r.price=:price where r.id = :id", nativeQuery = true)
    void updateStatusAndMasterById(@Param("status") String status,
                                   @Param("id") Long id,
                                   @Param("master") User master,
                                   @Param("reason") String reason,
                                   @Param("price") Long price);

    @Transactional
    @Modifying
    @Query(value = "update Request r set r.status = :status where r.id = :id", nativeQuery = true)
    void updateStatusById(@Param("status") String status,
                                   @Param("id") Long id);

    @Query(value = "SELECT r.* FROM trucking.request r inner join trucking.user u on r.master_id=u.id " +
            "where u.email=:email and r.status=:status",
            nativeQuery = true)
    Optional<List<Request>> findByStatusAndEmail(@Param("email")String email,
                                                 @Param("status") String status);
}
