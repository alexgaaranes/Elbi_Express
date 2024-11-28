package game;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer{
    private long startTime;
    private long elapsedTime;
    private long endTime;
    boolean isRunning = false;

    public Timer(long endTimeinSec){
        this.endTime = endTimeinSec/1000000000L;
    }

    @Override
    public void handle(long l) {
        if(!isRunning){
            this.startTime = System.nanoTime();
            isRunning = true;
        }

        this.elapsedTime = l - this.startTime;

    }
    // Standalone timer class that keeps track of time on start (Can be use flexibly)

    // Methods
    public long getTimeLeft(){
        return this.endTime - this.elapsedTime;
    }
}

