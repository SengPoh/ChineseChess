package game.pieces;

import game.Location;
import game.Board;

import java.util.ArrayList;

/**
 * Represents a game piece. In this implementation, black pieces should be setup on the side of the river where
 * y-coordinates are greater than 4.
 *
 * @author Lee Seng Poh
 * @version 17-8-2023
 */

public abstract class Piece {

    private Location location;
    private Board board;
    //to identify color of the piece.
    private boolean isBlack;
    //the available move set of the piece.
    private ArrayList<Location> moveSet = new ArrayList<>();
    //The approximate value of the piece relative to other pieces.
    private int value;

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Piece(Board board, boolean isBlack)
    {
        if (board == null) {
            throw new IllegalArgumentException("A piece must be assigned to a board.");
        }
        this.board = board;
        this.isBlack = isBlack;
        this.location = null;
    }

    /**
     * Set the location of this piece.
     * @param newLocation The new location of this piece.
     *
     * @throws IllegalArgumentException if the location is neither null nor within the board.
     */
    public void setLocation(Location newLocation)
    {
        if (newLocation != null && !getBoard().isWithinBoard(newLocation)) {
            throw new IllegalArgumentException("Location can only be either within the board or null.");
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
    public Board getBoard()
    {
        return board;
    }

    /**
     * Return the location of  this piece.
     * @return The location of  this piece.
     */
    public Location getLocation()
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

    /**
     * Move this piece to a specified location if it is a legal move.
     * Returns true if the piece is moved.
     * @param location The location to be moved to.
     * @return True if this piece is moved.
     */
    public boolean move(Location location)
    {
        boolean moved = false;
        if (getMoves().contains(location)) {
            getBoard().clearLocation(location);
            getBoard().setPiece(this, location);
            moved = true;
        }
        return moved;
    }

    /**
     * Returns a list of legal moves.
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

    protected void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    /**
     * Returns a copy of this piece.
     * @return A copy of this piece.
     */
    abstract public Piece copy();
}
