package graphics.map;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;

abstract public class Objective {
    protected final Map map;
    protected int xGridPos, yGridPos;
    protected double xPos, yPos;
    protected Vehicle p1, p2;
    protected Vehicle occupiedVehicle = null;

    Objective(int xGridPos, int yGridPos, Map map) {
        this.xGridPos = xGridPos;
        this.yGridPos = yGridPos;
        this.map = map;

        this.xPos = xGridPos * map.getTileW();
        this.yPos = yGridPos * map.getTileH();

    }

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
                        openObjective();
                    }
                } else if(bodyEnters(p2)){
                    if(occupiedVehicle == null){
                        setVehicle(p2);
                        openObjective();
                    }
                } else {
                    occupiedVehicle = null;
                }
            }
        }.start();
    }

    public boolean bodyEnters(Vehicle vehicle) {
        return vehicle.getBounds().intersects(this.getBounds());
    }

    public synchronized void setVehicle(Vehicle vehicle) {
        this.occupiedVehicle = vehicle;
    }

    public void openObjective() {
        System.out.println("In objective: "+occupiedVehicle);
    }

}
