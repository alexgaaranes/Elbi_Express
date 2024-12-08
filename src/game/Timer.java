package game;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer{
    private long startTime;
    private long elapsedTime;
    private final long endTime;
    private boolean isRunning = false;

    public Timer(long endTimeInSec){
        this.endTime = endTimeInSec*1000000000L;
    }

    @Override
    public void handle(long l) {
        if(!isRunning){
            this.startTime = System.nanoTime();
            this.isRunning = true;
        }

        if(elapsedTime >= endTime){
            this.stop();
            this.isRunning = false;
        }

        this.elapsedTime = l - this.startTime;
    }
    // Standalone timer class that keeps track of time on start (Can be use flexibly)

    // Methods
    public long getTime(){
        return this.endTime - this.elapsedTime;
    }

    public void restart(){
        this.startTime = System.nanoTime();
    }

    public long getTimeSec(){
        return (this.endTime - this.elapsedTime)/1000000000;
    }

    public long getElapsedTimeInSec(){
        return this.elapsedTime/1000000000L;
    }

    public boolean getStatus(){
        return this.isRunning;
    }
}

