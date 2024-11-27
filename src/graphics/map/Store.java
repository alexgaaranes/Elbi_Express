package graphics.map;

import graphics.Graphic;
import graphics.vehicles.Vehicle;
import javafx.scene.image.Image;

public class Store implements Objective{
    int xGridPos, yGridPos;

    public Store(int xGridPos, int yGridPos) {
        this.xGridPos = xGridPos;
        this.yGridPos = yGridPos;
    }

    @Override
    public void openObjective(Vehicle vehicle) {
        System.out.println("In store");
    }
}
