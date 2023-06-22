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
    public Cannon(Board board, boolean isBlack) {
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
     *  A cannon can move any distance orthogonally, but can only capture by
     *  jumping a single piece of either colour along the path of attack.
     * @return A list of legal moves.
     */
    @Override
    public ArrayList<Location> getMoves() {
        ArrayList<Location> legalMoves = new ArrayList<>();

        return legalMoves;
    }
}
