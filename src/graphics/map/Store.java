/**
 * The Store class represents a store that processes orders from households
 * and allows vehicles to pick up the orders. It tracks the number of orders,
 * the store's name, and handles the visual representation of orders on the map.
 * 
 * In the game, the Store can hold a certain number of orders and vehicles can
 * interact with the store to pick up orders for delivery.
 */

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

public class Store extends Objective {
    
    private String name; 
    private int currentOrders = 0; //Keeps track of orders from households to the store
    private Group orderBags = new Group(); //Holds the visual representation of the order bags
    private PlayPane bagPane; //The pane to display the orders in the play scene

    //STATIC CLASS ATTRIBUTES
    public static final int NUM_OF_STORES = 4;
    public static final String[] STORE_NAMES = {"Jollibee", "Domino's Pizza", "Dairy Queen", "Burger King"};
    private static Image bag = new Image(Store.class.getResource("/assets/sprites/order.gif").toExternalForm()); 

    /**
     * Constructs a Store object at a specific position in the map.
     * 
     * @param xGridPos the x-coordinate position on the grid
     * @param yGridPos the y-coordinate position on the grid
     * @param name the name of the store
     * @param map the map where the store is located
     * @param gc the graphics context for rendering
     */
    public Store(int xGridPos, int yGridPos, String name, Map map, GraphicsContext gc) {
        super(xGridPos, yGridPos, map, gc);
        this.setName(name);
    }

    /**
     * Opens the objective associated with the store and prompts the player
     * to interact if the store has orders and the vehicle is not full.
     */
    @Override
    public void openObjective() {
        if (!occupiedVehicle.isFull() && this.currentOrders != 0) {
            this.showPrompt(); //Show prompt for interaction
        }
    }

    /**
     * Processes the order when the player interacts with the store.
     * This method handles order completion by decrementing the current order count
     * and updating the vehicle's order list.
     */
    @Override
    protected void doProcess() {
        this.makeOrder(this.occupiedVehicle); //Completes the order for the vehicle
    }
    
    /**
     * Completes an order by decrementing the number of ongoing orders and
     * adding the order to the vehicle's order list. Also updates the vehicle's load.
     * 
     * @param vehicle the vehicle picking up the order
     */
    private void makeOrder(Vehicle vehicle) {
        this.currentOrders -= 1; //Decrement the number of ongoing orders from households
        
        //Ensures the order is properly labeled in the vehicle's order list
        HashMap<String, Integer> vehicleStoreOrder = vehicle.getStoreOrder();
        if (vehicleStoreOrder.containsKey(this.name)) {
            vehicleStoreOrder.put(this.name, vehicleStoreOrder.get(this.name) + 1);
        } else {
            vehicleStoreOrder.put(this.name, 1);
        }

        vehicle.updateLoad(1); //Update the vehicle's load by 1
        closePickUp(); //Closes the pickup visual

        System.out.println("Order from " + this.name + " was picked up by " + vehicle);
    }
    
    /**
     * Opens the pickup interface, allowing the vehicle to collect the order if 
     * there is at least one current order. Displays the order bag in the play pane.
     * 
     * @param playPane the play pane where the order bag is displayed
     */
    public void openPickUp(PlayPane playPane) {
        if (this.currentOrders == 1) {
            this.bagPane = playPane; 
            bagPane.getChildren().add(orderBags); 
            
            // Create an image view for the order bag and add it to the order bags group
            ImageView orderBag = new ImageView(bag);
            orderBags.getChildren().add(orderBag);
            orderBag.setTranslateX(this.xPos - 95); 
            orderBag.setTranslateY(this.yPos - 10); 
            orderBag.setScaleX(0.33); 
            orderBag.setScaleY(0.33); 
        }
    }
    
    /**
     * Closes the pickup interface if there are no current orders.
     */
    private void closePickUp() {
        if (currentOrders == 0) {
            this.bagPane.getChildren().remove(orderBags); // Remove the order bags from the pane
        }
    }

    /**
     * Gets the current number of orders at the store.
     * 
     * @return the number of current orders
     */
    public int getCurrentOrders() {
        return this.currentOrders;
    }

    /**
     * Adds orders to the store's order queue.
     * 
     * @param order the number of orders to add
     */
    public void addOrder(int order) {
        this.currentOrders += order; // Increment the current orders
    }

    /**
     * Gets the name of the store.
     * 
     * @return the name of the store
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the store.
     * 
     * @param name the name to set for the store
     */
    public void setName(String name) {
        this.name = name;
    }
}
