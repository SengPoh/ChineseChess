package game;

import game.pieces.Piece;

/**
 * Represents the game board.
 *
 * @author Lee Seng Poh
 * @version 26-6-2023
 */
public class Board {

    //The width(horizontal) and length(vertical) of the board.
    private final int  width;
    private final int length;

    //Storage for game pieces in a column by row array.
    private Location[][] board;

    public Board(int width, int length)
    {
        if (width < 1 || length < 1) {
            throw new IllegalArgumentException("Width and length must be more than 1.");
        }
        this.width = width;
        this.length = length;
        setupBoard();
    }

    /**
     * Set specified piece at a location within the board if the location is not null,
     * is within the board and does not already have a piece.
     * @param piece The piece to be placed.
     * @param location The location of the piece.
     */
    public void setPiece(Piece piece, Location location)
    {
        if (location != null && isWithinBoard(location) && isEmpty(location)) {
            //to ensure that one piece is not in two different location.
            if (boardContains(piece)) {
                clearLocation(piece.getLocation());
            }
            Location boardLocation = getLocation(location);
            boardLocation.setPiece(piece);
            piece.setLocation(boardLocation);
        }
    }

    /**
     * Returns the piece at the specified location on the board, if there are any is
     * present there.
     * @param location The location of the piece.
     * @return The piece on the location.
     */
    public Piece getPiece(Location location)
    {
        if (location != null && isWithinBoard(location)) {
            return getLocation(location).getPiece();
        }

        return null;
    }

    /**
     * Remove any piece from the specified location on the board, if there are any is
     * present there.
     * @param location The location to be emptied.
     * @return The piece that is removed is there is any.
     */
    public Piece clearLocation(Location location)
    {
        Piece piece = null;
        if (location != null && isWithinBoard(location)) {
            piece = getLocation(location).removePiece();
        }
        return piece;
    }

    /**
     * Returns true if this board contains the specified piece on it.
     * @param piece Piece whose presence in the board is to be tested.
     * @return True if the board contains the specified piece.
     */
    private boolean boardContains(Piece piece)
    {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                 if (board[i][j].getPiece() == piece) {
                     return true;
                 }
            }
        }
        return false;
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
     * Returns true if the location on the board does not contain a piece.
     * @param location The location to be checked.
     * @return True if the location does not contain a piece.
     */
    public boolean isEmpty(Location location)
    {
        return getLocation(location).getPiece() == null;
    }

    private void setupBoard()
    {
        board = new Location[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = new Location(i, j);
            }
        }
    }

    /**
     * Returns the location stored within the board that is equivalent to
     * the specified location, if it is within the board.
     * @param location The location with the same x and y coordinates to the location to be obtained.
     * @return The location in the board equivalent to the specified location, if it is within
     *          the board.
     * @throws IllegalArgumentException if location is null or is not within the board.
     */
    private Location getLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        } else if (!isWithinBoard(location)) {
            throw new IllegalArgumentException("This location is not within the board.");
        }
        return board[location.getX()][location.getY()];
    }
}
