package game;

import game.pieces.Piece;

import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private boolean isBlack;

    private Stack<Location> pastMoves;

    private ArrayList<Piece> pieces;

    /**
     * 
     * @param isBlack
     */
    public Player(boolean isBlack) {
        this.isBlack = isBlack;
        pastMoves = new Stack<>();
        pieces = new ArrayList<>();
    }

    private void addPiece(Piece piece)
    {
        pieces.add(piece);
    }

    private boolean removePiece(Piece piece)
    {
        return pieces.remove(piece);
    }
}
