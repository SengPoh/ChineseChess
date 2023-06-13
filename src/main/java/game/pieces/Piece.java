package game.pieces;

import game.Location;

/**
 * Represents a game piece.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */

public abstract class Piece {
    private Location location;

    abstract public void move();
}
