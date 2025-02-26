package com.devesh.cricket.repository;

import com.devesh.cricket.entity.Over;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverRepository extends JpaRepository<Over, Long> {
    List<Over> findOversByInning_Id(Long inningId);
}
