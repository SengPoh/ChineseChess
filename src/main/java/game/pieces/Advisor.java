package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Advisor that moves and captures one space diagonally and
 * must stay within the palace.
 *
 * @author Lee Seng Poh
 * @version 29-6-2023
 */

public class Advisor extends Piece{

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Advisor(Board board, boolean isBlack)
    {
        super(board, isBlack);

        ArrayList<Location> moveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 1),
                new Location(1, -1),
                new Location(-1, 1),
                new Location(-1, -1)
        ));
        setMoveSet(moveSet);
    }

    /**
     * Returns a list of legal moves.
     * An advisor may move and capture one space diagonal, as long as it remains within
     * the palace.
     * @return A list of legal moves.
     */
    @Override
    public ArrayList<Location> getMoves() {
        ArrayList<Location> legalMoves = new ArrayList<>();

        //if this piece is not on the board.
        if (getLocation() == null) {
            return legalMoves;
        }

        for (Location move : getMoveSet()) {
            Location newLocation = getLocation().add(move);
            if (isLegalMove(newLocation)) {
                legalMoves.add(newLocation);
            }
        }
        return legalMoves;
    }

    /**
     * Returns true if moving this piece to the specified location is a legal move.
     * It is a legal move if the location is within the board and there is no other
     * piece of the same color on the location.
     * Additionally, it is only legal if the new location within the palace.
     * @param location The location this piece wants to move to.
     * @return True if it is a legal move.
     */
    @Override
    protected boolean isLegalMove(Location location)
    {
        return super.isLegalMove(location) && getBoard().isPalace(location);
    }
}
