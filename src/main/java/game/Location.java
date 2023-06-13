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

    /**
     * Returns the row.
     * @return The row.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Returns the column.
     * @return The column.
     */
    public int getColumn()
    {
        return column;
    }
}
