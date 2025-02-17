package com.devesh.cricket.repository;

import com.devesh.cricket.model.Inning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InningRepository extends JpaRepository<Inning, Long> {
}
