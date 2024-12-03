package graphics.map;

import java.util.HashMap;
import java.util.Random;

import game.Timer;
import game.panes.PlayPane;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Household extends Objective{
	private String order;
	private boolean hasActiveOrder = false;
	private final static int orderInterval = 30;

	// Bar Attributes
	private Group timeBar = new Group();
	public final static float barHeight = 15f;
	public final static float barWidth = 100f;
	private Rectangle redBar = new Rectangle(Household.barWidth,Household.barHeight, Color.valueOf("8a1538"));
	private Rectangle greenBar = new Rectangle(Household.barWidth,Household.barHeight,Color.valueOf("00573f"));
	private Rectangle barBorder = new Rectangle(Household.barWidth,Household.barHeight);
	private Scene parentScene;

    Household(int xGridPos, int yGridPos, Map map, GraphicsContext gc) {
		super(xGridPos, yGridPos, map, gc);
		this.parentScene = map.getMapScene();
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
		// Set Up bar
		PlayPane playPane = (PlayPane) this.parentScene.getRoot();
		playPane.getChildren().add(this.timeBar);
		barBorder.setFill(Color.TRANSPARENT);
		barBorder.setStroke(Color.valueOf("ffb81c"));
		timeBar.getChildren().addAll(redBar, greenBar, barBorder);
		this.timeBar.setTranslateX(this.xPos + map.getTileH()/2 - Household.barWidth/2);
		this.timeBar.setTranslateY(this.yPos + map.getTileH());

		// SetUp Order
		this.hasActiveOrder = true;
		Random r = new Random();
        int randomOrder = r.nextInt(Store.NUM_OF_STORES);
		Store storeToOrder = map.storeList.get(randomOrder);
        this.order = storeToOrder.getName();	// Get the store name where the household wants the order from
		storeToOrder.addOrder(1);

		System.out.println("Order Set "+this.order+" from "+this);

		// Timer for order
		Timer orderTimer = new Timer(Household.orderInterval);
		orderTimer.start();

		new AnimationTimer(){
			boolean isAngry = false;
			@Override
			public void handle(long l) {
				if(!hasActiveOrder) {
					timeBar.getChildren().removeAll(redBar, greenBar, barBorder);
					playPane.getChildren().removeAll(timeBar);
					this.stop();
				}
				if(!this.isAngry && !orderTimer.getStatus()){
					System.out.println("House "+this+" is angry");
					this.isAngry = true;
					/*
					*  LOGIC FOR DMG OVER TIME
					* */
				}
				// Update green bar width base on the time left by getting the percentage
				greenBar.setWidth(100 - (((double) (Household.orderInterval - orderTimer.getTimeSec()) /
						Household.orderInterval)*100));
			}
		}.start();

	}

	public boolean getHasActiveOrder() {
		return this.hasActiveOrder;
	}
}
