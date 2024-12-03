package graphics.map;

import game.Game;
import game.Scoreboard;
import graphics.Graphic;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Map {
    private final Stage stage;
    private final Scene parentScene;
    private final GraphicsContext parentGC;
    private Scoreboard scoreboard;
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
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,2,1,2,1,1,0,1,0,1,1,2,1,2,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,3,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,3,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,3,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,3,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,3,0},
            {0,1,3,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
    ArrayList<Graphic> mapTiles = new ArrayList<>();
    ArrayList<Store> storeList = new ArrayList<>();
    ArrayList<Household> houseList = new ArrayList<>();

    public Map(Stage stage, Scene parentScene, GraphicsContext parentGC, Scoreboard scoreboard) {
        this.stage = stage;
        this.parentScene = parentScene;
        this.parentGC = parentGC;
        this.scoreboard = scoreboard;
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
                                mapMatrix[i][j]==1?"file:src/assets/sprites/Grass_02_Green_1.png":"file:src/assets/sprites/Cobblestones_01_White_1.png"
                        ), xPos, yPos, tileW, tileH);
                mapTiles.add(tileGraphic);

                // Create instances of objectives on map
                if(mapMatrix[i][j] == 2){
                    this.storeList.add(new Store(j, i,Store.STORE_NAMES[storeList.size()], this, this.parentGC));

                } else if (mapMatrix[i][j] == 3){
                    this.houseList.add(new Household(j, i,this, this.parentGC));
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

    public ArrayList<Store> getStoreList(){
        return this.storeList;
    }

    public ArrayList<Household> getHouseList(){
        return this.houseList;
    }

    public Scene getMapScene(){
        return this.parentScene;
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

}

