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

    public Move(Piece piece, Location moveFromLocation, Location moveToLocation)
    {
        if (piece == null) {
            throw new IllegalArgumentException("The piece cannot be null.");
        }
        this.piece = piece;
        this.board = piece.getBoard();

        if (moveFromLocation == null || !board.isWithinBoard(moveFromLocation) || moveToLocation == null
                || !board.isWithinBoard(moveToLocation)) {
            throw new IllegalArgumentException("The location parameter is invalid.");
        }

        if (!piece.getLocation().equals(moveFromLocation))
        {
            throw new IllegalArgumentException("The moveFromLocation does not match the location of the piece.");
        }
        this.moveFromLocation = new Location(board.getLocation(moveFromLocation));
        this.moveToLocation = new Location(board.getLocation(moveToLocation));

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
