/**
 * AboutPane is responsible for displaying instructional content to the user.
 * It includes navigation controls to browse through instruction pages and a back button to return to the main menu.
 */

package game.panes;

import game.Audio;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AboutPane extends Pane implements gamePane {
    //Stage reference to switch between scenes
    private final Stage stage;

    //References to other scenes for navigation
    private Scene parentScene = null;

    //Instruction page tracking
    private int currentInstructionIndex = 1; //Tracks the current instruction page index
    private final int maxInstructionIndex = 4; //Total number of instruction pages available

    //UI Components
    private Button leftArrowBtn; //Button to navigate to the previous instruction page
    private Button rightArrowBtn; //Button to navigate to the next instruction page
    private ImageView background; //Background image displaying the current instruction

    /**
     * Constructs an AboutPane instance with a reference to the main application stage.
     *
     * @param stage The main application stage.
     */
    public AboutPane(Stage stage) {
        this.stage = stage;

        //Initialize the background with the first instruction page
        background = new ImageView(
                new Image(getClass().getResource("/assets/sprites/instruction" + currentInstructionIndex + ".png").toExternalForm())
        );
        this.getChildren().add(background);
        updateBackground();

        //Load and set up a pixel font for UI elements
        Font pixelFont = Font.loadFont(getClass().getResource("/assets/sprites/pixelFont.ttf").toExternalForm(), 20);
        setUpButtons(pixelFont);
    }

    /**
     * Sets the parent scene for this pane.
     *
     * @param scene The parent scene to be set.
     */
    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    /**
     * Configures the scene to be loaded when the back button is clicked.
     *
     * @param menu The menu scene to navigate back to.
     */

    /**
     * Sets up the UI buttons for navigation and assigns their actions.
     * Buttons are styled, positioned, given an audio cue and linked to corresponding actions for navigation.
     *
     * @param font The font to be used for button text.
     */
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
        backBtn.setOnAction(event -> {
            Audio.playClip("menuButton", 1.5);
            stage.setScene(parentScene);
        });
        leftArrowBtn.setOnAction(event -> {
            Audio.playClip("menuButton", 1.5);
            changeInstruction(-1);
        }
        );
        rightArrowBtn.setOnAction(event -> {
            Audio.playClip("menuButton", 1.5);
            changeInstruction(1);
        });

        // Add buttons to the pane
        this.getChildren().addAll(backBtn, leftArrowBtn, rightArrowBtn);

        // Update button visibility initially
        updateArrowButtons();
    }

    /**
     * Changes the current instruction page by a specified direction.
     *
     * @param direction -1 to navigate backward, 1 to navigate forward.
     */
    private void changeInstruction(int direction) {
        currentInstructionIndex += direction;
        updateBackground();
        updateArrowButtons();
    }

    /**
     * Updates the background image to reflect the current instruction page.
     */
    private void updateBackground() {
        background.setImage(new Image(getClass().getResource("/assets/sprites/instruction" + currentInstructionIndex + ".png").toExternalForm()));
    }

    /**
     * Updates the visibility of the navigation buttons based on the current instruction page.
     */
    private void updateArrowButtons() {
        leftArrowBtn.setVisible(currentInstructionIndex > 1);
        rightArrowBtn.setVisible(currentInstructionIndex < maxInstructionIndex);
    }

    /**
     * Creates a styled button with a consistent appearance and hover effect.
     *
     * @param text The text to display on the button.
     * @param font The font to use for the button text.
     * @return A styled Button instance.
     */
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

        //Add hover effect: scale up on hover
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
