package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Advisor.java.
 *
 * @author Lee Seng Poh
 * @version 29-6-2023
 */

class AdvisorTest {
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
        Advisor advisor = new Advisor(board, true);

        assertEquals(board, advisor.getBoard(), "the board is different from expected");
        assertTrue(advisor.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 1),
                new Location(1, -1),
                new Location(-1, 1),
                new Location(-1, -1)
        ));
        ArrayList<Location> actualMoveSet = advisor.getMoveSet();
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Advisor(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Advisor advisor = new Advisor(board, true);
        advisor.setLocation(null);
        assertNull(advisor.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Advisor advisor = new Advisor(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> advisor.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Advisor advisor = new Advisor(board, true);

        assertTrue(advisor.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Advisor advisor = new Advisor(board, false);

        assertFalse(advisor.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Advisor advisor1 = new Advisor(board, true);
        Advisor advisor2 = new Advisor(board, true);
        assertTrue(advisor1.isSameColor(advisor2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Advisor advisor1 = new Advisor(board, false);
        Advisor advisor2 = new Advisor(board, false);
        assertTrue(advisor1.isSameColor(advisor2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Advisor advisor1 = new Advisor(board, false);
        Advisor advisor2 = new Advisor(board, true);
        assertFalse(advisor1.isSameColor(advisor2));
    }
}