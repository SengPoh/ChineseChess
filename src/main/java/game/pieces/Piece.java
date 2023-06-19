package game.pieces;

import game.Location;
import game.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Represents a game piece.
 *
 * @author Lee Seng Poh
 * @version 18-6-2023
 */

public abstract class Piece {

    private Location location;
    private Board board;
    //to identify color of the piece.
    private boolean isBlack;
    //the available move set of the piece.
    private ArrayList<Location> moveSet = new ArrayList<>();

    /**
     * Initialise the piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     * @param board The board that the piece is on.
     * @param location The starting location of the piece.
     *
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Piece(Board board, Location location, boolean isBlack)
    {
        if (board == null) {
            throw new IllegalArgumentException("A piece must be assigned to a board.");
        }
        this.board = board;
        setLocation(location);
        this.isBlack = isBlack;
    }

    /**
     * Set the location of this piece.
     * @param newLocation The new location of this piece.
     *
     * @throws IllegalArgumentException if the location is neither null nor within the board.
     */
    protected void setLocation(Location newLocation)
    {
        if (location != null && !getBoard().isWithinBoard(newLocation)) {
            throw new IllegalArgumentException("location can only be either within the board or null.");
        }
        location = newLocation;
    }

    /**
     * Sets a new move set to this piece.
     * @param moveSet The new move set of this piece.
     */
    protected void setMoveSet(ArrayList<Location> moveSet)
    {
        this.moveSet = moveSet;
    }

    /**
     * Return the board this piece is on.
     * @return The board this piece is on.
     */
    protected Board getBoard()
    {
        return board;
    }

    /**
     * Return the location of  this piece.
     * @return The location of  this piece.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Returns the move set of this piece.
     * @return The move set of this piece.
     */
    protected ArrayList<Location> getMoveSet()
    {
        return moveSet;
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
        }

        return board.isEmpty(location) || !isSameColor(board.getPiece(location));
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
     * Returns true if  this piece being compared has the same color as this piece.
     * @param otherPiece The piece that is being compared to this piece.
     * @return True if both pieces have the same color.
     */
    protected boolean isSameColor(Piece otherPiece)
    {
        return isBlack() == otherPiece.isBlack();
    }
}
