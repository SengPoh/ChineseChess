package game;

import game.pieces.General;
import game.pieces.Piece;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents the player.

 * @author Lee Seng Poh
 * @version 31-7-2023
 */
public class Player {
    private boolean isBlack;
    private boolean lost;
    private Stack<Location> pastMoves;
    private ArrayList<Piece> pieces;

    /**
     * Initialise the player with their color.
     * @param isBlack Whether this player is playing black side.
     */
    public Player(boolean isBlack) {
        this.isBlack = isBlack;
        lost = false;
        pastMoves = new Stack<>();
        pieces = new ArrayList<>();
    }

    /**
     * Returns true if this player is on the black side.
     * @return True if this player is on the black side.
     */
    public boolean isBlack()
    {
        return isBlack;
    }

    /**
     * Returns true if this player has lost.
     * @return True if this player has lost.
     */
    public boolean lost()
    {
        return lost;
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
     * Remove a specified piece from this player. If the piece is a general, the player has lost.
     * @param piece The piece to be removed.
     * @return True if the piece is removed.
     */
    public boolean removePiece(Piece piece)
    {
        if (piece instanceof General) {
            lost = true;
        }
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
