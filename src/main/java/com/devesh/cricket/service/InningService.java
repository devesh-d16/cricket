package com.devesh.cricket.service;

import com.devesh.cricket.model.Inning;
import com.devesh.cricket.model.Over;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.repository.InningRepository;
import com.devesh.cricket.repository.OverRepository;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InningService {

    private final OverService overService;
    private final GameLogicService gameLogicService;
    private final InningRepository inningRepository;
    private final OverRepository overRepository;

    public InningService(OverService overService, GameLogicService gameLogicService, InningRepository inningRepository, OverRepository overRepository) {
        this.overService = overService;
        this.gameLogicService = gameLogicService;
        this.inningRepository = inningRepository;
        this.overRepository = overRepository;
    }


    public void startInnings(Inning inning, TeamMatchStats battingTeam, TeamMatchStats bowlingTeam, int targetRun) {

        int totalOvers = inning.getMatch().getOvers();
        StrikePair strikePair = new StrikePair(battingTeam.getPlayers().get(0), battingTeam.getPlayers().get(1));

        List<Over> overList = new ArrayList<>();
        for (int over = 1; over <= totalOvers && (!gameLogicService.gameEnd(battingTeam, inning, targetRun)); over++) {
            Over newOver = new Over();
            newOver.setOverNumber(over);
            newOver.setInning(inning);
            overRepository.save(newOver);

            overService.simulateOver(inning, newOver, battingTeam, targetRun, strikePair);

            overList.add(newOver);
            inning.addOvers();
        }

        inning.setOvers(overList);
        inningRepository.save(inning);
    }

    public List<Inning> getAllInnings() {
        return inningRepository.findAll();
    }

    public Inning getInningById(Long inningId) {
        return inningRepository.findById(inningId).orElse(null);
    }

    public List<Inning> getInningsByMatch(Long matchId) {
        return inningRepository.getAllByMatch_MatchId(matchId);
    }
}
