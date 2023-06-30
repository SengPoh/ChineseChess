package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Horse
 * .java.
 *
 * @author Lee Seng Poh
 * @version 30-6-2023
 */

class HorseTest {
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
        Horse horse = new Horse(board, true);

        assertEquals(board, horse.getBoard(), "the board is different from expected");
        assertTrue(horse.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 2),
                new Location(1, -2),
                new Location(-1, 2),
                new Location(-1, -2),
                new Location(2, 1),
                new Location(2, -1),
                new Location(-2, 1),
                new Location(-2, -1)
        ));
        ArrayList<Location> actualMoveSet = horse.getMoveSet();
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Horse horse = new Horse(board, true);
        horse.setLocation(null);
        assertNull(horse.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Horse horse = new Horse(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> horse.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Horse horse = new Horse(board, true);

        assertTrue(horse.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Horse horse = new Horse(board, false);

        assertFalse(horse.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Horse horse1 = new Horse(board, true);
        Horse horse2 = new Horse(board, true);
        assertTrue(horse1.isSameColor(horse2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Horse horse1 = new Horse(board, false);
        Horse horse2 = new Horse(board, false);
        assertTrue(horse1.isSameColor(horse2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Horse horse1 = new Horse(board, false);
        Horse horse2 = new Horse(board, true);
        assertFalse(horse1.isSameColor(horse2));
    }
}