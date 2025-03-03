package com.devesh.cricket.utils;

import java.util.List;

public class GameUtil {

    public static final int MAX_PLAYERS = 11;
    public static final int BALLS_PER_OVER = 6;
    public static final List<Integer> SCORING_OPTIONS = List.of(0, 1, 2, 3, 4, 6, -1);
    public static final List<Integer> BATTER_WEIGHT = List.of(20, 25, 18, 8, 14, 10, 8);
    public static final List<Integer> BOWLER_WEIGHT = List.of(18, 22, 12, 5, 10, 6, 20);
}


//    private final Map<String, BallRepository> ballRepositoryMap;
//
//    public OverService(BallService ballService, GameRulesService gameRulesService, List<BallRepository> ballRepository) {
//        this.ballService = ballService;
//        this.gameRulesService = gameRulesService;
//        this.ballRepositoryMap = buildMap(ballRepository);
//    }
//
//    private Map<String, BallRepository> buildMap(List<BallRepository> ballRepository) {
//        Map<String, BallRepository> ballRepositoryMap = new HashMap<>();
//        for(BallRepository ballRepository1 : ballRepository) {
//            ballRepositoryMap.put(ballRepository1.getRepoType(), ballRepository1);
//        }
//        return ballRepositoryMap;
//    }
