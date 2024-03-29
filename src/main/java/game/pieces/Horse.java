package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Horse that moves and captures in an "L" shape:
 * two squares vertically and one square horizontally, or two squares horizontally and one square vertically.
 * A horse may be blocked by a piece next to it vertically or horizontally if it is in the horse's path.
 * A horse has a relative value of 8.
 *
 * @author Lee Seng Poh
 * @version 17-8-2023
 */

public class Horse extends Piece {

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Horse(Board board, boolean isBlack)
    {
        super(board, isBlack);

        ArrayList<Location> moveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 2),
                new Location(1, -2),
                new Location(-1, 2),
                new Location(-1, -2),
                new Location(2, 1),
                new Location(2, -1),
                new Location(-2, 1),
                new Location(-2, -1)
        ));
        setMoveSet(moveSet);
        setValue(8);
    }

    public ArrayList<Location> getMoves(){
        ArrayList<Location> legalMoves = new ArrayList<>();

        //If this piece is not on the board.
        if (getLocation() == null) {
            return legalMoves;
        }

        for (Location move : getMoveSet()) {
            Location newLocation = getLocation().add(move);
            if (isLegalMove(newLocation, move)) {
                legalMoves.add(newLocation);
            }
        }
        return legalMoves;
    }

    /**
     * Returns true if moving this piece to the specified location is a legal move.
     * It is a legal move if the location is within the board and there is no other
     * piece of the same color on the location.
     * Additionally, it is only legal if the move is not blocked by another piece.
     * @param location The location this piece wants to move to.
     * @param move The path this piece takes to get to the location.
     * @return True if it is a legal move.
     */
    protected boolean isLegalMove(Location location, Location move)
    {
        if (!super.isLegalMove(location)) {
            return false;
        }
        //whether it is blocked
        return getBoard().isEmpty(getBlockingPoint(move));
    }

    /**
     * Return the location where the specified move can be blocked by another piece.
     * @param move The move that is being attempted.
     * @return The location where the specified move can be blocked.
     */
    private Location getBlockingPoint(Location move)
    {
        return getLocation().add(move.getX() / 2, move.getY() /2);
    }

    @Override
    public Piece copy() {
        Horse copy = new Horse(this.getBoard(), this.isBlack());
        copy.setLocation(this.getLocation());
        copy.setMoveSet(this.getMoveSet());

        return copy;
    }

    /**
     * Returns true if the specified object is an instance of Horse and has the same location,
     * board and move set as this Horse.
     * @param obj The object to be tested.
     * @return True if the specified object is the same as this object. False otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Horse)) {
            return false;
        }

        Horse piece = (Horse) obj;

        boolean sameLocation;
        if (this.getLocation() == null) {
            sameLocation = (this.getLocation() == piece.getLocation());
        } else {
            sameLocation = this.getLocation().equals(piece.getLocation());
        }

        return sameLocation
                && this.getBoard() == piece.getBoard()
                && this.getMoveSet().equals(piece.getMoveSet());
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = result + getBoard().hashCode();
        result = result + getLocation().hashCode();
        result = result + getMoveSet().hashCode();
        return result;
    }
}
