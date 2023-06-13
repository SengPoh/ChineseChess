package game;

/**
 * Represents a location a game piece can be at on the board.
 *
 * @author Lee Seng Poh
 * @version 13-6-2023
 */

public class Location {
    private int row;
    private int column;

    /**
     * Represents the row and the column on the board.
     * @param row The row.
     * @param column The column.
     */
    public Location(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }
}
