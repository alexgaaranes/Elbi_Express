package graphics.map;

import graphics.Graphic;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;

import java.awt.*;

public class Store extends Objective{
	private String name;
	private int orderMade = 0;
	private int orderDispatched1 = 0;
	private int orderDispatched2 = 0;

    public Store(int xGridPos, int yGridPos, Map map, String name) {
        super(xGridPos, yGridPos, map);
        this.setName(name);
    }

    @Override
    public void openObjective() {
        System.out.println("In store: "+this.occupiedVehicle);
        makeOrder(this.occupiedVehicle);
    }
    
    public void makeOrder(Vehicle vehicle) {
    	if(!vehicle.isFull() && orderMade != 0) {
    		if(vehicle == p1) {
    			this.orderDispatched1 += 1;
    		}
    		else {
    			this.orderDispatched2 += 1;
    		}
    		this.orderMade -= 1;
    		
    		//Making sure the order is properly labeled
    		if(this.getName().equals("Jollibee")) vehicle.setStore1(vehicle.getStore1() + 1);
    		else if(this.getName().equals("Domino's Pizza")) vehicle.setStore2(vehicle.getStore2() + 1);
    		else if(this.getName().equals("Burger King")) vehicle.setStore3(vehicle.getStore3() + 1);
    		else vehicle.setStore4(vehicle.getStore4() + 1);
    		
    		System.out.println("Order picked up by "+ vehicle);
    	}
    }

	public int getOrderMade() {
		return orderMade;
	}

	public void setOrderMade(int orderMade) {
		this.orderMade = orderMade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
