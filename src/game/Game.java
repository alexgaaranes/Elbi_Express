package game;

import game.panes.AboutPane;
import game.panes.DeveloperPane;
import game.panes.MenuPane;
import game.panes.PlayPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
    private final Stage stage;
    private final Scene menuScene;
    private Scene playScene;
    private Scene developerScene;
    private Scene aboutScene;

    // STATIC Attributes
    // 720 Resolution TODO: Try dynamic scaling for diff viewport
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 960;

    public Game(Stage stage) {
        this.stage = stage;
        
        // SETUP MAIN MENU SCENE
        MenuPane menuPane = new MenuPane(this.stage, 10);
        this.menuScene = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPane.setParentScene(this.menuScene);

        // SETUP OTHER SCENES
        setUpScenes();
        menuPane.setButtonScenes(this.developerScene, this.aboutScene, this.playScene);
    }

    // Methods
    public void start(){
        stage.setTitle("Elbi Express");
        stage.setScene(this.menuScene);
        stage.setResizable(false);

        stage.show();

        // Handle the closing of all process on closing of window on some circumstances
        stage.setOnCloseRequest(e ->{
            System.out.println("Window closed: Ending all processes...");
            System.exit(0);
        });
    }

    private void setUpScenes(){
        PlayPane playPane = new PlayPane(this.stage);
        this.playScene = new Scene(playPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        playPane.setParentScene(this.playScene);

        AboutPane aboutPane = new AboutPane(this.stage);
        this.aboutScene = new Scene(aboutPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        aboutPane.setParentScene(this.aboutScene);

        DeveloperPane developerPane = new DeveloperPane(this.stage);
        this.developerScene = new Scene(developerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        developerPane.setParentScene(this.developerScene);
    }
}
