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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Household extends Objective{
	private String order;
	private boolean hasActiveOrder = false;
	private final static int orderInterval = 30;
	private Group timeBar = new Group();
	private Rectangle redBar = new Rectangle(100,10, Color.RED);
	private Rectangle greenBar = new Rectangle(100,10,Color.GREEN);
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
		try {
			PlayPane playPane = (PlayPane) this.parentScene.getRoot();
			playPane.getChildren().add(this.timeBar);
			timeBar.getChildren().addAll(redBar, greenBar);
			Image barFrame = new Image("file:src/assets/sprites/bars/barFrame.png");
			this.timeBar.setTranslateX(this.xPos - map.getTileW() / 2);
			this.timeBar.setTranslateY(this.yPos + map.getTileH());
			gc.drawImage(barFrame,
					this.xPos + 10, this.yPos + 10, barFrame.getWidth(), barFrame.getHeight()
			);
			this.hasActiveOrder = true;
		} catch (Exception e) {
			System.out.println("Encountered duplicate but hopefully resolved.");
			e.printStackTrace();
			return;	// Stop from proceeding
		}
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
			@Override
			public void handle(long l) {
				if(!hasActiveOrder) {
					timeBar.getChildren().removeAll(redBar, greenBar);
					this.stop();
				}
				if(!orderTimer.getStatus()){
					System.out.println("House "+this+" is angry");
					/*
					*  LOGIC FOR DMG OVER TIME
					* */
					this.stop();
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
