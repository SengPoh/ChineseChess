package game.pieces;

import game.Board;
import game.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Cannon.java.
 *
 * @author Lee Seng Poh
 * @version 22-6-2023
 */

public class CannonTest {
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
        Cannon cannon = new Cannon(board, true);

        assertEquals(board, cannon.getBoard(), "the board is different from expected");
        assertTrue(cannon.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 0),
                new Location(-1, 0),
                new Location(0, 1),
                new Location(0, -1)
        ));
        ArrayList<Location> actualMoveSet = cannon.getMoveSet();
        assertArrayEquals(expectedMoveSet.toArray(), actualMoveSet.toArray(), "The move sets are different");
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Cannon(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Cannon cannon = new Cannon(board, true);
        cannon.setLocation(null);
        assertNull(cannon.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Cannon cannon = new Cannon(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> cannon.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Cannon cannon = new Cannon(board, true);

        assertTrue(cannon.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Cannon cannon = new Cannon(board, false);

        assertFalse(cannon.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Cannon cannon1 = new Cannon(board, true);
        Cannon cannon2 = new Cannon(board, true);
        assertTrue(cannon1.isSameColor(cannon2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Cannon cannon1 = new Cannon(board, false);
        Cannon cannon2 = new Cannon(board, false);
        assertTrue(cannon1.isSameColor(cannon2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Cannon cannon1 = new Cannon(board, false);
        Cannon cannon2 = new Cannon(board, true);
        assertFalse(cannon1.isSameColor(cannon2));
    }
}
