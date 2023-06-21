package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Chariot.java.
 *
 * @author Lee Seng Poh
 * @version 21-6-2023
 */

class ChariotTest {

    private Board board;
    private Location location;

    @BeforeEach
    void setUp()
    {
        board = new Board(9, 10);
    }

    @AfterEach
    void tearDown()
    {
        board = null;
    }

    @Test
    public void init() {
        Chariot chariot = new Chariot(board, true);

        assertEquals(board, chariot.getBoard(), "the board is different from expected");
        assertTrue(chariot.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 0),
                new Location(-1, 0),
                new Location(0, 1),
                new Location(0, -1)
        ));
        ArrayList<Location> actualMoveSet = chariot.getMoveSet();
        assertArrayEquals(expectedMoveSet.toArray(), actualMoveSet.toArray());
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Chariot(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Chariot chariot = new Chariot(board, true);
        chariot.setLocation(null);
        assertNull(chariot.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Chariot chariot = new Chariot(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> chariot.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Chariot chariot = new Chariot(board, true);

        assertTrue(chariot.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Chariot chariot = new Chariot(board, false);

        assertFalse(chariot.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Chariot chariot1 = new Chariot(board, true);
        Chariot chariot2 = new Chariot(board, true);
        assertTrue(chariot1.isSameColor(chariot2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Chariot chariot1 = new Chariot(board, false);
        Chariot chariot2 = new Chariot(board, false);
        assertTrue(chariot1.isSameColor(chariot2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Chariot chariot1 = new Chariot(board, false);
        Chariot chariot2 = new Chariot(board, true);
        assertFalse(chariot1.isSameColor(chariot2));
    }

    @Test
    public void getMoves_centerLocation_OrthogonalUnblocked()
    {

    }
}