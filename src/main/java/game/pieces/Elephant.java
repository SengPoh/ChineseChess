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
 * @version 29-6-2023
 */

public class Elephant extends Piece{

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Elephant(Board board, boolean isBlack)
    {
        super(board, isBlack);

        ArrayList<Location> moveSet = new ArrayList<>(Arrays.asList(
                new Location(2, 2),
                new Location(2, -2),
                new Location(-2, 2),
                new Location(-2, -2)
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
            if (isLegalMove(newLocation) && getBoard().isPalace(newLocation)) {
                legalMoves.add(newLocation);
            }
        }
        return legalMoves;
    }
}
