/*
 *  MUST CONTAIN MAIN GAME LOOP
 */

package game;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer {
    private final Stage stage;

    public GameTimer(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(long l) {

    }
}
