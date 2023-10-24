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
 * @version 14-8-2023
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
    public void init_Black() {
        Soldier soldier = new Soldier(board, true);

        assertEquals(board, soldier.getBoard(), "the board is different from expected");
        assertTrue(soldier.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>();
        expectedMoveSet.add(new Location(0, -1));

        ArrayList<Location> actualMoveSet = soldier.getMoveSet();
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }

    @Test
    public void init_Red() {
        Soldier soldier = new Soldier(board, false);

        assertEquals(board, soldier.getBoard(), "the board is different from expected");
        assertFalse(soldier.isBlack(), "the color is different from expected");

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
    public void getMoves_BlackBeforeRiverUnblocked_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setPiece(soldier, location);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location.add(0, -1));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedBeforeRiverUnblocked_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        board.setPiece(soldier, location);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location.add(0, 1));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_BlackAfterRiverUnblocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 4);
        board.setRiverEdge(newLocation, true);

        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,3),
                new Location(3,4),
                new Location(5,4)
        ));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedAfterRiverUnblocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        board.setRiverEdge(newLocation, true);

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
    public void getMoves_BlackBeforeRiverAllyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Soldier allySoldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        Location allyLocation = new Location(4, 4);
        board.setPiece(soldier, location);
        board.setPiece(allySoldier, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_BeforeRiverAllyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, false);
        Soldier allySoldier = new Soldier(board, false);
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
    public void getMoves_BlackBeforeRiverEnemyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, true);
        Soldier enemySoldier = new Soldier(board, false);
        Location location = new Location(4, 5);
        Location enemyLocation = new Location(4, 4);
        board.setPiece(soldier, location);
        board.setPiece(enemySoldier, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(enemyLocation);
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedBeforeRiverEnemyBlocking_OneSpaceForward()
    {
        Soldier soldier = new Soldier(board, false);
        Soldier enemySoldier = new Soldier(board, true);
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
    public void getMoves_BlackAfterRiverAllyBlocking_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 4);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier = new Soldier(board, true);
        Location allyLocation = new Location(4, 3);
        board.setPiece(allySoldier, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(3, 4),
                new Location(5, 4)
        ));
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedAfterRiverAllyBlocking_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier = new Soldier(board, false);
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
    public void getMoves_BlackAfterRiverEnemyBlocking_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 4);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier = new Soldier(board, false);
        Location enemyLocation = new Location(4, 3);
        board.setPiece(enemySoldier, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,3),
                new Location(3,4),
                new Location(5,4))
        );
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedAfterRiverEnemyBlocking_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier = new Soldier(board, true);
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
    public void getMoves_BlackAfterRiverFullyAllyBlocked_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 4);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier1 = new Soldier(board, true);
        Soldier allySoldier2 = new Soldier(board, true);
        Soldier allySoldier3 = new Soldier(board, true);
        Location allyLocation1 = new Location(4, 3);
        Location allyLocation2 = new Location(3, 4);
        Location allyLocation3 = new Location(5, 4);
        board.setPiece(allySoldier1, allyLocation1);
        board.setPiece(allySoldier2, allyLocation2);
        board.setPiece(allySoldier3, allyLocation3);

        ArrayList<Location> expectedResult = new ArrayList<>();
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedAfterRiverFullyAllyBlocked_HorizontalMoves()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        location.setRiverEdge(true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        newLocation.setRiverEdge(true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier allySoldier1 = new Soldier(board, false);
        Soldier allySoldier2 = new Soldier(board, false);
        Soldier allySoldier3 = new Soldier(board, false);
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
    public void getMoves_BlackAfterRiverFullyEnemyBlocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, true);
        Location location = new Location(4, 5);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 4);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier1 = new Soldier(board, false);
        Soldier enemySoldier2 = new Soldier(board, false);
        Soldier enemySoldier3 = new Soldier(board, false);
        Location enemyLocation1 = new Location(4, 3);
        Location enemyLocation2 = new Location(3, 4);
        Location enemyLocation3 = new Location(5, 4);
        board.setPiece(enemySoldier1, enemyLocation1);
        board.setPiece(enemySoldier2, enemyLocation2);
        board.setPiece(enemySoldier3, enemyLocation3);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(4,3),
                new Location(3,4),
                new Location(5,4))
        );
        ArrayList<Location> actualResult = soldier.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(soldier.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RedAfterRiverFullyEnemyBlocked_ForwardHorizontal()
    {
        Soldier soldier = new Soldier(board, false);
        Location location = new Location(4, 4);
        board.setRiverEdge(location, true);
        board.setPiece(soldier, location);
        Location newLocation = new Location(4, 5);
        board.setRiverEdge(newLocation, true);
        assertTrue(soldier.move(newLocation), "This piece did not move to new location");

        Soldier enemySoldier1 = new Soldier(board, true);
        Soldier enemySoldier2 = new Soldier(board, true);
        Soldier enemySoldier3 = new Soldier(board, true);
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
        Soldier soldier = new Soldier(board, false);
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

    @Test
    public void equals_EqualSoldier_True()
    {
        Location loc = new Location(2, 8);
        Soldier Soldier1 = new Soldier(board, true);
        Soldier1.setLocation(loc);
        Soldier Soldier2 = new Soldier(board, true);
        Soldier2.setLocation(loc);
        assertEquals(Soldier1, Soldier2);
    }

    @Test
    public void equals_NonSoldierObject_False()
    {
        Location loc = new Location(2, 8);
        Soldier Soldier1 = new Soldier(board, true);
        Soldier1.setLocation(loc);
        Object obj = new Object();
        assertNotEquals(Soldier1, obj);
    }

    @Test
    public void equals_DifferentLocations_False()
    {
        Soldier Soldier1 = new Soldier(board, true);
        Location loc = new Location(2, 8);
        Soldier1.setLocation(loc);
        Soldier Soldier2 = new Soldier(board, true);
        Location loc2 = new Location(3, 7);
        Soldier2.setLocation(loc2);
        assertNotEquals(Soldier1, Soldier2);
    }

    @Test
    public void hashCode_EqualSoldiers_Equals()
    {
        Location loc = new Location(2, 8);
        Soldier Soldier1 = new Soldier(board, true);
        Soldier1.setLocation(loc);
        Soldier Soldier2 = new Soldier(board, true);
        Soldier2.setLocation(loc);
        assertEquals(Soldier1.hashCode(), Soldier2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal()
    {
        Location loc = new Location(2, 8);
        Soldier Soldier = new Soldier(board, true);
        Soldier.setLocation(loc);
        int hashCode = Soldier.hashCode();
        assertEquals(Soldier.hashCode(), hashCode);
    }
}