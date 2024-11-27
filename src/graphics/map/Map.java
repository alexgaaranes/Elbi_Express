package graphics.map;

import game.Game;
import graphics.Graphic;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private final Stage stage;
    private final Scene parentScene;
    private int row;
    private int col;
    private double tileH;
    private double tileW;

    /* MAP GRID
    *   0 - Empty (Road)
    *   1 - Wall/Building
    *   2 - Store
    *    3 - Household
    * */
    private final int[][] mapMatrix = new int[][] {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,2,1,2,1,1,0,1,0,1,1,2,1,2,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,0},
            {0,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
    ArrayList<Graphic> mapTiles = new ArrayList<>();
    HashMap<String, Store> storeMap = new HashMap<>();

    public Map(Stage stage, Scene parentScene) {
        this.stage = stage;
        this.parentScene = parentScene;
        this.row = mapMatrix.length;
        this.col = mapMatrix[0].length;

        this.tileH = (double) Game.WINDOW_HEIGHT /row;
        this.tileW = (double) Game.WINDOW_WIDTH /col;

        // Apply textures
        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                double xPos = j*tileW;
                double yPos = i*tileH;
                Graphic tileGraphic = new Graphic(
                        new Image(
                                mapMatrix[i][j]==1?"file:src/assets/sprites/testGrass.png":"file:src/assets/sprites/testRoad.png"
                        ), xPos, yPos);
                mapTiles.add(tileGraphic);

                if(mapMatrix[i][j] == 2){
                    this.storeMap.put(
                            j+"-"+i,
                            new Store(j, i)
                    );
                }
            }
        }
    }

    public void drawMap(GraphicsContext gc){
        for (Graphic tile : this.mapTiles){
            tile.render(gc);
        }
    }

    // Getter
    public int[][] getMapMatrix(){
        return this.mapMatrix;
    }

    public double getTileH(){
        return this.tileH;
    }

    public double getTileW(){
        return this.tileW;
    }

    public HashMap<String, Store> getStoreMap(){
        return this.storeMap;
    }
}

