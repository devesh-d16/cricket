package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PlayerStatsSqlRepository extends JpaRepository<PlayerStats, Long> {

}
