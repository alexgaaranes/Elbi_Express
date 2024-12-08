package game.panes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuPane extends Pane implements gamePane {
    private final Stage stage;
    private Scene parentScene = null;
    private Scene developerScene = null;
    private Scene aboutScene = null;
    private Scene selectionScene = null;

    public MenuPane(Stage stage) {
        this.stage = stage;

        // Set the background
        this.setStyle(
            "-fx-background-image: url('file:src/assets/sprites/menu.png');" +
            "-fx-background-size: cover;" +
            "-fx-background-position: center;"
        );

        Font pixelFont = Font.loadFont("file:src/assets/sprites/pixelFont.ttf", 20);

        setUpButtons(pixelFont);
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    public void setButtonScenes(Scene devScene, Scene abScene, Scene selectionScene) {
        this.developerScene = devScene;
        this.aboutScene = abScene;
        this.selectionScene = selectionScene;
    }

    private void setUpButtons(Font font) {
        // Create buttons
        Button playBtn = createStyledButton("PLAY", font);
        Button developerBtn = createStyledButton("DEVELOPERS", font);
        Button aboutBtn = createStyledButton("INSTRUCTIONS", font);

        // Set positions for buttons
        playBtn.setLayoutX(590);
        playBtn.setLayoutY(750);

        developerBtn.setLayoutX(300);
        developerBtn.setLayoutY(750); 

        aboutBtn.setLayoutX(780);
        aboutBtn.setLayoutY(750);

        // Set button actions
        playBtn.setOnAction(event -> {
            stage.setScene(selectionScene);
            SelectionPane selectionPane = (SelectionPane) selectionScene.getRoot();
            selectionPane.setUpSelection();
        });

        developerBtn.setOnAction(event -> {
            stage.setScene(developerScene);
        });

        aboutBtn.setOnAction(event -> {
            stage.setScene(aboutScene);
        });

        // Add buttons to the Pane
        this.getChildren().addAll(playBtn, developerBtn, aboutBtn);
    }

    private Button createStyledButton(String text, Font font) {
        Button button = new Button(text);
        button.setFont(font);
        button.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);" +
            "-fx-text-fill: black;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 4px;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 8 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 4, 0.5, 2, 2);"
        );
        return button;
    }
}
