package graphics.map;

import java.util.Random;

import graphics.vehicles.Vehicle;
import javafx.geometry.Rectangle2D;

public class Household extends Objective{
	private String order;
	private boolean activeOrder = false;

    Household(int xGridPos, int yGridPos, Map map) {
        super(xGridPos, yGridPos, map);
    }

    @Override
    public void openObjective(){
        System.out.println("In household: "+this.occupiedVehicle);
        getDeliver(this.occupiedVehicle);
    }
    
    
    public void getDeliver(Vehicle vehicle) {
    	if(this.activeOrder) {
    		
    		//Checks is the vehicle has the order
    		boolean orderOnHand = false;
    		if(order.equals("Jollibee") && vehicle.getStore1() != 0) {
    			vehicle.setStore1(vehicle.getStore1() - 1);
    			orderOnHand = true;
    		}
    		else if(order.equals("Domino's Pizza") && vehicle.getStore2() != 0) {
    			vehicle.setStore2(vehicle.getStore2() - 1);
    			orderOnHand = true;
    		}
    		else if(order.equals("Burger King") && vehicle.getStore3() != 0) {
    			vehicle.setStore3(vehicle.getStore3() - 1);
    			orderOnHand = true;
    		}
    		else if(order.equals("Dairy Queen") && vehicle.getStore4() != 0) {
    			vehicle.setStore4(vehicle.getStore4() - 1);
    			orderOnHand = true;
    		}
    		
    		if(orderOnHand) {
    			//prompt the user to do task
    			vehicle.setScore(vehicle.getScore()+1);
    			System.out.println(vehicle + " successfully delivered the order");
    		}
    	}
    }

	public boolean isActiveOrder() {
		return activeOrder;
	}
	
	//Will be called when activeOrder is false and at a random time stamp
	public void setActiveOrder(boolean activeOrder) {
		this.activeOrder = activeOrder;
		Random random = new Random();
        int randomOrder = random.nextInt(4);
        this.order = map.storeList.get(randomOrder).getName();
        map.storeList.get(randomOrder).setOrderMade(map.storeList.get(randomOrder).getOrderMade() + 1);
	}
}
