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
 * @version 14-8-2023
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

    @Test
    public void getMoves_EmptyBoard_LUnblocked()
    {
        Horse horse = new Horse(board, true);
        Location horseLocation = new Location(4, 5);
        board.setPiece(horse, horseLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(2, 4),
                new Location(2, 6),
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EmptyBoardCorner_LUnblocked()
    {
        Horse horse = new Horse(board, true);
        Location horseLocation = new Location(0, 0);
        board.setPiece(horse, horseLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(1, 2),
                new Location(2, 1)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AllyBlocking_LBlocked()
    {
        Horse horse = new Horse(board, true);
        Chariot allyChariot = new Chariot(board, true);
        Location horseLocation = new Location(4, 5);
        Location allyLocation = new Location(2,4);
        board.setPiece(horse, horseLocation);
        board.setPiece(allyChariot, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(2, 6),
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EnemyBlocking_LBlocked()
    {
        Horse horse = new Horse(board, true);
        Chariot enemyChariot = new Chariot(board, false);
        Location horseLocation = new Location(4, 5);
        Location enemyLocation = new Location(2,4);
        board.setPiece(horse, horseLocation);
        board.setPiece(enemyChariot, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(2, 4),
                new Location(2, 6),
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullAllyBlocking_LBlocked()
    {
        Horse horse = new Horse(board, true);
        Chariot allyChariot1 = new Chariot(board, true);
        Chariot allyChariot2 = new Chariot(board, true);
        Chariot allyChariot3 = new Chariot(board, true);
        Chariot allyChariot4 = new Chariot(board, true);
        Chariot allyChariot5 = new Chariot(board, true);
        Chariot allyChariot6 = new Chariot(board, true);
        Chariot allyChariot7 = new Chariot(board, true);
        Chariot allyChariot8 = new Chariot(board, true);
        Location horseLocation = new Location(4, 5);
        Location allyLocation1 = new Location(2,4);
        Location allyLocation2 = new Location(2, 6);
        Location allyLocation3 = new Location(6,4);
        Location allyLocation4 = new Location(6,6);
        Location allyLocation5 = new Location(3,3);
        Location allyLocation6 = new Location(3,7);
        Location allyLocation7 = new Location(5,3);
        Location allyLocation8 = new Location(5,7);
        board.setPiece(horse, horseLocation);
        board.setPiece(allyChariot1, allyLocation1);
        board.setPiece(allyChariot2, allyLocation2);
        board.setPiece(allyChariot3, allyLocation3);
        board.setPiece(allyChariot4, allyLocation4);
        board.setPiece(allyChariot5, allyLocation5);
        board.setPiece(allyChariot6, allyLocation6);
        board.setPiece(allyChariot7, allyLocation7);
        board.setPiece(allyChariot8, allyLocation8);

        ArrayList<Location> expectedResult = new ArrayList<>();

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullEnemyBlocking_LCapture()
    {
        Horse horse = new Horse(board, true);
        Chariot enemyChariot1 = new Chariot(board, false);
        Chariot enemyChariot2 = new Chariot(board, false);
        Chariot enemyChariot3 = new Chariot(board, false);
        Chariot enemyChariot4 = new Chariot(board, false);
        Chariot enemyChariot5 = new Chariot(board, false);
        Chariot enemyChariot6 = new Chariot(board, false);
        Chariot enemyChariot7 = new Chariot(board, false);
        Chariot enemyChariot8 = new Chariot(board, false);
        Location horseLocation = new Location(4, 5);
        Location enemyLocation1 = new Location(2,4);
        Location enemyLocation2 = new Location(2, 6);
        Location enemyLocation3 = new Location(6,4);
        Location enemyLocation4 = new Location(6,6);
        Location enemyLocation5 = new Location(3,3);
        Location enemyLocation6 = new Location(3,7);
        Location enemyLocation7 = new Location(5,3);
        Location enemyLocation8 = new Location(5,7);
        board.setPiece(horse, horseLocation);
        board.setPiece(enemyChariot1, enemyLocation1);
        board.setPiece(enemyChariot2, enemyLocation2);
        board.setPiece(enemyChariot3, enemyLocation3);
        board.setPiece(enemyChariot4, enemyLocation4);
        board.setPiece(enemyChariot5, enemyLocation5);
        board.setPiece(enemyChariot6, enemyLocation6);
        board.setPiece(enemyChariot7, enemyLocation7);
        board.setPiece(enemyChariot8, enemyLocation8);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(2, 4),
                new Location(2, 6),
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AllyBlockingPath_LBlocked()
    {
        Horse horse = new Horse(board, true);
        Chariot allyChariot = new Chariot(board, true);
        Location horseLocation = new Location(4, 5);
        Location allyLocation = new Location(3,5);
        board.setPiece(horse, horseLocation);
        board.setPiece(allyChariot, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EnemyBlockingPath_LBlocked()
    {
        Horse horse = new Horse(board, true);
        Chariot enemyChariot = new Chariot(board, false);
        Location horseLocation = new Location(4, 5);
        Location enemyLocation = new Location(3,5);
        board.setPiece(horse, horseLocation);
        board.setPiece(enemyChariot, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(6, 4),
                new Location(6, 6),
                new Location(3, 3),
                new Location(3, 7),
                new Location(5, 3),
                new Location(5, 7)
        ));

        ArrayList<Location> actualResult = horse.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(horse.getMoves()), "The lists of moves are different");
    }

    @Test
    public void move_LegalMove_True()
    {
        Horse horse = new Horse(board, true);
        Location location = new Location(2, 2);
        board.setPiece(horse, location);
        Location newLocation = new Location(0,1);

        assertTrue(horse.move(newLocation));
        assertEquals(newLocation, horse.getLocation(), "This horse was not moved to the correct location.");
    }

    @Test
    public void move_IllegalMove_False() {
        Horse horse = new Horse(board, true);
        Location location = new Location(2, 2);
        board.setPiece(horse, location);

        Location newLocation = new Location(6, 1);

        assertFalse(horse.move(newLocation));
        assertEquals(location, horse.getLocation(), "This horse's location was changed.");
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
    public void equals_NonSoldierObject_True()
    {
        Location loc = new Location(2, 8);
        Soldier Soldier1 = new Soldier(board, true);
        Soldier1.setLocation(loc);
        Object obj = new Object();
        assertNotEquals(Soldier1, obj);
    }

    @Test
    public void equals_DifferentLocations_True()
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