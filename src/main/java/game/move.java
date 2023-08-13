package game;

import game.pieces.Piece;

/**
 * Represents a move that is made during a game.

 * @author Lee Seng Poh
 * @version 13-8-2023
 */
public class move {
    Location moveFromLocation;
    Location moveToLocation;
    Piece piece;

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
        this.piece = moveFromLocation.getPiece();
    }
    
}
