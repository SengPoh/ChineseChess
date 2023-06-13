package game.pieces;

import game.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Represents a game piece.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */

public abstract class Piece {
    private Location location;

    public Piece (Location location)
    {
        setLocation(location);
    }

    // Returns a list of legal moves.
    abstract public ArrayList<Location> getMoves();

    protected void setLocation(Location newLocation)
    {
        if (newLocation != null) {
            location = newLocation;
        }
    }
}
