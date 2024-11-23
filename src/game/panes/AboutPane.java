package game.panes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;

    public AboutPane(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
