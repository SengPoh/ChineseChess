package game;

/**
 * Represents a location on the board that a game piece can be at.
 *
 * @author Lee Seng Poh
 * @version 19-6-2023
 */

public class Location {

    //X and Y coordinates
    private int x, y;

    /**
     * Represents the X and Y coordinates on the board.
     * @param x The X-coordinates.
     * @param y The Y-coordinates.
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X-coordinates.
     * @return The X-coordinates.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Returns the Y-coordinates.
     * @return The Y-coordinates.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Returns a location with the specified coordinates added to the coordinates
     * of this location.
     * @param x The X coordinate addition.
     * @param y The Y coordinate addition.
     * @return The location with added coordinates.
     */
    public Location add(int x, int y)
    {
        return new Location(getX() + x, getY() + y);
    }

    /**
     * Returns a location with the coordinates of the specified location added to the
     * coordinates of this location.
     * @param location The location whose coordinates are to be added
     * @return The location with added coordinates.
     */
    public Location add(Location location)
    {
        return add(location.getX(), location.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Location)) {
            return false;
        }

        Location loc = (Location) obj;

        // Compare the data members and return accordingly
        return this.getX() == loc.getX()
                && getY() == loc.getY();
    }
}
