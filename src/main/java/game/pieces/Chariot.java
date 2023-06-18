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
 * @version 18-6-2023
 */
public class Chariot extends Piece{
    public Chariot(Location location, Board board) {
        super(location, board);

        ArrayList<Location> moveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 0),
                new Location(-1, 0),
                new Location(0, 1),
                new Location(0, -1)
        ));
        setMoveSet(moveSet);
    }

    /** Returns a list of legal moves.
     *  A chariot can move and capture enemy pieces in any direction orthogonally,
     *  but it cannot jump over intervening pieces.
     * @return A list of legal moves.
     */
    @Override
    public ArrayList<Location> getMoves() {
        ArrayList<Location> legalMoves = new ArrayList<>();

        for (Location move : getMoveSet()) {
            while (getBoard().isWithinBoard(move)) {
                while (isLegalMove(move)) {
                    legalMoves.add(move);
                    move.add(move);         //incrementing the move to check all distance.
                }
            }
        }

        return legalMoves;
    }
}
