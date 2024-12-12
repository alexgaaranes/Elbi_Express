/**
 * SelectionPane class represents a custom pane for displaying the selection phase of the game.
 * It extends the Group class and implements the gamePane interface.
 * The pane includes a background image, the skins and vehicles available to the game, and manages scene transitions.
 */

package game.panes;

import game.Audio;
import game.Game;
import game.Scoreboard;
import graphics.map.Map;
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

import java.util.HashSet;

public class SelectionPane extends Group implements gamePane{
	//Attributes associated in the scene
    private final Stage stage;
    private Scene parentScene = null;
    private Scene playScene = null;
    private static HashSet<KeyCode> activeKeys = new HashSet<>();
    Canvas selectionCanvas;
    private boolean isReady;

    //Attributes associated with the players' to the vehicles
    private String vehicles[] = {"Car", "Motor", "Truck"};
    private int p1Index = 0;
    private int p2Index = 0;
    private int p1ColorIndex = 0;
    private int p2ColorIndex = 0;
    private int spinFrame = 0;
    private ImageView p1Preview;
    private ImageView p2Preview;
    
    //Attributes associated to the map generation
    GraphicsContext gc;
    Map map;
    Scoreboard scoreboard;
    
    // UI elements
    private Text p1Text;
    private Text p2Text;
    private Text player1Ready;
    private Text player2Ready;
    private Image background = new Image(getClass().getResource("/assets/sprites/selectionBG.png").toExternalForm());

    /**
     * Constructor to initialize the SelectionPane with the provided stage.
     * It sets up the background image and initializes the selection boolean.
     * 
     * @param stage The main stage for the application.
     */
    public SelectionPane(Stage stage) {
        this.stage = stage;
        this.selectionCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.getChildren().add(selectionCanvas);
        this.gc = selectionCanvas.getGraphicsContext2D();
        this.isReady = false;
    }

    
    /**
     * Calls the methods to set up the SelectionPane.
     */
    public void setUpSelection(){
        this.setUpPlayScene();
        this.setUp();
        this.setUpUI();
        this.loadSpinFrame();
    }

    /**
     * Sets up the selection prompts and their actions on the selection phase.
     */
    private void setUp(){
        this.parentScene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
        });
        this.parentScene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
        });
        
        //Timer to monitor if the players are ready to play
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(isReady){
                    PlayPane playPane = (PlayPane) playScene.getRoot();
                    playPane.startGame(p1Index, p2Index, p1ColorIndex, p2ColorIndex);
                    stage.setScene(playScene);
                    Audio.playSound("in_game", 0.5);
                    this.stop();
                }
            }
        }.start();

        //Timer that monitors changing vehicles
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

        //Timer that monitors changing skins
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

        /**
         * Timer that monitors changes in the player readiness and updates visual cues.
         */
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

        //Displays the background
        gc.drawImage(background, 0, 0);
    }


    /**
     * Prepares play screen where players can get engages in the game.
     * Creates a new PlayPane, and sets it up.
     */
    private void setUpPlayScene(){
        PlayPane playPane = new PlayPane(this.stage);
        this.playScene = new Scene(playPane, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        playPane.setParentScene(this.playScene);
    }

    /**
     * Sets up the text, vehicle, skins, and visual cues.
     * Text are styled and positioned.
     */
    private void setUpUI(){
        player1Ready = new Text("Hold Q to ready");
        player2Ready = new Text("Hold PERIOD to ready");
        player1Ready.setFill(Color.RED);
        player2Ready.setFill(Color.RED);
        player1Ready.setStroke(Color.BLACK);
        player2Ready.setStroke(Color.BLACK);
        player1Ready.setStrokeWidth(1);
        player2Ready.setStrokeWidth(1);
        player1Ready.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 20));
        player2Ready.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 20));
        p1Text = new Text(vehicles[p1Index]);
        p2Text = new Text(vehicles[p2Index]);
        p1Text.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 30));
        p2Text.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 30));
        p1Text.setFill(Color.valueOf("ffb81c"));
        p2Text.setFill(Color.valueOf("ffb81c"));
        p1Text.setStroke(Color.BLACK);
        p2Text.setStroke(Color.BLACK);
        p1Text.setStrokeWidth(2);
        p2Text.setStrokeWidth(2);
        p1Text.setX(300);
        p1Text.setY(950);
        p2Text.setX(900);
        p2Text.setY(950);

        // Preview Setup
        p1Preview = new ImageView(new Image(getClass().getResource("/assets/sprites/vehicle-sheets/"+vehicles[p1Index].toLowerCase()+"/"
                +p1ColorIndex+"-"+vehicles[p1Index].toLowerCase()+".png").toExternalForm()));
        p2Preview = new ImageView(new Image(getClass().getResource("/assets/sprites/vehicle-sheets/"+vehicles[p2Index].toLowerCase()+"/"
                +p2ColorIndex+"-"+vehicles[p2Index].toLowerCase()+".png").toExternalForm()));
        p1Preview.setScaleX(1.75);
        p1Preview.setScaleY(1.75);
        p2Preview.setScaleX(1.75);
        p2Preview.setScaleY(1.75);
        p1Preview.setX(300);
        p1Preview.setY(400);
        p2Preview.setX(900);
        p2Preview.setY(400);

        // ImageView Setup for Key Hints on Selection
        ImageView keyW = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyW.png").toExternalForm()));
        ImageView keyS = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyS.png").toExternalForm()));
        ImageView keyA = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyA.png").toExternalForm()));
        ImageView keyD = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyD.png").toExternalForm()));
        ImageView keyUP = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyUP.png").toExternalForm()));
        ImageView keyDOWN = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyDOWN.png").toExternalForm()));
        ImageView keyLEFT = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyLEFT.png").toExternalForm()));
        ImageView keyRIGHT = new ImageView(new Image(getClass().getResource("/assets/sprites/keys/keyRIGHT.png").toExternalForm()));
        double scale = 0.15;
        setScale(keyW, scale);
        setScale(keyA, scale);
        setScale(keyD, scale);
        setScale(keyS, scale);
        setScale(keyUP, scale);
        setScale(keyDOWN, scale);
        setScale(keyLEFT, scale);
        setScale(keyRIGHT, scale);
        setXY(keyW, 205, 180);
        setXY(keyS, 205, 460);
        setXY(keyA, 100, 805);
        setXY(keyD, 350, 805);
        setXY(keyUP, 795, 180);
        setXY(keyDOWN, 795, 460);
        setXY(keyLEFT, 700, 805);
        setXY(keyRIGHT, 950, 805);


        this.getChildren().addAll(p1Text, p2Text, player1Ready, player2Ready ,p1Preview, p2Preview, keyW, keyA, keyS, keyD, keyUP, keyDOWN, keyLEFT, keyRIGHT);
        // Auto Update text content base on the user
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                p1Text.setText(vehicles[p1Index]);
                p2Text.setText(vehicles[p2Index]);
                p1Preview.setImage(new Image(getClass().getResource("/assets/sprites/vehicle-sheets/"+vehicles[p1Index].toLowerCase()+"/"
                +p1ColorIndex+"-"+vehicles[p1Index].toLowerCase()+".png").toExternalForm()));
                p2Preview.setImage(new Image(getClass().getResource("/assets/sprites/vehicle-sheets/"+vehicles[p2Index].toLowerCase()+"/"
                        +p2ColorIndex+"-"+vehicles[p2Index].toLowerCase()+".png").toExternalForm()));
            }
        }.start();

        //Auto update imagePreview rotation
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

    /**
     * Timer that updates vehicle sprite angle.
     */
    private void loadSpinFrame(){
        new AnimationTimer(){
            long startTime = System.nanoTime();
            @Override
            public void handle(long l) {
                if(isReady){this.stop();}
                if(l - startTime > 500000000L){ //Get new frame every 0.5 sec
                    spinFrame = ++spinFrame % 8;
                    startTime = l;
                }
            }
        }.start();
    }

    /**
     * Set scale of images.
     * 
     * @param imageView The image to be scaled.
     * @param scale The value to be set as image size.
     */
    private void setScale(ImageView imageView, double scale){
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
    }

    /**
     * Set position of images.
     * 
     * @param imageView The image to be positioned.
     * @param x The x-coordinate in pixel to be set.
     * @param y The u-coordinate in pixel to be set.
     */
    private void setXY(ImageView imageView, double x, double y){
        imageView.setX(x);
        imageView.setY(y);
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
}
