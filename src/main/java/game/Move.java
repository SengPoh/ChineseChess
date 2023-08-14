package game;

import game.pieces.Piece;

/**
 * Represents a move that is made during a game.

 * @author Lee Seng Poh
 * @version 14-8-2023
 */
public class Move {
    private Location moveFromLocation;
    private Location moveToLocation;
    private Piece piece;
    private Board board;

    public Move(Board board, Location moveFromLocation, Location moveToLocation)
    {
        if (board == null) {
            throw new IllegalArgumentException("The board cannot be null.");
        }

        if (moveFromLocation == null || !board.isWithinBoard(moveFromLocation) || moveToLocation == null
                || !board.isWithinBoard(moveToLocation)) {
            throw new IllegalArgumentException("The location parameter is invalid.");
        }

        if (board.isEmpty(moveFromLocation))
        {
            throw new IllegalArgumentException("The moveFromLocation does not have a piece to be moved.");
        }
        this.board = board;
        this.moveFromLocation = new Location(moveFromLocation);
        this.moveToLocation = new Location(moveToLocation);
        piece = board.getPiece(moveFromLocation);
    }

    /**
     * Returns the location from which the piece moves from.
     * @return the location from which the piece moves from.
     */
    public Location getMoveFromLocation()
    {
        return moveFromLocation;
    }

    /**
     * Returns the location from which the piece moves to.
     * @return the location from which the piece moves to.
     */
    public Location getMoveToLocation()
    {
        return moveToLocation;
    }

    /**
     * Returns true if this move can be made.
     * @return True if this move can be made.
     */
    public boolean canMove()
    {
        return board.getPiece(moveFromLocation).equals(piece) && piece.getMoves().contains(moveToLocation);
    }
}
