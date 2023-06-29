package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Elephant.java.
 *
 * @author Lee Seng Poh
 * @version 29-6-2023
 */
class ElephantTest {
    private Board board;

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
        Elephant elephant = new Elephant(board, true);

        assertEquals(board, elephant.getBoard(), "the board is different from expected");
        assertTrue(elephant.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(2, 2),
                new Location(2, -2),
                new Location(-2, 2),
                new Location(-2, -2)
        ));
        ArrayList<Location> actualMoveSet = elephant.getMoveSet();
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Elephant(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Elephant elephant = new Elephant(board, true);
        elephant.setLocation(null);
        assertNull(elephant.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Elephant elephant = new Elephant(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> elephant.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Elephant elephant = new Elephant(board, true);

        assertTrue(elephant.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Elephant elephant = new Elephant(board, false);

        assertFalse(elephant.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Elephant elephant1 = new Elephant(board, true);
        Elephant elephant2 = new Elephant(board, true);
        assertTrue(elephant1.isSameColor(elephant2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Elephant elephant1 = new Elephant(board, false);
        Elephant elephant2 = new Elephant(board, false);
        assertTrue(elephant1.isSameColor(elephant2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Elephant elephant1 = new Elephant(board, false);
        Elephant elephant2 = new Elephant(board, true);
        assertFalse(elephant1.isSameColor(elephant2));
    }
}