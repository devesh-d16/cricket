package com.devesh.cricket.repository;

import com.devesh.cricket.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {


    Tournament findByTournamentName(String tournamentName);
}
