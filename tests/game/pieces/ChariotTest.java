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
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
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
    public void getMoves_emptyBoard_OrthogonalUnblocked()
    {
        Chariot chariot = new Chariot(board, true);
        Location chariotLocation = new Location(4, 5);
        board.setPiece(chariot, chariotLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < board.getLength(); i++) {
            Location tempLoc = new Location(4, i);
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, 5);
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = chariot.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(chariot.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_emptyBoardCorner_OrthogonalUnblocked()
    {
        Chariot chariot = new Chariot(board, true);
        Location chariotLocation = new Location(0, 0);
        board.setPiece(chariot, chariotLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < board.getLength(); i++) {
            Location tempLoc = new Location(chariotLocation.getX(), i);
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, chariotLocation.getY());
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = chariot.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(chariot.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AllyBlocking_OrthogonalBlocked()
    {
        Chariot chariot = new Chariot(board, true);
        Chariot allyChariot = new Chariot(board, true);
        Location chariotLocation = new Location(4, 5);
        Location allyLocation = new Location(4, 6);
        board.setPiece(chariot, chariotLocation);
        board.setPiece(allyChariot, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < allyLocation.getY(); i++) {
            Location tempLoc = new Location(chariotLocation.getX(), i);
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, chariotLocation.getY());
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = chariot.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(chariot.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EnemyBlocking_OrthogonalCapture()
    {
        Chariot chariot = new Chariot(board, true);
        Chariot enemyChariot = new Chariot(board, false);
        Location chariotLocation = new Location(4, 5);
        Location enemyLocation = new Location(4, 6);
        board.setPiece(chariot, chariotLocation);
        board.setPiece(enemyChariot, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i <= enemyLocation.getY(); i++) {
            Location tempLoc = new Location(chariotLocation.getX(), i);
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves to the left
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, chariotLocation.getY());
            if (!chariotLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = chariot.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(chariot.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullyAllyBlocked_NoMoves()
    {
        Chariot chariot = new Chariot(board, true);
        Chariot allyChariot1 = new Chariot(board, true);
        Chariot allyChariot2 = new Chariot(board, true);
        Chariot allyChariot3 = new Chariot(board, true);
        Chariot allyChariot4 = new Chariot(board, true);
        Location chariotLocation = new Location(4, 5);
        Location allyLocation1 = new Location(3, 5);
        Location allyLocation2 = new Location(5, 5);
        Location allyLocation3 = new Location(4, 6);
        Location allyLocation4 = new Location(4, 4);
        board.setPiece(chariot, chariotLocation);
        board.setPiece(allyChariot1, allyLocation1);
        board.setPiece(allyChariot2, allyLocation2);
        board.setPiece(allyChariot3, allyLocation3);
        board.setPiece(allyChariot4, allyLocation4);

        ArrayList<Location> actualResult = chariot.getMoves();
        assertTrue(actualResult.isEmpty(), "List of moves is not empty");
    }

    @Test
    public void getMoves_FullyEnemyBlocked_OnlyCaptureMoves()
    {
        Chariot chariot = new Chariot(board, true);
        Chariot enemyChariot1 = new Chariot(board, false);
        Chariot enemyChariot2 = new Chariot(board, false);
        Chariot enemyChariot3 = new Chariot(board, false);
        Chariot enemyChariot4 = new Chariot(board, false);
        Location chariotLocation = new Location(4, 5);
        Location enemyLocation1 = new Location(3, 5);
        Location enemyLocation2 = new Location(5, 5);
        Location enemyLocation3 = new Location(4, 6);
        Location enemyLocation4 = new Location(4, 4);
        board.setPiece(chariot, chariotLocation);
        board.setPiece(enemyChariot1, enemyLocation1);
        board.setPiece(enemyChariot2, enemyLocation2);
        board.setPiece(enemyChariot3, enemyLocation3);
        board.setPiece(enemyChariot4, enemyLocation4);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                enemyLocation1, enemyLocation2, enemyLocation3, enemyLocation4
        ));

        ArrayList<Location> actualResult = chariot.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(chariot.getMoves()), "The lists of moves are different");
    }

    @Test
    public void move_LegalMove_True()
    {
        Chariot chariot = new Chariot(board, true);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);
        Location newLocation = new Location(6,5);

        assertTrue(chariot.move(newLocation));
        assertEquals(newLocation, chariot.getLocation(), "This chariot was not moved to the correct location.");
    }

    @Test
    public void move_IllegalMove_False() {
        Chariot chariot = new Chariot(board, true);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);

        Location newLocation = new Location(2, 1);

        assertFalse(chariot.move(newLocation));
        assertEquals(location, chariot.getLocation(), "This chariot's location was changed.");
    }
}