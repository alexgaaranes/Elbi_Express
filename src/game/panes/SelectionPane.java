package game.panes;

import game.Game;
import game.Scoreboard;
import graphics.map.Map;
import graphics.vehicles.Vehicle;
import graphics.vehicles.types.Car;
import graphics.vehicles.types.Motorcycle;
import graphics.vehicles.types.Truck;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private boolean isReady;

    private String vehicles[] = {"Car", "Motor", "Truck"};
    private Vehicle p1, p2;
    private int p1Index = 0;
    private int p2Index = 0;
    private int p1ColorIndex = 0;
    private int p2ColorIndex = 0;
    private int spinFrame = 0;
    private ImageView p1Preview;
    private ImageView p2Preview;

    Canvas selectionCanvas;
    GraphicsContext gc;
    Map map;
    Scoreboard scoreboard;
    // UI elements
    private Text p1Text;
    private Text p2Text;
    private Text player1Ready;
    private Text player2Ready;
    private final Image background = new Image("file:src/assets/sprites/selectionBG.png");

    public SelectionPane(Stage stage) {
        this.stage = stage;
        this.selectionCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.getChildren().add(selectionCanvas);
        this.gc = selectionCanvas.getGraphicsContext2D();
        this.isReady = false;
    }

    public void setUpSelection(){
        this.setUpPlayScene();
        this.setUp();
        this.setUpUI();
        this.loadSpinFrame();
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
                    playPane.startGame(p1Index, p2Index, p1ColorIndex, p2ColorIndex);
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
                if(isReady){
                    activeKeys.clear();
                    this.stop();
                }
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

        // Detect when changing skins
        new AnimationTimer(){
            KeyCode p1LastKey = null;
            KeyCode p2LastKey = null;
            @Override
            public void handle(long l) {
                if(isReady){
                    activeKeys.clear();
                    this.stop();
                }
                if(p1LastKey != null && !activeKeys.contains(p1LastKey)){p1LastKey = null;}
                if(p1LastKey == null && activeKeys.contains(KeyCode.W)){
                    p1LastKey = KeyCode.W;
                    p1ColorIndex = ++p1ColorIndex % 7;
                }
                if(p1LastKey == null && activeKeys.contains(KeyCode.S)){
                    p1LastKey = KeyCode.S;
                    --p1ColorIndex;
                    if(p1ColorIndex < 0){p1ColorIndex = 6;}
                }
                if(p2LastKey != null && !activeKeys.contains(p2LastKey)){p2LastKey = null;}
                if(p2LastKey == null && activeKeys.contains(KeyCode.UP)){
                    p2LastKey = KeyCode.UP;
                    p2ColorIndex = ++p2ColorIndex % 7;
                }
                if(p2LastKey == null && activeKeys.contains(KeyCode.DOWN)){
                    p2LastKey = KeyCode.DOWN;
                    --p2ColorIndex;
                    if(p2ColorIndex < 0){p2ColorIndex = 6;}
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
                    player1Ready.setX(300);
                    player1Ready.setY(700);
                    player1Ready.setFill(Color.GREEN);
                } else {
                    player1Ready.setText("Hold Q to ready");
                    player1Ready.setX(225);
                    player1Ready.setY(700);
                    player1Ready.setFill(Color.RED);
                }
                if(activeKeys.contains(KeyCode.PERIOD)){
                    player2Ready.setText("Ready!");
                    player2Ready.setX(890);
                    player2Ready.setY(700);
                    player2Ready.setFill(Color.GREEN);
                } else {
                    player2Ready.setText("Hold PERIOD to ready");
                    player2Ready.setX(765);
                    player2Ready.setY(700);
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
        player1Ready = new Text("Hold Q to ready");
        player2Ready = new Text("Hold PERIOD to ready");
        player1Ready.setFill(Color.RED);
        player2Ready.setFill(Color.RED);
        player1Ready.setStroke(Color.BLACK);
        player2Ready.setStroke(Color.BLACK);
        player1Ready.setStrokeWidth(1);
        player2Ready.setStrokeWidth(1);
        player1Ready.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        player2Ready.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        p1Text = new Text(vehicles[p1Index]);
        p2Text = new Text(vehicles[p2Index]);
        p1Text.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 30));
        p2Text.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 30));
        p1Text.setFill(Color.valueOf("ffb81c"));
        p2Text.setFill(Color.valueOf("ffb81c"));
        p1Text.setStroke(Color.BLACK);
        p2Text.setStroke(Color.BLACK);
        p1Text.setStrokeWidth(2);
        p2Text.setStrokeWidth(2);
        p1Text.setX(275);
        p1Text.setY(950);
        p2Text.setX(900);
        p2Text.setY(950);

        // Preview Setup
        p1Preview = new ImageView(new Image("file:src/assets/sprites/vehicle-sheets/"
                +vehicles[p1Index].toLowerCase()+"/"+this.p1ColorIndex+"-"+vehicles[p1Index].toLowerCase()+".png"));
        p2Preview = new ImageView(new Image("file:src/assets/sprites/vehicle-sheets/"
                +vehicles[p2Index].toLowerCase()+"/"+this.p2ColorIndex+"-"+vehicles[p2Index].toLowerCase()+".png"));
        p1Preview.setScaleX(1.75);
        p1Preview.setScaleY(1.75);
        p2Preview.setScaleX(1.75);
        p2Preview.setScaleY(1.75);
        p1Preview.setX(300);
        p1Preview.setY(400);
        p2Preview.setX(900);
        p2Preview.setY(400);

        this.getChildren().addAll(p1Text, p2Text, player1Ready, player2Ready ,p1Preview, p2Preview);
        // Auto Update text content base on the user
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                p1Text.setText(vehicles[p1Index]);
                p2Text.setText(vehicles[p2Index]);
                p1Preview.setImage(new Image("file:src/assets/sprites/vehicle-sheets/"
                        +vehicles[p1Index].toLowerCase()+"/"+p1ColorIndex+"-"+vehicles[p1Index].toLowerCase()+".png"));
                p2Preview.setImage(new Image("file:src/assets/sprites/vehicle-sheets/"
                        +vehicles[p2Index].toLowerCase()+"/"+p2ColorIndex+"-"+vehicles[p2Index].toLowerCase()+".png"));
            }
        }.start();

        // Auto update imagePreview rotation
        new AnimationTimer() {
            float side = 96;
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                int col = spinFrame % 4;
                int row = spinFrame / 4;
                p1Preview.setViewport(new Rectangle2D(col*side, row*side, side, side));
                p2Preview.setViewport(new Rectangle2D(col*side, row*side, side, side));
            }
        }.start();
    }

    private void loadSpinFrame(){
        new AnimationTimer(){
            long startTime = System.nanoTime();
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                if(l - startTime > 500000000L){ // Get new frame every 0.5 sec
                    spinFrame = ++spinFrame % 8;
                    startTime = l;
                }
            }
        }.start();
    }


    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
