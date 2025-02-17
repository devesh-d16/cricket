package com.devesh.cricket.repository;

import com.devesh.cricket.model.Over;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverRepository extends JpaRepository<Over, Long> {
}
