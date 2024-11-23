package game.panes;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPane extends VBox implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;

    public MenuPane(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
