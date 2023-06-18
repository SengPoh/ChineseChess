package game.pieces;

import game.Board;
import game.Location;

import java.util.ArrayList;

/**
 * Represents game piece Chariot that moves in a straight line.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */
public class Chariot extends Piece{
    public Chariot(Location location, Board board) {
        super(location, board);
    }

    @Override
    public ArrayList<Location> getMoves() {
        ArrayList<Location> legalMoves = new ArrayList<>();


        return legalMoves;
    }
}
