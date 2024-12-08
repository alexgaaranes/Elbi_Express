package game.panes;

import game.Game;
import game.Scoreboard;
import graphics.map.Map;
import graphics.vehicles.Vehicle;
import graphics.vehicles.types.Car;
import graphics.vehicles.types.Motorcycle;
import graphics.vehicles.types.Truck;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

public class SelectionPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;
    private Scene playScene = null;
    private static HashSet<KeyCode> activeKeys = new HashSet<>();
    private boolean isReady = false;

    private String vehicles[] = {"Car", "Motorcycle", "Truck"};
    private Vehicle p1, p2;
    private int p1Index = 0;
    private int p2Index = 0;

    Canvas selectionCanvas;
    GraphicsContext gc;
    Map map;
    Scoreboard scoreboard;
    // UI elements
    private Text p1Text;
    private Text p2Text;
    private final Text player1Title = new Text("Player 1");
    private final Text player2Title = new Text("Player 2");
    private Text player1Ready;
    private Text player2Ready;
    private final Image background = new Image("file:src/assets/sprites/selectionBG.png");

    public SelectionPane(Stage stage) {
        this.stage = stage;
        this.selectionCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.getChildren().add(selectionCanvas);
        this.gc = selectionCanvas.getGraphicsContext2D();
    }

    public void setUpSelection(){
        this.setUpPlayScene();
        this.setUp();
        this.setUpUI();
    }

    // Setup selection scene
    private void setUp(){
        this.parentScene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
        });
        this.parentScene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
        });

        // Detect Ready on both players (HOLD Q and PERIOD to start)
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(isReady){
                    PlayPane playPane = (PlayPane) playScene.getRoot();
                    playPane.startGame(p1Index, p2Index);
                    stage.setScene(playScene);
                    this.stop();
                }
            }
        }.start();

        // Detect when changing options
        new AnimationTimer() {
            KeyCode p1LastKey = null;
            KeyCode p2LastKey = null;
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                if(p1LastKey != null && !activeKeys.contains(p1LastKey)){p1LastKey = null;}
                if(p1LastKey == null && activeKeys.contains(KeyCode.D)){
                    p1LastKey = KeyCode.D;
                    p1Index = ++p1Index % vehicles.length;
                }
                if(p1LastKey == null && activeKeys.contains(KeyCode.A)){
                    p1LastKey = KeyCode.A;
                    --p1Index;
                    if(p1Index < 0){p1Index = vehicles.length-1;}
                }
                if(p2LastKey != null && !activeKeys.contains(p2LastKey)){p2LastKey = null;}
                if(p2LastKey == null && activeKeys.contains(KeyCode.RIGHT)){
                    p2LastKey = KeyCode.RIGHT;
                    p2Index = ++p2Index % vehicles.length;
                }
                if(p2LastKey == null && activeKeys.contains(KeyCode.LEFT)){
                    p2LastKey = KeyCode.LEFT;
                    --p2Index;
                    if(p2Index < 0){p2Index = vehicles.length-1;}
                }
            }
        }.start();

        // Detect ready on both players
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                if(activeKeys.contains(KeyCode.Q)){
                    player1Ready.setText("Ready!");
                    player1Ready.setFill(Color.GREEN);
                } else {
                    player1Ready.setText("Hold Q to ready");
                    player1Ready.setFill(Color.RED);
                }
                if(activeKeys.contains(KeyCode.PERIOD)){
                    player2Ready.setText("Ready!");
                    player2Ready.setFill(Color.GREEN);
                } else {
                    player2Ready.setText("Hold PERIOD to ready");
                    player2Ready.setFill(Color.RED);
                }
                if(activeKeys.contains(KeyCode.Q) && activeKeys.contains(KeyCode.PERIOD)){
                    isReady = true;
                }
            }
        }.start();

        // Draw Background
        gc.drawImage(background, 0, 0);
    }


    // SetUp play Scene
    private void setUpPlayScene(){
        PlayPane playPane = new PlayPane(this.stage);
        this.playScene = new Scene(playPane, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        playPane.setParentScene(this.playScene);
    }

    // Set Up UI elements
    private void setUpUI(){
        player1Title.setFill(Color.BLUE);
        player2Title.setFill(Color.RED);
        player1Title.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 40));
        player2Title.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 40));
        player1Title.setX(180);
        player1Title.setY(65);
        player2Title.setX(830);
        player2Title.setY(65);
        player1Ready = new Text("Hold Q to ready");
        player2Ready = new Text("Hold PERIOD to ready");
        player1Ready.setFill(Color.RED);
        player2Ready.setFill(Color.RED);
        player1Ready.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        player2Ready.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        player1Ready.setX(165);
        player1Ready.setY(800);
        player2Ready.setX(800);
        player2Ready.setY(800);
        p1Text = new Text(vehicles[p1Index]);
        p2Text = new Text(vehicles[p2Index]);
        p1Text.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 30));
        p2Text.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 30));
        p1Text.setFill(Color.valueOf("ffb81c"));
        p2Text.setFill(Color.valueOf("ffb81c"));
        p1Text.setX(200);
        p1Text.setY(950);
        p2Text.setX(800);
        p2Text.setY(950);

        this.getChildren().addAll(p1Text, p2Text, player1Title, player2Title, player1Ready, player2Ready);
        // Auto Update text content base on the user
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                p1Text.setText(vehicles[p1Index]);
                p2Text.setText(vehicles[p2Index]);
            }
        }.start();
    }



    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
