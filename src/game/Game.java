/**
 * Central class for the Bite Rush: Elbi Edition game.
 * Handles the setup and initialization of various game scenes and manages the application lifecycle.
 */

package game;

import game.panes.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
    private final Stage stage;

    //Static scenes for easy reference between panes
    private static Scene menuScene;
    private static Scene developerScene;
    private static Scene aboutScene;

    //Scene for selection functionality (if applicable)
    private Scene selectionScene;

    /**
     * Static attributes for window dimensions.
     * TODO: Consider implementing dynamic scaling for different viewports.
     */
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1024;

    /**
     * Constructor for the Game class.
     * Sets up the primary stage and initializes the main menu and other scenes.
     *
     * @param stage The primary stage for the JavaFX application.
     */
    public Game(Stage stage) {
        this.stage = stage;

        //Setup Main Menu Scene
        MenuPane menuPane = new MenuPane(this.stage);
        menuScene = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPane.setParentScene(menuScene);

        //Setup other scenes
        setUpScenes();

        //Link menu pane buttons to other scenes
        menuPane.setButtonScenes(developerScene, aboutScene);
    }

    /**
     * Starts the application by setting the initial scene and stage properties.
     * Displays the main menu and sets up a handler for proper application closure.
     */
    public void start() {
        stage.setTitle("Bite Rush: Elbi Edition");
        stage.setScene(menuScene);
        stage.setResizable(false);

        stage.show();

        //Handle closing operations to ensure all processes terminate correctly
        stage.setOnCloseRequest(e -> {
            System.out.println("Window closed: Ending all processes...");
            stage.close();
            System.exit(0);
        });
    }

    /**
     * Sets up the additional scenes (About and Developer) for the application.
     * Configures each scene with its respective pane and links navigation buttons.
     */
    private void setUpScenes() {
        // Setup About Scene
        AboutPane aboutPane = new AboutPane(this.stage);
        aboutScene = new Scene(aboutPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        aboutPane.setParentScene(menuScene);
        aboutPane.setButtonScenes(menuScene);

        // Setup Developer Scene
        DeveloperPane developerPane = new DeveloperPane(this.stage);
        developerScene = new Scene(developerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        developerPane.setParentScene(menuScene);
        developerPane.setButtonScenes(menuScene);
    }

    /**
     * Getter for the main menu scene.
     * Provides access to the static menuScene object for inter-scene navigation.
     *
     * @return The main menu Scene object.
     */
    public static Scene getMenuScene() {
        return menuScene;
    }
}