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
    private Stack<Move> pastMoves;
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
     * Set whether this player has lost.
     * @param lost True if this player lost.
     */
    public void setLost(boolean lost)
    {
        this.lost = lost;
    }

    /**
     * Checks whether this player lost. If this player does not have a general, they lost.
     */
    public void checkLost()
    {
        boolean hasGeneral = false;
        for (Piece piece : pieces) {
            if (piece instanceof General) {
                hasGeneral = true;
            }
        }
        lost = !hasGeneral;
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
        return pieces.remove(piece);
    }

    /**
     * Records the move made by storing a copy of the locations that the piece moved from and to and their state.
     * @param moveFrom The location that the piece moved from.
     * @param moveTo The location that the piece moved to.
     */
    public void recordMove(Board board, Location moveFrom, Location moveTo)
    {
        if (moveFrom != null && moveTo != null) {
            Location moveFromLocation = new Location(moveFrom);
            Location moveToLocation = new Location(moveTo);
            Move move = new Move(board, moveFromLocation, moveToLocation);
            pastMoves.add(move);
        }
    }

    /**
     * Returns this player's last move.
     * @return This player's last move.
     */
    public Move popPreviousMove()
    {
        if (pastMoves.isEmpty()) {
            return null;
        }

        return pastMoves.pop();
    }

    public boolean hasMoved()
    {
        return !pastMoves.isEmpty();
    }

    /**
     * Returns the combined value of this player's pieces.
     * @return The combined value of this player's pieces.
     */
    public int getPieceValues()
    {
        int score = 0;
        for (Piece piece : pieces) {
            score = score + piece.getValue();
        }
        return score;
    }

    /**
     * Returns the color of the player in String format.
     * @return The color of the player in String format.
     */
    public String getColorString()
    {
        String color = "";
        if (isBlack) {
            color = "Black";
        } else {
            color = "Red";
        }
        return color;
    }
}
