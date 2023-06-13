package game;

/**
 * Represents the game board.
 *
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */
public class Board {

    //The length and width of the board.
    private int length, width;

    //Storage for game pieces.
    private Object[][] board;

    public Board(int length, int width)
    {
        this.length = length;
        this.width = width;
        board = new Object[length][width];
    }
}
