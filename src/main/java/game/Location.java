package game;

import game.pieces.Piece;

/**
 * Represents a location on the board that a game piece can be at.
 *
 * @author Lee Seng Poh
 * @version 3-8-2023
 */

public class Location {

    //X and Y coordinates
    private int x, y;
    //the edge of the river
    private boolean isRiverEdge;
    //the area where the general and advisors stay
    private boolean isPalace;
    private Piece piece;

    /**
     * Represents the X and Y coordinates on the board.
     * @param x The X-coordinates.
     * @param y The Y-coordinates.
     */
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
        isRiverEdge = false;
        isPalace = false;
        piece = null;
    }

    public Location(Location location)
    {
        this.x = location.x;
        this.y = location.y;
        this.isRiverEdge = location.isRiverEdge;
        this.isPalace = location.isPalace;

        if (location.getPiece() != null) {
            this.piece = location.getPiece().copy();
            this.piece.setLocation(this);
        }
    }

    /**
     * Returns the X-coordinates.
     * @return The X-coordinates.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Returns the Y-coordinates.
     * @return The Y-coordinates.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Returns a location with the specified coordinates added to the coordinates
     * of this location.
     * @param x The X coordinate addition.
     * @param y The Y coordinate addition.
     * @return The location with added coordinates.
     */
    public Location add(int x, int y)
    {
        return new Location(getX() + x, getY() + y);
    }

    /**
     * Returns a location with the coordinates of the specified location added to the
     * coordinates of this location.
     * @param location The location whose coordinates are to be added
     * @return The location with added coordinates.
     */
    public Location add(Location location)
    {
        return add(location.getX(), location.getY());
    }


    /**
     * Set whether this location is a palace location.
     * @param isPalace Whether this location is a palace location.
     */
    public void setPalace(boolean isPalace) {
        this.isPalace = isPalace;
    }

    /**
     * Set whether this location is at the edge of the river.
     * @param isRiverEdge Whether this location is on the edge of the river.
     */
    public void setRiverEdge(boolean isRiverEdge) {
        this.isRiverEdge = isRiverEdge;
    }

    /**
     * Returns true if this location is a palace location.
     * @return True if this location is a palace location.
     */
    public boolean isPalace()
    {
        return isPalace;
    }

    /**
     * Returns true if this location is a location on the edge of the river.
     * @return True if this location is on the edge of the river.
     */
    public boolean isRiverEdge() {
        return isRiverEdge;
    }

    /**
     * Set specified piece on this location.
     * @param piece The piece to be placed.
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    /**
     * Returns the piece on this location.
     * @return The piece on this location, or null if there is no piece here.
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * Remove any piece from the specified location on the board, if there are any is
     * present there.
     * @return The piece that is removed is there is any.
     */
    public Piece removePiece()
    {
        Piece piece = this.piece;
//        if (piece != null) {
//            piece.setLocation(null);
//        }
        this.piece = null;
        return piece;
    }

    /**
     * Returns true if the specified object is an instance of Location and has the same x and y as this location.
     * @param obj The object to be tested.
     * @return True if the specified object is the same as this object. False otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Location)) {
            return false;
        }

        Location loc = (Location) obj;

        return this.getX() == loc.getX()
                && this.getY() == loc.getY();
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
}
