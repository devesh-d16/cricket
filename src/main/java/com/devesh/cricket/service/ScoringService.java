package com.devesh.cricket.service;


import com.devesh.cricket.config.GameConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class ScoringService {
    private static final Random random = new Random();
    private static List<Integer> generateWeightedList(int[] weights) {
        List<Integer> weightedList = new ArrayList<>();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i]; j++) {
                weightedList.add(GameConfig.SCORING_OPTIONS[i]);
            }
        }
        return weightedList;
    }

    private static final List<Integer> BATTER_SCORE_LIST = generateWeightedList(GameConfig.BATTER_WEIGHT);
    private static final List<Integer> BOWLER_SCORE_LIST = generateWeightedList(GameConfig.BOWLER_WEIGHT);

    public int getRandomBatterWeightScore() {
        return BATTER_SCORE_LIST.get(random.nextInt(BATTER_SCORE_LIST.size()));
    }

    public int getRandomBowlerWeightScore() {
        return BOWLER_SCORE_LIST.get(random.nextInt(BOWLER_SCORE_LIST.size()));
    }
}
