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

    @Override
    public ArrayList<Location> getMoves() {
        return null;
    }
}
