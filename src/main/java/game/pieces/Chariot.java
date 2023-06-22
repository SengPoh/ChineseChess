package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Chariot that moves and captures any distance orthogonally,
 * but may not jump over intervening pieces.
 *
 * @author Lee Seng Poh
 * @version 22-6-2023
 */
public class Chariot extends Piece {

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Chariot(Board board, boolean isBlack)
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

    /** Returns a list of legal moves.
     *  A chariot can move and capture enemy pieces any distance orthogonally,
     *  but it cannot jump over intervening pieces.
     * @return A list of legal moves.
     */
    @Override
    public ArrayList<Location> getMoves()
    {
        ArrayList<Location> legalMoves = new ArrayList<>();

        for (Location move : getMoveSet()) {
            boolean captured = false;
            Location newLocation = getLocation().add(move);
            while (isLegalMove(newLocation) && !captured) {
                if (!getBoard().isEmpty(newLocation) && !isSameColor(getBoard().getPiece(newLocation))) {
                    legalMoves.add(newLocation);
                    captured = true;
                } else {
                    legalMoves.add(newLocation);
                }
                newLocation = newLocation.add(move);         //incrementing the move to check all distance.
            }
        }
        return legalMoves;
    }
}
