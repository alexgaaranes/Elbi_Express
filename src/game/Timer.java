package game;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer{
    private long startTime;

    @Override
    public void handle(long l) {

    }
    // Standalone timer class that keeps track of time on start (Can be use flexibly)

    // Methods
    public void startTimer(){
        this.startTime = System.nanoTime();
    }
}
