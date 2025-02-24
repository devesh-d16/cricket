package com.devesh.cricket.repository;

import com.devesh.cricket.model.Over;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverRepository extends JpaRepository<Over, Long> {
    List<Over> findDistinctByInning_InningsId(Long inningInningsId);
}
