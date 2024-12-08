package game.panes;

import graphics.vehicles.Vehicle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectionPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;

    private String vehicles[] = {"Car", "Motorcycle", "Truck"};
    private Vehicle p1, p2;

    public SelectionPane(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;

    }
}
