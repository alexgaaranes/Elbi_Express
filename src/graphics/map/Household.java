package graphics.map;

import java.util.HashMap;
import java.util.Random;

import game.Scoreboard;
import game.Timer;
import game.panes.PlayPane;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Household extends Objective{
	private String order;
	private boolean hasActiveOrder = false;
	private final static int orderInterval = 30;
	
	// Bar Attributes
	private Group timeBar = new Group();
	private Group orderBubbles = new Group();
	public final static float BAR_HEIGHT = 15f;
	public final static float BAR_WIDTH = 100f;
	private Rectangle redBar = new Rectangle(Household.BAR_WIDTH,Household.BAR_HEIGHT, Color.valueOf("8a1538"));
	private Rectangle greenBar = new Rectangle(Household.BAR_WIDTH,Household.BAR_HEIGHT,Color.valueOf("00573f"));
	private Rectangle barBorder = new Rectangle(Household.BAR_WIDTH,Household.BAR_HEIGHT);
	private Scene parentScene;
	private static Image jollibee = new Image(Household.class.getResource("/assets/sprites/jollibee.gif").toExternalForm());
	private static Image dominos = new Image(Household.class.getResource("/assets/sprites/domino's.gif").toExternalForm());
	private static Image dq = new Image(Household.class.getResource("/assets/sprites/dq.gif").toExternalForm());
	private static Image bk = new Image(Household.class.getResource("/assets/sprites/bk.gif").toExternalForm());
	
	

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
			map.getScoreboard().updateScore(1);
			System.out.println("Order Delivered by "+ vehicle);
		}
		this.hasActiveOrder = false;
    }

	// Will be called when activeOrder is false and at a random time stamp
	public void setActiveOrder() {
		// SetUp Order
		this.hasActiveOrder = true;
		Random r = new Random();
        int randomOrder = r.nextInt(Store.NUM_OF_STORES);
		Store storeToOrder = map.storeList.get(randomOrder);
        this.order = storeToOrder.getName();	// Get the store name where the household wants the order from
		storeToOrder.addOrder(1);

		System.out.println("Order Set "+this.order+" from "+this);
		
		// Set Up bar and bubbles
		PlayPane playPane = (PlayPane) this.parentScene.getRoot();
		playPane.getChildren().addAll(this.timeBar, orderBubbles);
		
		storeToOrder.openPickUp(playPane);
		
		ImageView order;
		if(randomOrder == 0) {
			order = new ImageView(jollibee);
		}
		else if(randomOrder == 1) {
			order = new ImageView(dominos);
		}
		else if(randomOrder == 2) {
			order = new ImageView(dq);
		}
		else {
			order = new ImageView(bk);
		}
		
		orderBubbles.getChildren().add(order);
		order.setTranslateX(this.xPos - 200);
		order.setTranslateY(this.yPos - 170);
		order.setScaleX(0.33);
		order.setScaleY(0.33);
		
		barBorder.setFill(Color.TRANSPARENT);
		barBorder.setStroke(Color.valueOf("ffb81c"));
		
		timeBar.getChildren().addAll(redBar, greenBar, barBorder);
		this.timeBar.setTranslateX(this.xPos + map.getTileH()/2 - Household.BAR_WIDTH/2);
		this.timeBar.setTranslateY(this.yPos + map.getTileH());

		// Timer for order
		Timer orderTimer = new Timer(Household.orderInterval);
		orderTimer.start();

		new AnimationTimer(){
			boolean isAngry = false;
			@Override
			public void handle(long l) {
				if(!hasActiveOrder) {
					timeBar.getChildren().removeAll(redBar, greenBar, barBorder);
					playPane.getChildren().removeAll(timeBar, orderBubbles);
					this.stop();
				}
				if(!this.isAngry && !orderTimer.getStatus()){
					System.out.println("House "+this+" is angry");
					this.isAngry = true;
					angryDamage();
				}
				// Update green bar width base on the time left by getting the percentage
				greenBar.setWidth(BAR_WIDTH - (((double) (Household.orderInterval - orderTimer.getTimeSec()) /
						Household.orderInterval)*BAR_WIDTH));
			}
		}.start();

	}

	private void angryDamage(){
		Scoreboard scoreboard = map.getScoreboard();
		scoreboard.reduceHappiness(5);
		new AnimationTimer(){
			long startTime = System.nanoTime();
			@Override
			public void handle(long l) {	// IF angry, reduce happiness by 1 every 3 sec
				if(!hasActiveOrder) {this.stop();}
				if(l - startTime >= 1000000000L){ // reduce 5 every sec
					scoreboard.reduceHappiness(5);
					startTime = l;
				}
			}
		}.start();
	}

	public boolean getHasActiveOrder() {
		return this.hasActiveOrder;
	}
}
