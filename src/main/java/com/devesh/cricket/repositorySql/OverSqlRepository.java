package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entity.Over;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverSqlRepository extends JpaRepository<Over, Long> {
}
