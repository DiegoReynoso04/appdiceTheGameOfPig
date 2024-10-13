package com.example.appdice;


import java.util.HashMap;



public class Dice {



    private final HashMap<String, Integer> players = new HashMap<>();

    private boolean hold = false;

    private static int scoreRound = 0;



    public Dice() {

        players.put("p1", 0);

        players.put("p2", 0);

    }



    public static int roll() {

        return (int) (Math.random() * 6) + 1;

    }



    public int play() {
        int number = roll();
        if (number != 1) {
            scoreRound += number;
        } else {
            this.setHold(this.invertedHold());
            scoreRound = 0;
        }
        return number;
    }



    public String winner(){

        String winner = "";

        if (players.get("p1") >= 100) {

            winner = "P1";

        } else if (players.get("p2") >= 100) {

            winner = "P2";

        }

        return winner;

    }



    public void deleteGame(){

        players.put("p1", 0);

        players.put("p2", 0);

        scoreRound = 0;

    }



    public static int getScoreRound() {

        return scoreRound;

    }



    public int getPoints(String player){

        Integer points = players.get(player);

        return points != null ? points : 0;

    }



    public void addDice(String player, int roll){

        players.merge(player, roll, Integer::sum);

    }



    public void setHold(boolean hold) {

        this.hold = hold;

    }



    public void setScoreRound(int currentRound) {

        scoreRound = currentRound;

    }



    public boolean invertedHold() {

        return !hold;

    }



}