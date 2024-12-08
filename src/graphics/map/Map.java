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
    private GraphicsContext parentGC;
    private Scoreboard scoreboard;
    private int row;
    private int col;
    private double tileH;
    private double tileW;
    private Image mapImage = new Image("file:src/assets/sprites/map.png");
    private Image houseImage = new Image("file:src/assets/sprites/building.png");

    /* MAP GRID
    *   0 - Empty (Road)
    *   1 - Wall/Building
    *   2 - Store
    *    3 - Household
    * */
    private final int[][] mapMatrix = new int[][] {
	    	{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,1,1,1,1,2,0,0,1,2,0,0,1,1,1,1,0,0,1,1,0,0,1,1,1,1,2,0,0,1,2,0,0,1,1,1,1,0,0},
    		{0,0,1,1,1,1,0,0,0,1,0,0,0,1,1,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,0,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,3,0,0,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,3,0,0,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,3,0,0,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,3,0,0,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,3,0,0,1,1,1,0,0,1,1,0,0,1,1,1,3,0,0,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,1,1,0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };
    ArrayList<Graphic> mapTiles = new ArrayList<>();
    ArrayList<Store> storeList = new ArrayList<>();
    ArrayList<Household> houseList = new ArrayList<>();

    public Map(Stage stage, Scene parentScene, Scoreboard scoreboard, GraphicsContext gc) {
        this.stage = stage;
        this.parentScene = parentScene;
        this.scoreboard = scoreboard;
        this.parentGC = gc;
        this.row = mapMatrix.length;
        this.col = mapMatrix[0].length;
        
        this.tileH = (double) Game.WINDOW_HEIGHT /row;
        this.tileW = (double) Game.WINDOW_WIDTH /col;

        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                if(mapMatrix[i][j] == 2){
                    this.storeList.add(new Store(j, i,Store.STORE_NAMES[storeList.size()], this, this.parentGC));

                } else if (mapMatrix[i][j] == 3){
                    this.houseList.add(new Household(j, i,this, this.parentGC));
                }
            }
        }
    }

    public void drawMap(GraphicsContext gc){
    	gc.drawImage(this.mapImage, 0, 0);
    }
    
    public void drawHouse(GraphicsContext gc){
    	gc.drawImage(this.houseImage, 0, 0);
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

