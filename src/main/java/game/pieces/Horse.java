package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Horse that moves and captures in an "L" shape:
 * two squares vertically and one square horizontally, or two squares horizontally and one square vertically.
 * A horse may be blocked by a piece next to it vertically or horizontally if it is in the horse's path.
 *
 * @author Lee Seng Poh
 * @version 30-6-2023
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
        Location blockingPoint;
        if (move.getX() == 2) {
            blockingPoint = getLocation().add(1, 0);
        } else {
            blockingPoint = getLocation().add(0, 1);
        }
        return blockingPoint;
    }
}
