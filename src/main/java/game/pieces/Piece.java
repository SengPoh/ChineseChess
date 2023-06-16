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
    private boolean isBlack;    //to identify color of the piece.

    /**
     * Initialise the piece with its starting location.
     * @param location The starting location of the piece.
     * @param board The board that the piece is on.
     */
    public Piece(Location location, Board board)
    {
        setLocation(location);

        if (board == null) {
            throw new IllegalArgumentException("A piece must be assigned to a board.");
        }
        this.board = board;
    }

    /**
     * Set the location of the piece.
     * @param newLocation The new location of the piece.
     */
    protected void setLocation(Location newLocation)
    {
        location = newLocation;
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

    /** Returns a list of legal moves.
     * @return A list of legal moves.
     */
    abstract public ArrayList<Location> getMoves();

    /**
     * Returns true if moving this piece to the specified location is a legal move.
     * It is a legal move if the location is within the board and there is no other
     * piece of the same color on the location,
     * @param location The location this piece wants to move to.
     * @return True if it is a legal move.
     */
    protected boolean isLegalMove(Location location)
    {
        if (location == null || !board.isWithinBoard(location)) {
            return false;
        } else if (!board.isEmpty(location) && isSameColor(board.getPiece(location))) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if this piece is black color.
     * @return True if this is a black piece.
     */
    public boolean isBlack()
    {
        return isBlack;
    }

    /**
     * Returns true if the piece being compared has the same color as this piece.
     * @param otherPiece The piece that is being compared to this piece.
     * @return True if both pieces have the same color.
     */
    protected boolean isSameColor(Piece otherPiece)
    {
        return isBlack() == otherPiece.isBlack();
    }
}
