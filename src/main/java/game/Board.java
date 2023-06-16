package game;

import game.pieces.Piece;

/**
 * Represents the game board.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */
public class Board {

    //The length(vertical) and width(horizontal) of the board.
    private int length, width;

    //Storage for game pieces in a column by row array.
    private Piece[][] board;

    public Board(int width, int length)
    {
        this.width = width;
        this.length = length;
        board = new Piece[length][width];
    }

    /**
     * Returns the horizontal width of the board.
     * @return The horizontal width of the board.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the vertical length of the board.
     * @return The vertical length of the board.
     */
    public int getLength()
    {
        return length;
    }

    /**
     * Return true if the location is within the bounds of the board.
     * @param location Location to be checked.
     * @return True if the location is within the bounds of the board.
     */
    public boolean isWithinBoard(Location location)
    {
        if (location == null) {
            return false;
        } else if (location.getX() < 0 || location.getX() >= width) {
            return false;
        } else if (location.getY() < 0 || location.getY() >= length) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the location does not contain a piece and is within the board.
     * @param location The location to be checked.
     * @return True if the location does not contain a piece and is within the board.
     *          False if the location is null.
     */
    public boolean isEmpty(Location location)
    {
        if (location == null || !isWithinBoard(location)) {
            return false;
        } else if (board[location.getX()][location.getY()] != null) {
            return false;
        }

        return true;
    }
}
