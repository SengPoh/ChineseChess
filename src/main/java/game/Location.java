package game;

/**
 * Represents a location a game piece can be at on the board.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
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
    }
}
