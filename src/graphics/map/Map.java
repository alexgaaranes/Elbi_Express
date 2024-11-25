package graphics.map;

import graphics.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {

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

    public Map() {
        for(int i=0; i<mapMatrix.length; i++) {
            for(int j=0; j<mapMatrix[0].length; j++) {
                double xPos = 0; // TODO: Calculate for position base on index
                double yPos = 0;
                Graphics tileGraphic = new Graphics(new Image(""), xPos, yPos);
            }
        }
    }


}
