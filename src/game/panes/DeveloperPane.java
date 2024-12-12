/**
 * DeveloperPane class represents a custom pane for displaying developer profiles.
 * It extends the Pane class and implements the gamePane interface.
 * The pane includes a background image, a button to return to the menu scene, and manages scene transitions.
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

public class DeveloperPane extends Pane implements gamePane {
    private final Stage stage;
    private Scene parentScene = null;
    private ImageView background;

    /**
     * Constructor to initialize the DeveloperPane with the provided stage.
     * It sets up the background image and initializes the buttons.
     *
     * @param stage The main stage for the application.
     */
    public DeveloperPane(Stage stage) {
        this.stage = stage;

        //Set the initial background image for the pane
        background = new ImageView(
                new Image(getClass().getResource("/assets/sprites/devs.png").toExternalForm())
        );
        this.getChildren().add(background);

        //Load and apply a custom pixel font for buttons
        Font pixelFont = Font.loadFont(getClass().getResource("/assets/sprites/pixelFont.ttf").toExternalForm(), 20);
        setUpButtons(pixelFont);
    }

    /**
     * Sets the parent scene for navigation purposes.
     *
     * @param scene The scene to be set as the parent.
     */
    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    /**
     * Sets the menu scene to be used when navigating back.
     *
     * @param menu The menu scene to navigate to.
     */

    /**
     * Sets up the buttons and their actions on the DeveloperPane.
     * Buttons are styled and positioned, and actions are assigned to them.
     *
     * @param font The font used for button text.
     */
    private void setUpButtons(Font font) {
        //Create the "BACK" button
        Button backBtn = createStyledButton("BACK", font);

        //Set the position for the button within the pane
        backBtn.setLayoutX(50);
        backBtn.setLayoutY(50);

        // Set the action to switch to the menu scene when the button is clicked
        backBtn.setOnAction(event -> {
            Audio.playClip("menuButton", 1.5);
            stage.setScene(parentScene);  //Set the menu scene as the current scene on the stage.
        });

        //Add the button to the pane
        this.getChildren().addAll(backBtn);
    }

    /**
     * Creates and styles a button with the given text and font.
     * The button is styled with a gradient background, border, padding, and a shadow effect.
     * Additionally, it includes hover effects to scale the button on mouse enter and reset on mouse exit.
     *
     * @param text The text to display on the button.
     * @param font The font used for the button text.
     * @return The created button with styling and effects applied.
     */
    private Button createStyledButton(String text, Font font) {
        Button button = new Button(text);
        button.setFont(font);  // Set the font for the button text.
        button.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);" +
            "-fx-text-fill: black;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 4px;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 8 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 4, 0.5, 2, 2);"  // Apply gradient and shadow styles.
        );

        //Add hover effect: scale up the button when the mouse enters
        button.setOnMouseEntered(event -> {
            button.setScaleX(1.2);
            button.setScaleY(1.2);
        });

        // Reset the button size when the mouse exits
        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        return button;  //Return the styled button.
    }
}
