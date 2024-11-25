package game.panes;

import game.GameTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;
    private GameTimer gameTimer;

    public PlayPane(Stage stage) {
        this.stage = stage;
        gameTimer = new GameTimer(stage);
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
