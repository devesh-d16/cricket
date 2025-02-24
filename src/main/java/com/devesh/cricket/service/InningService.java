package com.devesh.cricket.service;

import com.devesh.cricket.model.Inning;
import com.devesh.cricket.model.Over;
import com.devesh.cricket.model.PlayerMatchStats;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.repository.InningRepository;
import com.devesh.cricket.repository.OverRepository;
import com.devesh.cricket.utils.StrikePair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InningService {

    private final OverService overService;
    private final GameRulesService gameRulesService;
    private final InningRepository inningRepository;
    private final OverRepository overRepository;

    public void startInnings(Inning inning, TeamMatchStats battingTeam, TeamMatchStats bowlingTeam, int targetRun) {
        int totalOvers = inning.getMatch().getOvers();
        StrikePair strikePair = new StrikePair(battingTeam.getPlayers().get(0), battingTeam.getPlayers().get(1));

        List<Over> overs = new ArrayList<>();
        int bowlerIndex = 0;

        for (int overNumber = 1; overNumber <= totalOvers && !gameRulesService.gameEnd(battingTeam, inning, targetRun); overNumber++) {
            PlayerMatchStats bowler = getNextBowler(bowlingTeam, bowlerIndex);
            Over newOver = createOver(inning, overNumber, bowler);

            overService.simulateOver(inning, newOver, battingTeam, targetRun, strikePair, bowler);

            overs.add(newOver);
            inning.addOvers();
            overRepository.save(newOver);

            bowlerIndex = (bowlerIndex + 1) % bowlingTeam.getBowlers().size();
        }

        inning.setOvers(overs);
        inningRepository.save(inning);
    }

    private PlayerMatchStats getNextBowler(TeamMatchStats bowlingTeam, int index) {
        return bowlingTeam.getBowlers().get(index);
    }

    private Over createOver(Inning inning, int overNumber, PlayerMatchStats bowler) {
        Over over = new Over();
        over.setOverNumber(overNumber);
        over.setInning(inning);
        over.setBowler(bowler);
        return over;
    }

}

