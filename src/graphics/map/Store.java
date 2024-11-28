package graphics.map;

import graphics.Graphic;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;

import java.awt.*;

public class Store extends Objective{

    public Store(int xGridPos, int yGridPos, Map map) {
        super(xGridPos, yGridPos, map);
    }

    @Override
    public void openObjective() {
        System.out.println("In store: "+this.occupiedVehicle);
    }
}
