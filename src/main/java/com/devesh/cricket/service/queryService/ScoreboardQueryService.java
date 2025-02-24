package com.devesh.cricket.service.queryService;

import com.devesh.cricket.dto.PlayerScoreboardDTO;
import com.devesh.cricket.dto.ScoreboardDTO;
import com.devesh.cricket.dto.TeamScoreboardDTO;
import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.PlayerMatchStats;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScoreboardQueryService {
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final MatchRepository matchRepository;


    public ScoreboardDTO findScoreboardByMatchId(Long matchId) {
        ScoreboardDTO scoreboardDTO = new ScoreboardDTO();
        Match match = matchRepository.findById(matchId).orElse(null);
        if(match != null){
            scoreboardDTO.setMatchId(match.getMatchId());
            scoreboardDTO.setTeam1(match.getTeam1());
            scoreboardDTO.setTeam2(match.getTeam2());
            scoreboardDTO.setWinner(match.getWinningTeam().getTeamName());
            scoreboardDTO.setWinningMargin(match.getWinningCondition());
            return scoreboardDTO;
        }
        return null;
    }


    public TeamScoreboardDTO findTeamScoreboardByMatchId(Long teamId, Long matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);

        TeamMatchStats teamMatchStat = null;
        if(match.getTeam1().getTeam().getTeamId().equals(teamId)){
            teamMatchStat = match.getTeam1();
        }
        else if(match.getTeam2().getTeam().getTeamId().equals(teamId)){
            teamMatchStat = match.getTeam2();
        }

        List<PlayerMatchStats> players = teamMatchStat.getPlayers();
        List<PlayerScoreboardDTO> playersDTO = new ArrayList<>();
        for(PlayerMatchStats p : players){
            PlayerScoreboardDTO player = new PlayerScoreboardDTO();
            player.setPlayerName(p.getPlayerName());
            player.setPlayerRole(p.getPlayerRole());
            player.setRunsScored(p.getRunsScored());
            player.setBallsFaced(p.getBallsFaced());
            player.setBallsBowled(p.getBallsBowled());
            player.setRunsConceded(p.getRunsConceded());
            player.setWicketsTaken(p.getWicketsTaken());
            playersDTO.add(player);
        }

        TeamScoreboardDTO teamScoreboardDTO = new TeamScoreboardDTO();
        teamScoreboardDTO.setTeamName(teamMatchStat.getTeamName());
        teamScoreboardDTO.setTotalRuns(teamMatchStat.getTotalRuns());
        teamScoreboardDTO.setTotalWickets(teamMatchStat.getTotalWickets());
        teamScoreboardDTO.setPlayers(playersDTO);
        return teamScoreboardDTO;
    }
}
