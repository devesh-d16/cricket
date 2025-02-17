package com.devesh.cricket.service;

import com.devesh.cricket.dto.ResultDTO;
import com.devesh.cricket.model.Inning;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

    public ResultDTO evaluateResult(Inning firstInnings, Inning secondInnings) {
        ResultDTO resultDTO = new ResultDTO();
        int runsScoredByTeam1 = firstInnings.getTotalRuns();
        int runsScoredByTeam2 = secondInnings.getTotalRuns();

        if(runsScoredByTeam1 > runsScoredByTeam2){
            int runs = runsScoredByTeam1 - runsScoredByTeam2;
            resultDTO.setWinningTeam(firstInnings.getBattingTeam());
            resultDTO.setWinningMargin(runs);
            resultDTO.setWinningCondition(firstInnings.getBattingTeam().getTeamName() + " won the game by " + String.valueOf(runs) + " runs.");
        }
        else if(runsScoredByTeam1 < runsScoredByTeam2){
            int wickets = secondInnings.getBattingTeam().getTotalWickets();
            resultDTO.setWinningTeam(secondInnings.getBattingTeam());
            resultDTO.setWinningMargin(10 - wickets);
            resultDTO.setWinningCondition(secondInnings.getBattingTeam().getTeamName() + " won the game by " + String.valueOf(wickets) + " wickets.");
        }
        else{
            resultDTO.setWinningTeam(null);
            resultDTO.setWinningMargin(0);
            resultDTO.setWinningCondition("Match drawn");
        }

        return resultDTO;
    }
}
