package game;

import javafx.animation.AnimationTimer;

public class Scoreboard {
    private float happinessLvl;
    private int totalScore;
    private boolean isGameOver = false;

    public static final float MAX_HAPPINESS_LVL = 10F;

    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
        this.checkHappiness();
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

    public void reduceHappiness(float val) {
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
