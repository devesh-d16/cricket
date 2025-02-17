package com.devesh.cricket.ui;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.Player;
import com.devesh.cricket.model.enums.PlayerRole;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.model.enums.Toss;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UI {
    private final Scanner sc = new Scanner(System.in);

    public void displayWelcomeMessage(){
        System.out.println("====================================");
        System.out.println("-------------Welcome----------------");
        System.out.println("----------------To------------------");
        System.out.println("--------Java Premiere League--------");
        System.out.println("====================================");
    }

    public String inputTeamName() {
        System.out.print("Please enter team's name: ");
        return sc.nextLine();
    }

    public Player inputPlayer(int playerNumber){
        System.out.println("Enter details for Player " + playerNumber + ":");
        System.out.print("Name: ");
        String name = sc.nextLine();

        PlayerRole playerRole = null;
        while(playerRole == null){
            System.out.print("Designation (Bat/Bowl): ");
            try{
                playerRole = PlayerRole.inputDesignation(sc.nextLine());
            }catch(IllegalArgumentException e){
                System.err.println(e.getMessage());
            }
        }
        Player p =  new Player();
        p.setPlayerName(name);
        p.setPlayerRole(playerRole);
        return p;
    }

    public void displayInningsEndMessage(Team team) {
        int runs = team.getTotalRuns();
        int wickets = team.getTotalWickets();

        System.out.println();
        System.out.println("Runs scored by " + team.getTeamName() + " : " + runs + "/" + wickets);
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("--------------------------Innings Over-------------------------");
        System.out.println("---------------------------------------------------------------");
        System.out.println();
    }


    public void displayMatchResult(Team teamWon, Team teamLost, int won) {
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        if(won == 0){
            int difference = teamWon.getTotalRuns() - teamLost.getTotalRuns();
            System.out.println(teamWon.getTeamName() + " won by " + difference + " runs.");
        }
        else{
            int difference = 10 - teamWon.getTotalWickets();
            System.out.println(teamWon.getTeamName() + " won by " + difference + " wickets.");
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println();
    }

    public void displayRunByBall(int run){
        switch(run) {
            case -1:
                System.out.print("W ");
                break;
            case 0:
                System.out.print(". ");
                break;
            default:
                System.out.print(run + " ");
        }
    }

    public void displayOverStat(int overNo, int runThisOver){
        System.out.println();
        System.out.println("Runs scored in over " + overNo + ": " + runThisOver);
    }

    public int inputOvers() {
        int overs = -1;
        while(overs < GameConfig.MIN_OVERS || overs > GameConfig.MAX_OVERS){
            System.out.println("Enter the number of overs (1-100): ");
            try{
                overs = Integer.parseInt(sc.nextLine());
                if(overs < GameConfig.MIN_OVERS  || overs > GameConfig.MAX_OVERS ){
                    System.err.println("Invalid input! Please enter a value between 1 and 100");
                }
            }
            catch(NumberFormatException e){
                System.err.println("Invalid input! Please enter a valid number");
            }
        }
        return overs;
    }

    public Toss inputToss() {
        Toss tossResult = null;
        while(tossResult == null){
            System.out.print("Toss Time! Chose Heads or Tails (H/T):");
            try{
                tossResult = Toss.inputToss(sc.nextLine());
            }
            catch (IllegalArgumentException e){
                System.err.println(e.getMessage());
            }
        }
        return tossResult;
    }
}
