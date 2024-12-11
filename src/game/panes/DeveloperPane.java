package game.panes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DeveloperPane extends Pane implements gamePane {
    private final Stage stage;
    private Scene parentScene = null;
    private Scene menuScene = null;
    private ImageView background;

    public DeveloperPane(Stage stage) {
        this.stage = stage;

     // Set the initial background
        background = new ImageView(
                new Image(getClass().getResource("/assets/sprites/devs.png").toExternalForm())
        );
        this.getChildren().add(background);

        Font pixelFont = Font.loadFont("file:src/assets/sprites/pixelFont.ttf", 20);
        setUpButtons(pixelFont);
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    public void setButtonScenes(Scene menu) {
        this.menuScene = menu;
    }

    private void setUpButtons(Font font) {
        // Create buttons
        Button backBtn = createStyledButton("BACK", font);

        // Set positions for buttons
        backBtn.setLayoutX(50);
        backBtn.setLayoutY(50);

        // Set button actions
        backBtn.setOnAction(event -> {
            stage.setScene(menuScene);
        });

        // Add buttons to the Pane
        this.getChildren().addAll(backBtn);
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

        // Add hover effect: scale up on hover
        // Hover Effects
        button.setOnMouseEntered(event -> {
            button.setScaleX(1.2);
            button.setScaleY(1.2);
        });
        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        return button;
    }
}
