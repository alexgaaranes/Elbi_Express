package game.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPane extends VBox implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;
    private Scene developerScene = null;
    private Scene aboutScene = null;
    private Scene playScene = null;

    public MenuPane(Stage stage, double spacing) {
        super(spacing);
        this.stage = stage;
        this.setAlignment(Pos.CENTER_LEFT);

       setUpButtons();
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    public void setButtonScenes(Scene devScene, Scene abScene, Scene playScene){
        this.developerScene = devScene;
        this.aboutScene = abScene;
        this.playScene = playScene;
    }

    private void setUpButtons(){
        Button playBtn = new Button("Play");
        Button developerBtn = new Button("Developers");
        Button aboutBtn = new Button("About");

        playBtn.setOnAction(event ->{
            stage.setScene(playScene);
        });

        developerBtn.setOnAction(event ->{
            stage.setScene(developerScene);
        });

        aboutBtn.setOnAction(event ->{
            stage.setScene(aboutScene);
        });

        this.getChildren().addAll(playBtn, developerBtn, aboutBtn);
    }
}
