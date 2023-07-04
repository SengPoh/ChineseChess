package game;

import game.pieces.Piece;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents the player.
 */
public class Player {
    private boolean isBlack;

    private Stack<Location> pastMoves;

    private ArrayList<Piece> pieces;

    /**
     * Initialise the player with their color.
     * @param isBlack Whether this player is playing black side.
     */
    public Player(boolean isBlack) {
        this.isBlack = isBlack;
        pastMoves = new Stack<>();
        pieces = new ArrayList<>();
    }

    /**
     * Add a specified piece of the same color to this player.
     * @param piece The piece to be added.
     * @return True if the piece is successfully added.
     */
    {
        pieces.add(piece);
    }

    private boolean removePiece(Piece piece)
    /**
     * Remove a specified piece from this player.
     * @param piece The piece to be removed.
     * @return True if the piece is removed.
     */
    {
        return pieces.remove(piece);
    }
}
