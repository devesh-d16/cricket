package com.devesh.cricket.service;

import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

    public Result evaluateResult(Inning firstInnings, Inning secondInnings) {
        Result result = new Result();

        int runsScoredByTeam1 = firstInnings.getRuns();
        int runsScoredByTeam2 = secondInnings.getRuns();

        if(runsScoredByTeam1 > runsScoredByTeam2){
            int runs = runsScoredByTeam1 - runsScoredByTeam2;
            result.setWinner(firstInnings.getBattingTeam());
            result.setWinningMargin(runs);
            result.setWinningCondition(firstInnings.getBattingTeam().getName() + " won the game by " + runs + " runs.");
        }
        else if(runsScoredByTeam1 < runsScoredByTeam2){
            int wicketsMargin = 10 - secondInnings.getWickets();
            result.setWinner(secondInnings.getBattingTeam());
            result.setWinningMargin(wicketsMargin);
            result.setWinningCondition(secondInnings.getBattingTeam().getName()+ " won the game by " + wicketsMargin + " wickets.");
        }
        else{
            result.setWinner(null);
            result.setWinningMargin(0);
            result.setWinningCondition("Match drawn");
        }

        return result;
    }
}
