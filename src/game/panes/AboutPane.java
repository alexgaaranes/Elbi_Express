package game.panes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AboutPane extends Pane implements gamePane {
    private final Stage stage;
    private Scene parentScene = null;
    private Scene menuScene = null;

    private int currentInstructionIndex = 1; // Track current instruction page
    private final int maxInstructionIndex = 4; // Total number of instructions

    private Button leftArrowBtn;
    private Button rightArrowBtn;

    public AboutPane(Stage stage) {
        this.stage = stage;

        // Set the initial background
        updateBackground();

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
        leftArrowBtn = createStyledButton("<", font);
        rightArrowBtn = createStyledButton(">", font);

        // Set positions for buttons
        backBtn.setLayoutX(50);
        backBtn.setLayoutY(50);

        leftArrowBtn.setLayoutX(30);
        leftArrowBtn.setLayoutY(511);

        rightArrowBtn.setLayoutX(1200);
        rightArrowBtn.setLayoutY(511);

        // Set button actions
        backBtn.setOnAction(event -> stage.setScene(menuScene));
        leftArrowBtn.setOnAction(event -> changeInstruction(-1));
        rightArrowBtn.setOnAction(event -> changeInstruction(1));

        // Add buttons to the Pane
        this.getChildren().addAll(backBtn, leftArrowBtn, rightArrowBtn);

        // Update button visibility initially
        updateArrowButtons();
    }

    private void changeInstruction(int direction) {
        currentInstructionIndex += direction;
        updateBackground();
        updateArrowButtons();
    }

    private void updateBackground() {
        this.setStyle(
            "-fx-background-image: url('file:src/assets/sprites/instruction" + currentInstructionIndex + ".png');" +
            "-fx-background-size: cover;" +
            "-fx-background-position: center;"
        );
    }

    private void updateArrowButtons() {
        leftArrowBtn.setVisible(currentInstructionIndex > 1);
        rightArrowBtn.setVisible(currentInstructionIndex < maxInstructionIndex);
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
