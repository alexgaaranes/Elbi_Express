package game;

import javafx.animation.AnimationTimer;

public class Scoreboard {
    private float happinessLvl;
    private int totalScore;
    private boolean isGameOver = false;

    public static Scoreboard activeScoreboard;

    public static final int MAX_HAPPINESS_LVL = 100;

    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
        this.checkHappiness();
        activeScoreboard = this;
    }

    // Methods
    private void checkHappiness() {
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(isGameOver) {this.stop();}
                if(happinessLvl <= 0) {
                    isGameOver = true;
                }
            }
        }.start();
    }

    public void updateScore(int score) {
        this.totalScore += score;
    }

    public synchronized void reduceHappiness(int val) {
        if(this.happinessLvl <= val) {this.happinessLvl = 0; return;}
        this.happinessLvl -= val;
    }

    public float getHappinessLvl() {
        return this.happinessLvl;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public boolean checkIfLost(){
        return isGameOver;
    }

    public void checkTimeLeft(double timeLeft){
        if(timeLeft > 0) {return;}
        this.isGameOver = true;
    }

}
