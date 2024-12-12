/**
 * The Household class represents a household objective in the game.
 * It handles the order logic for a household, including assigning random orders to households, processing deliveries,
 * and managing time-based bar indicators for order completion.
 */

package graphics.map;

import java.util.HashMap;
import java.util.Random;

import game.Audio;
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

public class Household extends Objective {
    private String order; // The current order for the household.
    private boolean hasActiveOrder = false; // Indicates whether there is an active order to process.
    private final static int orderInterval = 30; // Time in seconds for the order to be processed before it expires.

    // Bar Attributes for order progress visualization
    private Group timeBar = new Group(); // Group containing the bar components.
    private Group orderBubbles = new Group(); // Group containing order visual indicators.
    public final static float BAR_HEIGHT = 15f; // Height of the progress bar.
    public final static float BAR_WIDTH = 100f; // Width of the progress bar.
    private Rectangle redBar = new Rectangle(Household.BAR_WIDTH, Household.BAR_HEIGHT, Color.valueOf("8a1538")); // Background red bar.
    private Rectangle greenBar = new Rectangle(Household.BAR_WIDTH, Household.BAR_HEIGHT, Color.valueOf("00573f")); // Progress bar.
    private Rectangle barBorder = new Rectangle(Household.BAR_WIDTH, Household.BAR_HEIGHT); // Border for the bar.
    private Scene parentScene; // The parent scene that holds the HUD elements.

    // Images representing different stores
    private static Image jollibee = new Image(Household.class.getResource("/assets/sprites/jollibee.gif").toExternalForm());
    private static Image dominos = new Image(Household.class.getResource("/assets/sprites/domino's.gif").toExternalForm());
    private static Image dq = new Image(Household.class.getResource("/assets/sprites/dq.gif").toExternalForm());
    private static Image bk = new Image(Household.class.getResource("/assets/sprites/bk.gif").toExternalForm());

    /**
     * Constructor for the Household class.
     * Initializes the household position and map for order management.
     * @param xGridPos The x-coordinate in the grid.
     * @param yGridPos The y-coordinate in the grid.
     * @param map The game map.
     * @param gc The GraphicsContext for drawing elements.
     */
    Household(int xGridPos, int yGridPos, Map map, GraphicsContext gc) {
        super(xGridPos, yGridPos, map, gc);
        this.parentScene = map.getMapScene();
    }

    /**
     * Opens the objective for the household, triggering the order prompt if the household has an active order.
     */
    @Override
    public void openObjective() {
        if (this.hasActiveOrder && this.occupiedVehicle.getStoreOrder().containsKey(this.order)) {
            this.showPrompt();
        }
    }

    /**
     * Processes the order by delivering it through the occupied vehicle.
     * If the vehicle's order matches the household's order, the delivery is completed.
     */
    @Override
    protected void doProcess() {
        this.getDeliver(this.occupiedVehicle);
    }

    /**
     * Delivers the order to the household and updates the vehicle's load and score.
     * If the order has been fully delivered, it is removed from the vehicle's order list.
     * @param vehicle The vehicle that is making the delivery.
     */
    private void getDeliver(Vehicle vehicle) {
        HashMap<String, Integer> vehicleStoreOrder = vehicle.getStoreOrder();

        if (vehicleStoreOrder.containsKey(this.order)) {
            if (vehicleStoreOrder.get(this.order) > 1) {
                vehicleStoreOrder.put(this.order, vehicleStoreOrder.get(this.order) - 1); // Reduce the vehicle load of the order.
            } else {
                vehicleStoreOrder.remove(this.order); // Remove the load if all are unloaded.
            }
            vehicle.updateLoad(-1);
            vehicle.updateScore(1);
            map.getScoreboard().updateScore(1);
			Audio.playClip("deliver", 0.25);
            System.out.println("Order Delivered by " + vehicle);
        }
        this.hasActiveOrder = false; // No active order after delivery.
    }

    /**
     * Sets an active order for the household by randomly selecting a store and generating a new order.
     * A timer is started for the order delivery, and visual indicators are shown.
     */
    public void setActiveOrder() {
        this.hasActiveOrder = true;
        Random r = new Random();
        int randomOrder = r.nextInt(Store.NUM_OF_STORES); // Randomly select a store.
        Store storeToOrder = map.storeList.get(randomOrder);
        this.order = storeToOrder.getName(); // Set the store name for the household's order.
        storeToOrder.addOrder(1);

        System.out.println("Order Set " + this.order + " from " + this);

        // Set Up bar and order bubbles
        PlayPane playPane = (PlayPane) this.parentScene.getRoot();
        playPane.getChildren().addAll(this.timeBar, orderBubbles);

        storeToOrder.openPickUp(playPane);

        // Display the order bubble for the store
        ImageView order;
        if (randomOrder == 0) {
            order = new ImageView(jollibee);
        } else if (randomOrder == 1) {
            order = new ImageView(dominos);
        } else if (randomOrder == 2) {
            order = new ImageView(dq);
        } else {
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
        this.timeBar.setTranslateX(this.xPos + map.getTileH() / 2 - Household.BAR_WIDTH / 2);
        this.timeBar.setTranslateY(this.yPos + map.getTileH());

        // Timer for order completion
        Timer orderTimer = new Timer(Household.orderInterval);
        orderTimer.start();

        new AnimationTimer() {
            boolean isAngry = false;

            @Override
            public void handle(long l) {
                if (!hasActiveOrder) {
                    timeBar.getChildren().removeAll(redBar, greenBar, barBorder);
                    playPane.getChildren().removeAll(timeBar, orderBubbles);
                    this.stop();
                }
                if (!this.isAngry && !orderTimer.getStatus()) {
                    System.out.println("House " + this + " is angry");
                    this.isAngry = true;
                    angryDamage();
                }
                // Update green bar width based on time left
                greenBar.setWidth(BAR_WIDTH - (((double) (Household.orderInterval - orderTimer.getTimeSec()) /
                        Household.orderInterval) * BAR_WIDTH));
            }
        }.start();
    }

    /**
     * Reduces the happiness of the scoreboard by a fixed amount every second if the order has not been delivered on time.
     */
    private void angryDamage() {
        Scoreboard scoreboard = map.getScoreboard();
        scoreboard.reduceHappiness(5);
        new AnimationTimer() {
            long startTime = System.nanoTime();

            @Override
            public void handle(long l) { //Reduce happiness by 5 every second
                if (!hasActiveOrder) {
                    this.stop();
                }
                if (l - startTime >= 1000000000L) { //1 second elapsed
                    scoreboard.reduceHappiness(5);
                    startTime = l;
                }
            }
        }.start();
    }

    /**
     * Returns whether the household has an active order.
     * @return True if the household has an active order, false otherwise.
     */
    public boolean getHasActiveOrder() {
        return this.hasActiveOrder;
    }
}
