package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece General that moves and captures one space orthogonally and
 * must stay within the palace, with the following exception.
 * If the two generals face each other along the same file with no intervening pieces,
 * the general crosses the board to capture the enemy general.(Flying General move)
 *
 * @author Lee Seng Poh
 * @version 26-6-2023
 */
public class General extends Piece{

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public General(Board board, boolean isBlack)
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
     * A general may move and capture one space orthogonally, as long as it remains within
     * the palace, unless there are no intervening pieces between the two generals,
     * in which case the general can cross the board to capture the enemy general
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

        return legalMoves;
    }

    /**
     * Returns the location of the enemy general if flying general move if available.
     * Otherwise, return null.
     * @return Location of the enemy general if flying general move if available.
     *          Otherwise, null.
     */
    private Location flyTarget()
    {
        Location increment;
        // to check which side of the board this general is on
        if (getLocation().getY() < (getBoard().getLength() / 2)) {
            increment = new Location(0, 1);
        } else {
            increment = new Location(0, -1);
        }
        Location target = null;
        boolean blocked = false;
        Location newLocation = getLocation().add(increment);
        while (getBoard().isWithinBoard(newLocation) && !blocked) {
            if (getBoard().isEmpty(newLocation)) {
                newLocation = newLocation.add(increment);
            }
            Piece piece = getBoard().getPiece(newLocation);
            if (piece instanceof General && !isSameColor(piece)) {          //if it is the enemy general
                target = newLocation;
            } else {
                blocked = true;
            }
        }
        return target;
    }
}
