package GUI;

import game.Location;
import javafx.scene.shape.Circle;

/**
 * A circle that represents a location on the board.
 */
public class LocationCircle extends Circle {
    private Location location;

    /**
     * Creates an empty instance of LocationCircle with its location.
     * @param location The location this circle represents.
     */
    public LocationCircle(Location location)
    {
        super();
        this.location = location;
    }

    /**
     * Creates a new instance of LocationCircle with a specified radius.
     * @param location The location this circle represents.
     */
    public LocationCircle(Location location, double radius)
    {
        super(radius);
        this.location = location;
    }

    /**
     * Returns the location this circle represents.
     * @return The location this circle represents.
     */
    public Location getLocation()
    {
        return location;
    }
}
