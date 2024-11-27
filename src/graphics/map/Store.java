package graphics.map;

import graphics.Graphic;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;

public class Store implements Objective{
    private Map map;
    int xGridPos, yGridPos;
    double xPos, yPos;
    private Vehicle p1, p2;
    private Vehicle occupiedVehicle = null;

    public Store(int xGridPos, int yGridPos, Map map) {
        this.xGridPos = xGridPos;
        this.yGridPos = yGridPos;
        this.map = map;

        this.xPos = xGridPos * map.getTileW();
        this.yPos = yGridPos * map.getTileH();

    }

    @Override
    public Rectangle2D getBounds() {
       return new Rectangle2D(this.xPos, this.yPos, map.getTileW(), map.getTileH());
    }

    public void trackVehicle(Vehicle v1, Vehicle v2) {
        this.p1 = v1;
        this.p2 = v2;
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(bodyEnters(p1)){
                    if(occupiedVehicle == null){
                        setVehicle(p1);
                    }
                } else if(bodyEnters(p2)){
                    if(occupiedVehicle == null){
                        setVehicle(p2);
                    }
                } else {
                    occupiedVehicle = null;
                }
                openObjective();
            }
        }.start();
    }

    public boolean bodyEnters(Vehicle vehicle) {
        return vehicle.getBounds().intersects(this.getBounds());
    }

    public synchronized void setVehicle(Vehicle vehicle) {
        this.occupiedVehicle = vehicle;
    }

    @Override
    public void openObjective() {
        if(occupiedVehicle == null){return;}
        System.out.println("In store"+occupiedVehicle);
    }
}
