package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Soldier.java.
 *
 * @author Lee Seng Poh
 * @version 23-6-2023
 */

class SoldierTest {

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
        Soldier soldier = new Soldier(board, true);

        assertEquals(board, soldier.getBoard(), "the board is different from expected");
        assertTrue(soldier.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>();
        expectedMoveSet.add(new Location(0, 1));

        ArrayList<Location> actualMoveSet = soldier.getMoveSet();;
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }



    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Soldier(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        Soldier soldier = new Soldier(board, true);
        soldier.setLocation(null);
        assertNull(soldier.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        Soldier soldier = new Soldier(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> soldier.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        Soldier soldier = new Soldier(board, true);

        assertTrue(soldier.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        Soldier soldier = new Soldier(board, false);

        assertFalse(soldier.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        Soldier soldier1 = new Soldier(board, true);
        Soldier soldier2 = new Soldier(board, true);
        assertTrue(soldier1.isSameColor(soldier2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        Soldier soldier1 = new Soldier(board, false);
        Soldier soldier2 = new Soldier(board, false);
        assertTrue(soldier1.isSameColor(soldier2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        Soldier soldier1 = new Soldier(board, false);
        Soldier soldier2 = new Soldier(board, true);
        assertFalse(soldier1.isSameColor(soldier2));
    }

    @Test
    public void getMoves_BeforeRiverUnblocked_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setPiece(soldier, location);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location.add(0, 1));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }
}