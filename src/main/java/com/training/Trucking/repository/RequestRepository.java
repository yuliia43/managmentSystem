package com.training.Trucking.repository;

import com.training.Trucking.dto.RequestDTO;
import com.training.Trucking.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<List<Request>> findByCreator(String creator);

    Optional<List<Request>> findByStatus(String status);

    @Modifying
    @Query(value = "update Request r set r.status = :status and r.master_id=:master  and r.reason=:reason  and r.price=:price where r.id = :id", nativeQuery = true)
    void updateStatusAndMasterById(@Param("status") String status,
                                   @Param("id") Long id,
                                   @Param("master")Long master_id,
                                   @Param("reason") String reason,
                                   @Param("price") Long price);
}
