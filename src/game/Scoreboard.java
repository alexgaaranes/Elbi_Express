/**
 * The Scoreboard class manages the player's score and happiness level during the game.
 * It continuously monitors the happiness level and determines game-over conditions.
 */

package game;

import javafx.animation.AnimationTimer;

public class Scoreboard {
    private float happinessLvl;
    private int totalScore;
    private boolean isGameOver = false;

    //Static instance of the active scoreboard for global access
    public static Scoreboard activeScoreboard;

    //Maximum happiness level constant
    public static final int MAX_HAPPINESS_LVL = 100;

    /**
     * Constructor for the Scoreboard class.
     * Initializes the happiness level and total score, and starts monitoring happiness.
     */
    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
        this.checkHappiness();
        activeScoreboard = this;
    }

    /**
     * Monitors the happiness level in real-time using an AnimationTimer.
     * Ends the game if the happiness level drops to zero or below.
     */
    private void checkHappiness() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(isGameOver){
                    this.stop();
                }
                if(happinessLvl <= 0){
                    isGameOver = true;
                }
            }
        }.start();
    }

    /**
     * Updates the player's total score by adding the specified value.
     *
     * @param score The value to be added to the total score.
     */
    public void updateScore(int score) {
        this.totalScore += score;
    }

    /**
     * Reduces the player's happiness level by the specified value.
     * If the reduction exceeds the current happiness level, it sets the level to zero.
     *
     * @param val The value to reduce the happiness level by.
     */
    public synchronized void reduceHappiness(int val) {
        if(this.happinessLvl <= val){
            this.happinessLvl = 0;
            return;
        }
        this.happinessLvl -= val;
    }

    /**
     * Retrieves the current happiness level.
     *
     * @return The current happiness level as a float.
     */
    public float getHappinessLvl() {
        return this.happinessLvl;
    }

    /**
     * Retrieves the player's total score.
     *
     * @return The total score as an integer.
     */
    public int getTotalScore() {
        return this.totalScore;
    }

    /**
     * Checks whether the game has been lost.
     *
     * @return True if the game is over, otherwise false.
     */
    public boolean checkIfLost() {
        return isGameOver;
    }

    /**
     * Ends the game if the specified time left is zero or below.
     *
     * @param timeLeft The remaining time in seconds.
     */
    public void checkTimeLeft(double timeLeft) {
        if(timeLeft > 0) {
            return;
        }
        this.isGameOver = true;
    }
}
