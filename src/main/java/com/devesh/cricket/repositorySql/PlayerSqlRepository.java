package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.Player;
import com.devesh.cricket.enums.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerSqlRepository extends JpaRepository<Player, Long> {

    boolean existsByPlayerName(String name);

    Player getPlayerByPlayerName(String name);

    List<Player> findAllByPlayerRole(PlayerRole role);

    Player getPlayerSqlByPlayerName(String playerName);
}
