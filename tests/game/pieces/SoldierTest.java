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

        ArrayList<Location> actualMoveSet = soldier.getMoveSet();
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

    @Test
    public void getMoves_AfterRiverUnblocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);

        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,6),
                new Location(3,5),
                new Location(5,5)
        ));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_BeforeRiverAllyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Soldier allySoldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        Location allyLocation = new Location(4, 6);
        board.setPiece(soldier, location);
        board.setPiece(allySoldier, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_BeforeRiverEnemyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Soldier enemySoldier = new Soldier(board, false);
        Location location = new Location(4, 5);
        Location enemyLocation = new Location(4, 6);
        board.setPiece(soldier, location);
        board.setPiece(enemySoldier, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(enemyLocation);
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AfterRiverAllyBlocking_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier = new Soldier(board, true);
        Location allyLocation = new Location(4, 6);
        board.setPiece(allySoldier, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(3, 5),
                new Location(5, 5)
        ));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AfterRiverEnemyBlocking_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier = new Soldier(board, false);
        Location enemyLocation = new Location(4, 6);
        board.setPiece(enemySoldier, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,6),
                new Location(3,5),
                new Location(5,5))
        );
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AfterRiverFullyAllyBlocked_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier1 = new Soldier(board, true);
        Soldier allySoldier2 = new Soldier(board, true);
        Soldier allySoldier3 = new Soldier(board, true);
        Location allyLocation1 = new Location(4, 6);
        Location allyLocation2 = new Location(3, 5);
        Location allyLocation3 = new Location(5, 5);
        board.setPiece(allySoldier1, allyLocation1);
        board.setPiece(allySoldier2, allyLocation2);
        board.setPiece(allySoldier3, allyLocation3);

        ArrayList<Location> expectedResult = new ArrayList<>();
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AfterRiverFullyEnemyBlocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier1 = new Soldier(board, false);
        Soldier enemySoldier2 = new Soldier(board, false);
        Soldier enemySoldier3 = new Soldier(board, false);
        Location enemyLocation1 = new Location(4, 6);
        Location enemyLocation2 = new Location(3, 5);
        Location enemyLocation3 = new Location(5, 5);
        board.setPiece(enemySoldier1, enemyLocation1);
        board.setPiece(enemySoldier2, enemyLocation2);
        board.setPiece(enemySoldier3, enemyLocation3);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,6),
                new Location(3,5),
                new Location(5,5))
        );
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void move_LegalMove_True()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4,6);

        assertTrue(soldier.move(newLocation));
        assertEquals(newLocation, soldier.getLocation(), "This soldier was not moved to the correct location.");
    }

    @Test
    public void move_IllegalMove_False() {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setPiece(soldier, location);

        Location newLocation = new Location(2, 1);

        assertFalse(soldier.move(newLocation));
        assertEquals(location, soldier.getLocation(), "This soldier's location was changed.");
    }
}