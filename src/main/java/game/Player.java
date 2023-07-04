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
    public boolean addPiece(Piece piece)
    {
        boolean sameColor = false;
        if (piece != null && piece.isBlack() ==  this.isBlack) {
            pieces.add(piece);
            sameColor = true;
        }
        return sameColor;
    }

    /**
     * Remove a specified piece from this player.
     * @param piece The piece to be removed.
     * @return True if the piece is removed.
     */
    public boolean removePiece(Piece piece)
    {
        return pieces.remove(piece);
    }

    /**
     * Record a move that this player made.
     * @param move The move to be recorded.
     */
    public void recordMove(Location move)
    {
        if (move != null) {
            pastMoves.add(move);
        }
    }

    /**
     * Undo the previous move this player made and returns the move.
     * @return The previous move/
     */
    public Location undo()
    {
        return pastMoves.pop();
    }
}
