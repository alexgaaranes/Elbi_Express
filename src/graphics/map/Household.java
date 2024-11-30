package graphics.map;

import java.util.HashMap;
import java.util.Random;
import graphics.vehicles.Vehicle;

public class Household extends Objective{
	private String order;
	private boolean hasActiveOrder = false;

    Household(int xGridPos, int yGridPos, Map map) {
        super(xGridPos, yGridPos, map);
    }

    @Override
    public void openObjective(){
        //System.out.println("In household: "+this.occupiedVehicle);
        getDeliver(this.occupiedVehicle);
    }
    
    public void getDeliver(Vehicle vehicle) {
    	if(this.hasActiveOrder) {
			HashMap<String, Integer> vehicleStoreOrder = vehicle.getStoreOrder();

			if(vehicleStoreOrder.containsKey(this.order)) {
				if(vehicleStoreOrder.get(this.order) > 1) {
					vehicleStoreOrder.put(this.order, vehicleStoreOrder.get(this.order) - 1);	// Reduce the vehicle load of the order
				} else {
					vehicleStoreOrder.remove(this.order);	// Remove the load if all are unloaded
				}
				vehicle.updateLoad(-1);
				vehicle.updateScore(1);
				System.out.println("Order Delivered by "+ vehicle);
			}
			this.hasActiveOrder = false;
    	}
    }

	// Will be called when activeOrder is false and at a random time stamp
	public void setActiveOrder() {
		this.hasActiveOrder = true;
		Random r = new Random();
        int randomOrder = r.nextInt(Store.NUM_OF_STORES);
		Store storeToOrder = map.storeList.get(randomOrder);
        this.order = storeToOrder.getName();	// Get the store name where the household wants the order from
		storeToOrder.addOrder(1);

		System.out.println("Order Set "+this.order+"from"+this);

		/*
		* 	START THE TIMER FOR THE ORDER
		* */
	}
}
