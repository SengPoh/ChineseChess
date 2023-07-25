package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents game piece Soldier that moves and capture one space forward before crossing the river.
 * After crossing the river, it may move and capture forward or horizontally.
 * Soldiers cannot move backwards.
 *
 * @author Lee Seng Poh
 * @version 23-6-2023
 */

public class Soldier extends Piece {

    /**
     * Initialise this piece with its starting location and the board it is on.
     * The board cannot be null and the starting location must either be within the board or null.
     *
     * @param board The board that the piece is on.
     * @param isBlack Whether this piece is black in color.
     * @throws IllegalArgumentException if the board parameter is null.
     */
    public Soldier(Board board, boolean isBlack)
    {
        super(board, isBlack);

        ArrayList<Location> moveSet = new ArrayList<>();
        if (isBlack)
        {
            moveSet.add(new Location(0, -1));
        } else {
            moveSet.add(new Location(0, 1));
        }
        setMoveSet(moveSet);
    }

    /**
     * Move this piece to a specified location if it is a legal move.
     * Returns true if the piece is moved.
     * @param location The location to be moved to.
     * @return True if this piece is moved.
     */
    @Override
    public boolean move(Location location)
    {
        if (getLocation() == null) {
            return false;
        }

        Location oldLocation = getLocation();
        boolean moved = super.move(location);
        Location newLocation = getLocation();

        if (oldLocation.isRiverEdge() && moved && newLocation.isRiverEdge()) {
            crossRiver();
        }

        return moved;
    }

    /**
     * Returns a list of legal moves.
     * Before crossing the river, a soldier can only move and capture one space forward.
     * After crossing the river, a soldier can move and capture one space forward or horizontally.
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
            Location newLocation = getLocation().add(move);
            if (isLegalMove(newLocation)) {
                legalMoves.add(newLocation);
            }
        }
        return legalMoves;
    }

    /**
     * Changes the status of this soldier piece to having crossed the river.
     * The move set of this soldier is changed to being able to move forward and horizontally.
     */
    private void crossRiver()
    {
        ArrayList<Location> newMoveSet;
        if (isBlack()) {
            newMoveSet = new ArrayList<>(Arrays.asList(
                    new Location(1, 0),
                    new Location(-1, 0),
                    new Location(0, -1)
            ));
        } else {
            newMoveSet = new ArrayList<>(Arrays.asList(
                    new Location(1, 0),
                    new Location(-1, 0),
                    new Location(0, 1)
            ));
        }
        setMoveSet(newMoveSet);
    }
}
