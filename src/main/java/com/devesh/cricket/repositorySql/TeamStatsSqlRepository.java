package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsSqlRepository extends JpaRepository<TeamStats, Long> {
    TeamStats getAllByMatch_MatchId(Long matchMatchId);
}
