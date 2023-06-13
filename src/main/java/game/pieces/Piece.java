package game.pieces;

import game.Location;
import game.Board;

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
    private Board board;

    /**
     * Initialise the piece with its starting location.
     * @param location The starting location of the piece.
     * @param board The board that the piece is on.
     */
    public Piece(Location location, Board board)
    {
        setLocation(location);
    }

    // Returns a list of legal moves.
    abstract public ArrayList<Location> getMoves();

    //Set the location of the piece.
    protected void setLocation(Location newLocation)
    {
        if (newLocation != null) {
            location = newLocation;
        }
    }

    /**
     * Return the board the piece is on.
     * @return The board the piece is on.
     */
    protected Board getBoard()
    {
        return board;
    }

    /**
     * Return the location of the piece.
     * @return The location of the piece.
     */
    protected Location getLocation()
    {
        return location;
    }
}
