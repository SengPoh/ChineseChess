package game;

/**
 * Represents the game board.
 *
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */
public class Board {

    //The length(vertical) and width(horizontal) of the board.
    private int length, width;

    //Storage for game pieces.
    private Object[][] board;

    public Board(int length, int width)
    {
        this.length = length;
        this.width = width;
        board = new Object[length][width];
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
        } else if (location.getColumn() < 0 || location.getColumn() >= width) {
            return false;
        } else if (location.getRow() < 0 || location.getRow() >= length) {
            return false;
        }

        return true;
    }
}
