package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Elephant that moves and captures two space diagonally and
 * may not jump over intervening pieces.
 * Elephants may not cross the river.
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
        return null;
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
