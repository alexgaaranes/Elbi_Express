package game;

import game.panes.MenuPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
    private final Stage stage;
    private final Scene menuScene;

    // STATIC Attributes
    // 720 Resolution TODO: Try dynamic scaling for diff viewport
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public Game(Stage stage) {
        this.stage = stage;

        // SETUP MAIN MENU SCENE
        MenuPane menuPane = new MenuPane(this.stage);
        this.menuScene = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        menuPane.setParentScene(this.menuScene);
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
}
