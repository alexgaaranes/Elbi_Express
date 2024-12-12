/**
 * The Timer class is a flexible standalone timer that tracks elapsed time and provides
 * functionality to manage timing-related tasks within the game.
 */

package game;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer {
    private long startTime;
    private long elapsedTime;
    private final long endTime;
    private boolean isRunning = false;

    /**
     * Constructs a Timer with a specified duration.
     *
     * @param endTimeInSec The duration of the timer in seconds.
     */
    public Timer(long endTimeInSec) {
        this.endTime = endTimeInSec * 1_000_000_000L;
    }

    /**
     * Handles the main animation timer logic. Starts the timer, tracks elapsed time,
     * and stops the timer when the end time is reached.
     *
     * @param now The current timestamp in nanoseconds.
     */
    @Override
    public void handle(long now) {
        if(!isRunning){
            this.startTime = System.nanoTime();
            this.isRunning = true;
        }

        if(elapsedTime >= endTime) {
            this.stop();
            this.isRunning = false;
        }

        this.elapsedTime = now - this.startTime;
    }

    /**
     * Retrieves the remaining time in nanoseconds.
     *
     * @return The remaining time as a long value in nanoseconds.
     */
    public long getTime() {
        return this.endTime - this.elapsedTime;
    }

    /**
     * Restarts the timer, resetting the start time to the current time.
     */
    public void restart() {
        this.startTime = System.nanoTime();
    }

    /**
     * Retrieves the remaining time in seconds.
     *
     * @return The remaining time as a long value in seconds.
     */
    public long getTimeSec() {
        return (this.endTime - this.elapsedTime) / 1_000_000_000;
    }

    /**
     * Retrieves the elapsed time in seconds.
     *
     * @return The elapsed time as a long value in seconds.
     */
    public long getElapsedTimeInSec() {
        return this.elapsedTime / 1_000_000_000L;
    }

    /**
     * Retrieves the running status of the timer.
     *
     * @return True if the timer is running, otherwise false.
     */
    public boolean getStatus() {
        return this.isRunning;
    }
}