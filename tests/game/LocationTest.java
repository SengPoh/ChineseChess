package game;

import game.pieces.Chariot;
import game.pieces.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Location.java.
 *
 * @author Lee Seng Poh
 * @version 20-6-2023
 */
class LocationTest {

    @Test
    public void init()
    {
        Location location = new Location(5, 10);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
        assertFalse(location.isPalace(), "Initialised as a palace location");
        assertFalse(location.isRiverEdge(), "Initialised as a river edge location");
        assertNull(location.getPiece());
    }

    @Test
    public void add_coordinates()
    {
        Location location = new Location(5, 10);
        Location result = location.add(6, 12);
        assertEquals(result.getX(), 11);
        assertEquals(result.getY(), 22);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void add_minusCoordinates()
    {
        Location location = new Location(5, 10);
        Location result = location.add(-6, -12);
        assertEquals(result.getX(), -1);
        assertEquals(result.getY(), -2);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }


    @Test
    public void add_location()
    {
        Location location = new Location(5, 10);
        Location location2 = new Location(3, 2);
        Location result = location.add(location2);
        assertEquals(result.getX(), 8);
        assertEquals(result.getY(), 12);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void add_minusLocation()
    {
        Location location = new Location(5, 10);
        Location location2 = new Location(-3, -2);
        Location result = location.add(location2);
        assertEquals(result.getX(), 2);
        assertEquals(result.getY(), 8);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void setPalace_True()
    {
        Location location = new Location(4, 1);
        location.setPalace(true);
        assertTrue(location.isPalace());
    }

    @Test
    public void setPalace_False()
    {
        Location location = new Location(4, 1);
        location.setPalace(false);
        assertFalse(location.isPalace());
    }

    @Test
    public void setRiverEdge_True()
    {
        Location location = new Location(3, 4);
        location.setRiverEdge(true);
        assertTrue(location.isRiverEdge());
    }

    @Test
    public void setRiverEdge_False()
    {
        Location location = new Location(3, 4);
        location.setRiverEdge(false);
        assertFalse(location.isRiverEdge());
    }

    @Test
    public void setPiece()
    {
        Location location = new Location(3, 4);
        Piece piece = new Chariot(new Board(8, 9), true);
        location.setPiece(piece);
        assertEquals(piece, location.getPiece());
    }

    @Test
    public void removePiece_NotEmpty()
    {
        Location location = new Location(3, 4);
        Piece piece = new Chariot(new Board(8, 9), true);
        location.setPiece(piece);
        assertEquals(piece, location.removePiece(),"The piece returned is not as expected.");
        assertNull(location.getPiece(),"Location still has a piece");
        assertNull(piece.getLocation(),"The piece still has a location assigned");
    }

    @Test
    public void removePiece_Empty()
    {
        Location location = new Location(3, 4);
        assertNull(location.removePiece());
    }

    @Test
    public void equals_EqualLocation_True() {
        Location loc1 = new Location(5, 5);
        Location loc2 = new Location(5, 5);
        assertEquals(loc1, loc2);
    }

    @Test
    public void equals_NonLocationObject_False() {
        Location loc1 = new Location(5, 5);
        Object obj = new Object();
        assertNotEquals(loc1, obj);
    }

    @Test
    public void equals_SameXDistinctY_False() {
        Location loc1 = new Location(5, 1);
        Location loc2 = new Location(5, 8);
        assertNotEquals(loc1, loc2);
    }

    @Test
    public void equals_DistinctXSameY_False() {
        Location loc1 = new Location(3, 8);
        Location loc2 = new Location(25, 8);
        assertNotEquals(loc1, loc2);
    }

    @Test
    public void hashCode_EqualLocation_Equals() {
        Location loc1 = new Location(5, 5);
        Location loc2 = new Location(5, 5);
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal() {
        Location loc1 = new Location(12, 3);
        int hashCode = loc1.hashCode();
        assertEquals(hashCode, loc1.hashCode());
    }
}