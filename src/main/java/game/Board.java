package game;

import game.pieces.Piece;

/**
 * Represents the game board.
 *
 * @author Lee Seng Poh
 * @version 4-8-2023
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
     * Move a piece to a specified location if it is a legal move.
     * Returns true if the piece is moved.
     * @param piece The piece to be moved.
     * @param location The location to be moved to.
     * @return True if this piece is moved.
     */
    public boolean move(Piece piece, Location location)
    {
        boolean moved = false;
        if (piece != null && boardContains(piece)) {
            moved = piece.move(location);
        }
        return moved;
    }

    /**
     * Move a piece according to the specified move.
     * @param move The move to be made.
     * @return True if this piece is moved.
     */
    public boolean move(Move move)
    {
        boolean moved = false;
        if (move.canMove()) {
            Piece piece = getPiece(move.getMoveFromLocation());
            moved = move(piece, move.getMoveToLocation());
        }
        return moved;
    }

    /**
     * Set specified piece at a location within the board if the location is not null,
     * is within the board and does not already have a piece.
     * @param piece The piece to be placed.
     * @param location The location of the piece.
     */
    public void setPiece(Piece piece, Location location)
    {
        if (isWithinBoard(location) && isEmpty(location)) {
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
     * present there. Returns null if no piece is found.
     * @param location The location of the piece.
     * @return The piece on the location or null if there is none.
     */
    public Piece getPiece(Location location)
    {
        if (isWithinBoard(location)) {
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
        if (isWithinBoard(location)) {
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

    /**
     * Set whether the specified location is a palace location.
     * @param location The location to be modified.
     * @param isPalace Whether the location is a palace location.
     */
    public void setPalace(Location location, boolean isPalace)
    {
        getLocation(location).setPalace(isPalace);
    }

    /**
     * Set whether the specified location is at the edge of the river.
     * @param location The location to be modified.
     * @param isRiverEdge Whether this location is on the edge of the river.
     */
    public void setRiverEdge(Location location, boolean isRiverEdge)
    {
        getLocation(location).setRiverEdge(isRiverEdge);
    }

    /**
     * Returns true if this location is a palace location.
     * @return True if this location is a palace location.
     */
    public boolean isPalace(Location location)
    {
        return getLocation(location).isPalace();
    }

    /**
     * Returns true if this location is a location on the edge of the river.
     * @return True if this location is on the edge of the river.
     */
    public boolean isRiverEdge(Location location) {
        return getLocation(location).isRiverEdge();
    }

    /**
     * Fill board with the respective locations.
     */
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
    public Location getLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        } else if (!isWithinBoard(location)) {
            throw new IllegalArgumentException("This location is not within the board.");
        }
        return board[location.getX()][location.getY()];
    }

    /**
     * Replaces the location that has the same coordinates on the board with the specified location.
     * @param location The location to be stored.
     */
    public void setLocation(Location location)
    {
        if (isWithinBoard(location)) {
            board[location.getX()][location.getY()] = location;
        }
    }
}
