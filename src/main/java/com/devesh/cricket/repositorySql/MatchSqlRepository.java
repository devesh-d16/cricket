package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.Match;
import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.entitySql.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchSqlRepository extends JpaRepository<Match, Long> {

    Match getMatchByMatchId(Long id);

    List<Match> getMatchesByMatchStatus(MatchStatus matchStatus);

    List<Match> getMatchesByWinner_Team(Team winnerTeam);

    List<Match> getMatchesByVenue(String venue);
}
