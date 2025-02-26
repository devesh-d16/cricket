package com.devesh.cricket.repository;

import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Match getMatchById(Long id);

    List<Match> getMatchesByMatchStatus(MatchStatus matchStatus);

    List<Match> getMatchesByWinner_Team(Team winnerTeam);
}
