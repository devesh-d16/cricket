package com.devesh.cricket.utils;

import com.devesh.cricket.dto.*;
import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.PlayerStats;
import com.devesh.cricket.entity.TeamStats;
import com.devesh.cricket.enums.PlayerRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@RequiredArgsConstructor
@Component
public class Mapper {

    public List<MatchResponseDTO> convertToList(List<Match> matches){
        return matches.stream().map(this::getMatchResponse).collect(Collectors.toList());
    }

    public MatchResponseDTO getMatchResponse(Match match) {
        MatchResponseDTO dto = new MatchResponseDTO();
        dto.setTitle(match.getTeam1().getTeam().getTeamName() + " v/s " + match.getTeam2().getTeam().getTeamName());
        dto.setVenue(match.getVenue());
        dto.setOvers(match.getOvers());
        dto.setResult(match.getWinningCondition());
        dto.setTeam1(convertToTeamDTO(match.getTeam1()));
        dto.setTeam2(convertToTeamDTO(match.getTeam2()));
        return dto;
    }

    public TeamResponseDTO convertToTeamDTO(TeamStats team) {
        if (team == null) return null; // Avoid NullPointerException
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setTeamName(team.getTeam().getTeamName());
        dto.setRuns(team.getRuns());
        dto.setWickets(team.getWickets());
        dto.setOvers(team.getOvers());
        return dto;
    }

    public InningsDTO convertToInningsDTO(Inning innings){
        InningsDTO inningsDTO = new InningsDTO();
        inningsDTO.setBattingTeam(innings.getBattingTeam().getTeam().getTeamName());
        inningsDTO.setBowlingTeam(innings.getBowlingTeam().getTeam().getTeamName());
        inningsDTO.setRuns(innings.getRuns());
        inningsDTO.setWickets(innings.getWickets());
        inningsDTO.setOvers(innings.getOvers());
        inningsDTO.setScoreboardDTO(convertToScoreboardDTO(innings));
        return inningsDTO;
    }

    public ScoreboardDTO convertToScoreboardDTO(Inning innings) {
        ScoreboardDTO scoreboardDTO = new ScoreboardDTO();
        List<BattingStatsDTO> batterStats = convertToBattingStatsDTO(innings.getBattingTeam());
        List<BowlingStatsDTO> bowlerStats = convertToBowlingStatsDTO(innings.getBowlingTeam());
        scoreboardDTO.setBattersStats(batterStats);
        scoreboardDTO.setBowlerStats(bowlerStats);
        return scoreboardDTO;
    }

    public List<BowlingStatsDTO> convertToBowlingStatsDTO(TeamStats bowlingTeam) {
        List<BowlingStatsDTO> bowl =  new ArrayList<>();
        List<PlayerStats> players = bowlingTeam.getPlayers();
        for(PlayerStats bowler : players){
            if(bowler.getPlayer().getPlayerRole() == PlayerRole.BOWLER) {
                BowlingStatsDTO bowlingStatsDTO = new BowlingStatsDTO();
                bowlingStatsDTO.setName(bowler.getPlayer().getPlayerName());
                bowlingStatsDTO.setOversBowled((bowler.getBallsBowled())/6);
                bowlingStatsDTO.setRunsConceded(bowler.getRunsConceded());
                bowlingStatsDTO.setWicketsTaken(bowler.getWicketsTaken());
                bowl.add(bowlingStatsDTO);
            }
        }
        return bowl;
    }

    public List<BattingStatsDTO> convertToBattingStatsDTO(TeamStats battingTeam) {
        List<PlayerStats> players = battingTeam.getPlayers();
        List<BattingStatsDTO> bat = new ArrayList<>();
        for(PlayerStats batter : players){
            BattingStatsDTO battingStatsDTO = new BattingStatsDTO();
            battingStatsDTO.setName(batter.getPlayer().getPlayerName());
            battingStatsDTO.setRunsScored(batter.getRunsScored());
            battingStatsDTO.setBallsFaced(batter.getBallsFaced());
            bat.add(battingStatsDTO);
        }
        return bat;
    }

}
