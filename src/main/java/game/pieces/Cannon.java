package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Chariot that moves any distance orthogonally,
 * but can only capture by jumping a single piece of either colour along the path of attack.
 *
 * @author Lee Seng Poh
 * @version 22-6-2023
 */
public class Cannon extends Piece{

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Cannon(Board board, boolean isBlack)
    {
        super(board, isBlack);

        ArrayList<Location> moveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 0),
                new Location(-1, 0),
                new Location(0, 1),
                new Location(0, -1)
        ));
        setMoveSet(moveSet);
    }

    /**
     * Returns a list of legal moves.
     * A cannon can move any distance orthogonally, but can only capture by
     * jumping a single piece of either colour along the path of attack.
     * @return A list of legal moves.
     */
    @Override
    public ArrayList<Location> getMoves()
    {
        ArrayList<Location> legalMoves = new ArrayList<>();

        //if this piece is not on the board.
        if (getLocation() == null) {
            return legalMoves;
        }

        for (Location move : getMoveSet()) {
            boolean blocked = false;
            Location newLocation = getLocation().add(move);
            while (getBoard().isWithinBoard(newLocation) && !blocked) {
                if (!getBoard().isEmpty(newLocation)) {
                    blocked = true;
                } else {
                    legalMoves.add(newLocation);
                }
                newLocation = newLocation.add(move);         //incrementing the move to check all distance.
            }

            if (blocked) {
                Location captureTargetLocation = getCaptureLocation(move, newLocation);
                if (captureTargetLocation != null) {
                    legalMoves.add(captureTargetLocation);
                }
            }
        }

        return legalMoves;
    }

    /**
     * Returns location of an enemy piece that can be captured after this cannon is blocked by another piece,
     * or null if no target can be found.
     * @param direction The direction to search for target.
     * @param location The starting location of the search.
     * @return The location of a capture target, or null if no target can be found.
     */
    private Location getCaptureLocation(Location direction, Location location)
    {
        Location captureTargetLocation = null;
        boolean found = false;

        while (isLegalMove(location) && !found) {            //loop until illegal move or found a piece.
            if (getBoard().isEmpty(location)) {
                location = location.add(direction);       //continue if the location is empty
            } else if (!isSameColor(getBoard().getPiece(location))) {        //if it is an enemy piece.
                captureTargetLocation = location;
                found = true;
            }
        }
        return captureTargetLocation;
    }
}
