package com.devesh.cricket.service;

import com.devesh.cricket.model.Inning;
import com.devesh.cricket.model.Over;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.repository.InningRepository;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InningService {

    private final OverService overService;
    private final GameLogicService gameLogicService;
    private final InningRepository inningRepository;

    public InningService(OverService overService, GameLogicService gameLogicService, InningRepository inningRepository) {
        this.overService = overService;
        this.gameLogicService = gameLogicService;
        this.inningRepository = inningRepository;
    }

    public void startInnings(Inning inning, Team battingTeam, Team bowlingTeam, int targetRun) {
        inning.setBattingTeam(battingTeam);
        inning.setBowlingTeam(bowlingTeam);
        inningRepository.save(inning);

        int overs = inning.getMatch().getOvers();
        StrikePair strikePair = new StrikePair(battingTeam.getPlayers().get(0), battingTeam.getPlayers().get(1));

        List<Over> overList = new ArrayList<>();
        for (int overNo = 1; overNo <= overs && (!gameLogicService.gameEnd(battingTeam, inning, targetRun)); overNo++) {
            Over over = new Over();
            over.setInning(inning);
            over.setOverNumber(overNo);

            overService.simulateOver(inning, over, battingTeam, targetRun, strikePair);
            overList.add(over);

            inning.addRuns(over.getRunsScored());
            inning.addWickets(over.getWicketsInTheOver());
            inning.addOvers();
        }

        inning.setOvers(overList);
        inningRepository.save(inning);
    }

}
