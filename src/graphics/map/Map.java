package graphics.map;

import game.Game;
import graphics.Graphics;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Map {
    private final Stage stage;
    private final Scene parentScene;
    private int row;
    private int col;
    private double tileH;
    private double tileW;

    private final int[][] mapMatrix = new int[][] {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,1,0},
            {0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
    ArrayList<Graphics> mapTiles = new ArrayList<>();

    public Map(Stage stage, Scene parentScene) {
        this.stage = stage;
        this.parentScene = parentScene;
        this.row = mapMatrix.length;
        this.col = mapMatrix[0].length;

        this.tileH = (double) Game.WINDOW_HEIGHT /row;
        this.tileW = (double) Game.WINDOW_WIDTH /col;

        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                double xPos = j*tileW;
                double yPos = i*tileH;
                Graphics tileGraphic = new Graphics(
                        new Image(""), xPos, yPos,
                        mapMatrix[i][j] == 0);
                mapTiles.add(tileGraphic);
            }
        }
    }

    public void drawMap(GraphicsContext gc){
        for (Graphics tile : this.mapTiles){
            tile.render(gc);
        }
    }

}
