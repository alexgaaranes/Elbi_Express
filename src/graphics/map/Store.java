package graphics.map;

import graphics.vehicles.Vehicle;

import java.util.HashMap;

public class Store extends Objective{
	private String name;
	private int currentOrders = 0;

	// STATIC CLASS ATTRIBUTES
	public static final int NUM_OF_STORES = 4;
	public static final String[] STORE_NAMES = {"Jollibee", "Domino's Pizza", "Burger King", "Dairy Queen"};

    public Store(int xGridPos, int yGridPos, String name, Map map) {
        super(xGridPos, yGridPos, map);
        this.setName(name);
    }

    @Override
    public void openObjective() {
        //System.out.println("In store: "+this.occupiedVehicle);
        makeOrder(this.occupiedVehicle);
    }
    
    public void makeOrder(Vehicle vehicle) {
    	if(!vehicle.isFull() && currentOrders != 0) {	// proceed to order
			/*
			* 	LOGIC FOR THE PROMPT
			* 	only proceed to the next lines if the prompt is finished
			* */

    		this.currentOrders -= 1;	// Decrement the number of ongoing orders from households
    		
    		// Making sure the order is properly labeled
			HashMap<String, Integer> vehicleStoreOrder = vehicle.getStoreOrder();
			if(vehicleStoreOrder.containsKey(this.name)) {
				vehicleStoreOrder.put(this.name, vehicleStoreOrder.get(this.name) + 1);
			} else {
				vehicleStoreOrder.put(this.name, 1);
			}

			vehicle.updateLoad(1); // Update Vehicle load by 1

    		System.out.println("Order from "+this.name+" was picked up by "+ vehicle);
    	}
    }

	public int getCurrentOrders() {
		return this.currentOrders;
	}

	public void addOrder(int order) {
		this.currentOrders += order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
