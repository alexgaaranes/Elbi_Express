package graphics.map;

import java.util.HashMap;
import java.util.Random;

import game.Timer;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Household extends Objective{
	private String order;
	private boolean hasActiveOrder = false;

    Household(int xGridPos, int yGridPos, Map map, GraphicsContext gc) {
		super(xGridPos, yGridPos, map, gc);
    }

    @Override
    public void openObjective(){
        //System.out.println("In household: "+this.occupiedVehicle);
		if(this.hasActiveOrder && this.occupiedVehicle.getStoreOrder().containsKey(this.order)){
			this.showPrompt();
		}
    }

	@Override
	protected void doProcess(){
		this.getDeliver(this.occupiedVehicle);
	}

	// Special process to be called by doProcess after successful prompts
    private void getDeliver(Vehicle vehicle) {
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

	// Will be called when activeOrder is false and at a random time stamp
	public void setActiveOrder() {
		this.hasActiveOrder = true;
		Random r = new Random();
        int randomOrder = r.nextInt(Store.NUM_OF_STORES);
		Store storeToOrder = map.storeList.get(randomOrder);
        this.order = storeToOrder.getName();	// Get the store name where the household wants the order from
		storeToOrder.addOrder(1);

		System.out.println("Order Set "+this.order+" from "+this);

		// Timer for order
		Timer orderTimer = new Timer(30);
		orderTimer.start();
		new AnimationTimer(){
			@Override
			public void handle(long l) {
				if(!hasActiveOrder) {this.stop();}
				if(!orderTimer.getStatus()){
					System.out.println("House "+this+" is angry");
					/*
					*  LOGIC FOR DMG OVER TIME
					* */
					this.stop();
				}
			}
		}.start();

	}

	public boolean getHasActiveOrder() {
		return this.hasActiveOrder;
	}
}
