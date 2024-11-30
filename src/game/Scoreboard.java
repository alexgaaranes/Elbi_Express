package game;

import graphics.map.Store;
import graphics.vehicles.Vehicle;

import java.util.HashMap;

public class Scoreboard {
    private int happinessLvl = MAX_HAPPINESS_LVL;
    private int totalScore = 0;

    public static final int MAX_HAPPINESS_LVL = 100;

    Scoreboard() {

    }

    // Methods
    public void updateScore(int score) {
        this.totalScore += score;
    }

    public void updateHappinessLvl(int val) {
        if(this.happinessLvl+val <= MAX_HAPPINESS_LVL) this.happinessLvl += val;
        else this.happinessLvl = MAX_HAPPINESS_LVL;
    }

    public int getHappinessLvl() {
        return this.happinessLvl;
    }
    public int getTotalScore() {
        return this.totalScore;
    }
}
