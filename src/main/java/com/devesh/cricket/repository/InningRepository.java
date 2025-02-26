package com.devesh.cricket.repository;

import com.devesh.cricket.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InningRepository extends JpaRepository<Inning, Long> {

    List<Inning> getAllByMatch_Id(Long matchId);
}
