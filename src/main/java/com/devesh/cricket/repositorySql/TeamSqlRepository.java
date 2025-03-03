package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamSqlRepository extends JpaRepository<Team, Long> {

    Team findTeamByTeamName(String teamName);
    boolean existsByTeamName(String teamName);
}