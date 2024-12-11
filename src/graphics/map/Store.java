package graphics.map;

import graphics.vehicles.Vehicle;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import game.panes.PlayPane;

public class Store extends Objective{
	private String name;
	private int currentOrders = 0;	// Keeps track of orders from household to the instance
	private Group orderBags = new Group();
	private PlayPane bagPane;

	// STATIC CLASS ATTRIBUTES
	public static final int NUM_OF_STORES = 4;
	public static final String[] STORE_NAMES = {"Jollibee", "Domino's Pizza", "Dairy Queen", "Burger King"};
	private static Image bag = new Image(Store.class.getResource("file:src/assets/sprites/order.gif").toExternalForm());

    public Store(int xGridPos, int yGridPos, String name, Map map, GraphicsContext gc) {
        super(xGridPos, yGridPos, map, gc);
        this.setName(name);
    }

    @Override
    public void openObjective() {
        //System.out.println("In store: "+this.occupiedVehicle);
		if(!occupiedVehicle.isFull() && this.currentOrders!=0){
			this.showPrompt();
		}
	}

	@Override
	protected void doProcess(){
		this.makeOrder(this.occupiedVehicle);
	}
	
	// Special process called by doProcess upon completion of prompts
    private void makeOrder(Vehicle vehicle) {
   		this.currentOrders -= 1;	// Decrement the number of ongoing orders from households
        
   		// Making sure the order is properly labeled
		HashMap<String, Integer> vehicleStoreOrder = vehicle.getStoreOrder();
		if(vehicleStoreOrder.containsKey(this.name)) {
			vehicleStoreOrder.put(this.name, vehicleStoreOrder.get(this.name) + 1);
		} else {
			vehicleStoreOrder.put(this.name, 1);
		}

		vehicle.updateLoad(1); // Update Vehicle load by 1
		closePickUp();

   		System.out.println("Order from "+this.name+" was picked up by "+ vehicle);
    }
    
    public void openPickUp(PlayPane playPane) {
    	if(this.currentOrders == 1) {
    		this.bagPane = playPane;
    		bagPane.getChildren().add(orderBags);
    		
    		ImageView orderBag = new ImageView(bag);
    		orderBags.getChildren().add(orderBag);
    		orderBag.setTranslateX(this.xPos - 95);
    		orderBag.setTranslateY(this.yPos - 10);
    		orderBag.setScaleX(0.33);
    		orderBag.setScaleY(0.33);
    	}
    }
    
    private void closePickUp() {
    	if(currentOrders == 0) {	
    		this.bagPane.getChildren().remove(orderBags);
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
