package graphics.map;

import graphics.vehicles.Vehicle;
import javafx.geometry.Rectangle2D;

public class Household extends Objective{

    Household(int xGridPos, int yGridPos, Map map) {
        super(xGridPos, yGridPos, map);
    }

    @Override
    public void openObjective(){
        System.out.println("In household: "+this.occupiedVehicle);
    }
}
