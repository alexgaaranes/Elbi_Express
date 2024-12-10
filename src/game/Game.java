package game;

import game.panes.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
    private final Stage stage;
    private static Scene menuScene;
    private static Scene developerScene;
    private static Scene aboutScene;
    private Scene selectionScene;

    // STATIC Attributes
    // 720 Resolution TODO: Try dynamic scaling for diff viewport
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1022;

    public Game(Stage stage) {
        this.stage = stage;

        // SETUP MAIN MENU SCENE
        MenuPane menuPane = new MenuPane(this.stage);
        menuScene = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPane.setParentScene(menuScene);

        // SETUP OTHER SCENES
        setUpScenes();
        
        menuPane.setButtonScenes(developerScene, aboutScene);
    }

    // Methods
    public void start(){
        stage.setTitle("Elbi Express");
        stage.setScene(menuScene);
        stage.setResizable(false);

        stage.show();

        // Handle the closing of all process on closing of window on some circumstances
        stage.setOnCloseRequest(e ->{
            System.out.println("Window closed: Ending all processes...");
            System.exit(0);
        });
    }

    private void setUpScenes(){
        AboutPane aboutPane = new AboutPane(this.stage);
        aboutScene = new Scene(aboutPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        aboutPane.setParentScene(aboutScene);

        DeveloperPane developerPane = new DeveloperPane(this.stage);
        developerScene = new Scene(developerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        developerPane.setParentScene(developerScene);
        developerPane.setButtonScenes(menuScene);

    }

	public static Scene getMenuScene() {
		return menuScene;
	}
}
