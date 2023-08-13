package game;

import game.pieces.Piece;
import javafx.scene.shape.MoveTo;

/**
 * Represents a move that is made during a game.

 * @author Lee Seng Poh
 * @version 13-8-2023
 */
public class move {
    private Location moveFromLocation;
    private Location moveToLocation;
    private Piece piece;
    private Board board;

    public move(Location moveFromLocation, Location moveToLocation)
    {
        if (moveFromLocation == null || moveToLocation == null) {
            throw new IllegalArgumentException("The locations entered cannot be null;");
        }

        if (this.moveFromLocation.getPiece() == null)
        {
            throw new IllegalArgumentException("The moveFromLocation does not have a piece to be moved.");
        }
        this.moveFromLocation = new Location(moveFromLocation);
        this.moveToLocation = new Location(moveToLocation);
        piece = moveFromLocation.getPiece();
        board = piece.getBoard();
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
