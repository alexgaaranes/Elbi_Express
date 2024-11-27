package graphics.map;

import graphics.vehicles.Vehicle;
import javafx.geometry.Rectangle2D;

public interface Objective {
    void openObjective();

    Rectangle2D getBounds();
}
