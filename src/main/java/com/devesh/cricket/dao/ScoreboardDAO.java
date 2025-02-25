package com.devesh.cricket.dao;

import com.devesh.cricket.dto.PlayerScoreboardDTO;
import com.devesh.cricket.dto.ScoreboardDTO;
import com.devesh.cricket.dto.TeamScoreboardDTO;
import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.PlayerMatchStats;
import com.devesh.cricket.entity.TeamMatchStats;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScoreboardDAO {
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final MatchRepository matchRepository;


    public ScoreboardDTO findScoreboardByMatchId(Long matchId) {
        ScoreboardDTO scoreboardDTO = new ScoreboardDTO();
        Match match = matchRepository.findById(matchId).orElse(null);
        if(match != null){
            scoreboardDTO.setMatchId(match.getId());
            scoreboardDTO.setTeam1(match.getTeam1());
            scoreboardDTO.setTeam2(match.getTeam2());
            scoreboardDTO.setWinner(match.getWinner().getName());
            scoreboardDTO.setWinningMargin(match.getWinningCondition());
            return scoreboardDTO;
        }
        return null;
    }


    public TeamScoreboardDTO findTeamScoreboardByMatchId(Long teamId, Long matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);

        TeamMatchStats teamMatchStat = null;
        if(match.getTeam1().getTeam().getId().equals(teamId)){
            teamMatchStat = match.getTeam1();
        }
        else if(match.getTeam2().getTeam().getId().equals(teamId)){
            teamMatchStat = match.getTeam2();
        }

        List<PlayerMatchStats> players = teamMatchStat.getPlayers();
        List<PlayerScoreboardDTO> playersDTO = new ArrayList<>();
        for(PlayerMatchStats p : players){
            PlayerScoreboardDTO player = new PlayerScoreboardDTO();
            player.setPlayerName(p.getName());
            player.setPlayerRole(p.getPlayerRole());
            player.setRunsScored(p.getRunsScored());
            player.setBallsFaced(p.getBallsFaced());
            player.setBallsBowled(p.getBallsBowled());
            player.setRunsConceded(p.getRunsConceded());
            player.setWicketsTaken(p.getWicketsTaken());
            playersDTO.add(player);
        }

        TeamScoreboardDTO teamScoreboardDTO = new TeamScoreboardDTO();
        teamScoreboardDTO.setTeamName(teamMatchStat.getName());
        teamScoreboardDTO.setTotalRuns(teamMatchStat.getRuns());
        teamScoreboardDTO.setTotalWickets(teamMatchStat.getWickets());
        teamScoreboardDTO.setPlayers(playersDTO);
        return teamScoreboardDTO;
    }
}
