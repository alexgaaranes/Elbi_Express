/**
 * MenuPane class represents the main menu of the game.
 * It provides options to start the game, view developer information, see instructions, or quit.
 * This pane includes background setup, button creation, and scene transitions.
 */
package game.panes;

import game.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuPane extends Pane implements gamePane {
    private final Stage stage; 
    private Scene parentScene = null;
    private Scene developerScene = null; 
    private Scene aboutScene = null; 
    private ImageView background; 

    //Static reference to the active menu pane (used for global access).
    public static MenuPane activeMenuPane;

    /**
     * Constructor to initialize the MenuPane with the provided stage.
     * It sets up the background, loads the font, and initializes buttons.
     * 
     * @param stage The main stage for the application.
     */
    public MenuPane(Stage stage) {
        this.stage = stage;

        //Set the background image for the menu pane
        background = new ImageView(new Image(
                getClass().getResource("/assets/sprites/menu.png").toExternalForm()
        ));
        this.getChildren().add(background);

        //Load and apply a custom pixel font for buttons
        Font pixelFont = Font.loadFont(getClass().getResource("/assets/sprites/pixelFont.ttf").toExternalForm(), 20);
        activeMenuPane = this;
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
     * Sets the scenes for the developer and about sections.
     * 
     * @param devScene The scene to navigate to for developer information.
     * @param abScene The scene to navigate to for instructions.
     */
    public void setButtonScenes(Scene devScene, Scene abScene) {
        this.developerScene = devScene;
        this.aboutScene = abScene;
    }

    /**
     * Sets up the buttons and their actions on the MenuPane.
     * Buttons are styled, positioned, and linked to corresponding actions for navigation or game exit.
     * 
     * @param font The font used for button text.
     */
    private void setUpButtons(Font font) {
        //Create buttons for the main menu
        Button playBtn = createStyledButton("PLAY", font);
        Button developerBtn = createStyledButton("DEVELOPERS", font);
        Button aboutBtn = createStyledButton("INSTRUCTIONS", font);
        Button quitBtn = createStyledButton("QUIT", font);

        //Set positions for buttons within the pane
        playBtn.setLayoutX(590);
        playBtn.setLayoutY(750);

        developerBtn.setLayoutX(300);
        developerBtn.setLayoutY(750);

        aboutBtn.setLayoutX(780);
        aboutBtn.setLayoutY(750);

        quitBtn.setLayoutX(595);
        quitBtn.setLayoutY(850);

        //Define actions for the buttons
        playBtn.setOnAction(event -> {
            this.setSelection();  //Navigate to the selection screen.
        });

        developerBtn.setOnAction(event -> {
            stage.setScene(developerScene);  //Switch to the developer scene.
        });

        aboutBtn.setOnAction(event -> {
            stage.setScene(aboutScene);  //Switch to the about/instructions scene.
        });

        quitBtn.setOnAction(event -> {
            System.out.println("Exiting game...");  //Print exit message to console.
            stage.close();  //Close the application window.
            System.exit(0);  //Exit the application.
        });

        // Add the buttons to the pane
        this.getChildren().addAll(playBtn, developerBtn, aboutBtn, quitBtn);
    }

    /**
     * Switches to the selection screen where players can choose their game options.
     * Creates a new SelectionPane, sets it up, and changes the scene to the selection screen.
     */
    public void setSelection() {
        SelectionPane selectionPane = new SelectionPane(this.stage);
        Scene selectionScene = new Scene(selectionPane, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        selectionPane.setParentScene(selectionScene);

        stage.setScene(selectionScene);  //Switch to the selection scene.
        selectionPane.setUpSelection();  //Set up the selection interface.
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
        button.setFont(font);  //Set the font for the button text.
        button.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);" +
            "-fx-text-fill: black;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 4px;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 8 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 4, 0.5, 2, 2);"  //Apply gradient and shadow styles.
        );
        
        //Add hover effect: scale up the button when the mouse enters
        button.setOnMouseEntered(event -> {
            button.setScaleX(1.2); 
            button.setScaleY(1.2); 
        });
        
        //Reset the button size when the mouse exits
        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
        
        return button;  //Return the styled button.
    }
}
