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
 * @version 14-8-2023
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
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
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

    @Test
    public void getMoves_emptyBoard_OrthogonalUnblocked()
    {
        Cannon cannon = new Cannon(board, true);
        Location cannonLocation = new Location(4, 5);
        board.setPiece(cannon, cannonLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < board.getLength(); i++) {
            Location tempLoc = new Location(4, i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, 5);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_emptyBoardCorner_OrthogonalUnblocked()
    {
        Cannon cannon = new Cannon(board, true);
        Location cannonLocation = new Location(0, 0);
        board.setPiece(cannon, cannonLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < board.getLength(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AllyBlocking_OrthogonalBlocked()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon allyCannon = new Cannon(board, true);
        Location cannonLocation = new Location(4, 5);
        Location allyLocation = new Location(4, 6);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(allyCannon, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < allyLocation.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_SingleEnemyBlocking_OrthogonalBlocked()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon enemyCannon = new Cannon(board, false);
        Location cannonLocation = new Location(4, 5);
        Location enemyLocation = new Location(4, 6);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(enemyCannon, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < enemyLocation.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullyAllyBlocked_NoMoves()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon allyCannon1 = new Cannon(board, true);
        Cannon allyCannon2 = new Cannon(board, true);
        Cannon allyCannon3 = new Cannon(board, true);
        Cannon allyCannon4 = new Cannon(board, true);
        Location cannonLocation = new Location(4, 5);
        Location allyLocation1 = new Location(3, 5);
        Location allyLocation2 = new Location(5, 5);
        Location allyLocation3 = new Location(4, 6);
        Location allyLocation4 = new Location(4, 4);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(allyCannon1, allyLocation1);
        board.setPiece(allyCannon2, allyLocation2);
        board.setPiece(allyCannon3, allyLocation3);
        board.setPiece(allyCannon4, allyLocation4);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertTrue(actualResult.isEmpty(), "List of moves is not empty");
    }

    @Test
    public void getMoves_FullyEnemyBlocked_NoMoves()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon enemyCannon1 = new Cannon(board, false);
        Cannon enemyCannon2 = new Cannon(board, false);
        Cannon enemyCannon3 = new Cannon(board, false);
        Cannon enemyCannon4 = new Cannon(board, false);
        Location cannonLocation = new Location(4, 5);
        Location enemyLocation1 = new Location(3, 5);
        Location enemyLocation2 = new Location(5, 5);
        Location enemyLocation3 = new Location(4, 6);
        Location enemyLocation4 = new Location(4, 4);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(enemyCannon1, enemyLocation1);
        board.setPiece(enemyCannon2, enemyLocation2);
        board.setPiece(enemyCannon3, enemyLocation3);
        board.setPiece(enemyCannon4, enemyLocation4);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertTrue(actualResult.isEmpty(), "List of moves is not empty");
    }

    @Test
    public void getMovesCapture_JumpAllyNoSpace_JumpCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon allyCannon = new Cannon(board, true);
        Cannon enemyCannon = new Cannon(board, false);
        Location cannonLocation = new Location(4, 5);
        Location allyLocation = new Location(4, 6);
        Location enemyLocation = new Location(4, 7);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(allyCannon, allyLocation);
        board.setPiece(enemyCannon, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < allyLocation.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        expectedResult.add(enemyLocation);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMovesCapture_JumpAllyWithSpace_JumpCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon allyCannon = new Cannon(board, true);
        Cannon enemyCannon = new Cannon(board, false);
        Location cannonLocation = new Location(4, 1);
        Location allyLocation = new Location(4, 3);
        Location enemyLocation = new Location(4, 9);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(allyCannon, allyLocation);
        board.setPiece(enemyCannon, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < allyLocation.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        expectedResult.add(enemyLocation);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMovesCapture_DoubleAlly_NoCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon allyCannon1 = new Cannon(board, true);
        Cannon allyCannon2 = new Cannon(board, true);
        Location cannonLocation = new Location(4, 1);
        Location allyLocation1 = new Location(4, 3);
        Location allyLocation2 = new Location(4, 9);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(allyCannon1, allyLocation1);
        board.setPiece(allyCannon2, allyLocation2);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < allyLocation1.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMovesCapture_JumpEnemyNoSpace_JumpCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon enemyCannon1 = new Cannon(board, false);
        Cannon enemyCannon2 = new Cannon(board, false);
        Location cannonLocation = new Location(4, 5);
        Location enemyLocation1 = new Location(4, 6);
        Location enemyLocation2 = new Location(4, 7);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(enemyCannon1, enemyLocation1);
        board.setPiece(enemyCannon2, enemyLocation2);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < enemyLocation1.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        expectedResult.add(enemyLocation2);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMovesCapture_JumpEnemyWithSpace_JumpCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon enemyCannon1 = new Cannon(board, false);
        Cannon enemyCannon2 = new Cannon(board, false);
        Location cannonLocation = new Location(4, 1);
        Location enemyLocation1 = new Location(4, 3);
        Location enemyLocation2 = new Location(4, 9);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(enemyCannon1, enemyLocation1);
        board.setPiece(enemyCannon2, enemyLocation2);

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < enemyLocation1.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        expectedResult.add(enemyLocation2);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMovesCapture_OneDirectionFilledEnemies_OnlyOneCapture()
    {
        Cannon cannon = new Cannon(board, true);
        Cannon enemyCannon1 = new Cannon(board, false);
        Cannon target = new Cannon(board, false);
        Location cannonLocation = new Location(4, 1);
        Location enemyLocation1 = new Location(4, 2);
        Location targetLocation = new Location(4, 3);
        board.setPiece(cannon, cannonLocation);
        board.setPiece(enemyCannon1, enemyLocation1);
        board.setPiece(target, targetLocation);
        for (int i = targetLocation.getY() + 1; i < board.getLength(); i++) {
            board.setPiece(new Cannon(board, false),
                    new Location(cannonLocation.getX(), i));
        }

        ArrayList<Location> expectedResult = new ArrayList<>();

        //add vertical moves
        for (int i = 0; i < enemyLocation1.getY(); i++) {
            Location tempLoc = new Location(cannonLocation.getX(), i);
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        //add horizontal moves
        for (int i = 0; i < board.getWidth(); i++) {
            Location tempLoc = new Location(i, cannonLocation.getY());
            if (!cannonLocation.equals(tempLoc)) {
                expectedResult.add(tempLoc);
            }
        }
        expectedResult.add(targetLocation);

        ArrayList<Location> actualResult = cannon.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(cannon.getMoves()), "The lists of moves are different");
    }

    @Test
    public void move_LegalMove_True()
    {
        Cannon cannon = new Cannon(board, true);
        Location location = new Location(4, 5);
        board.setPiece(cannon, location);
        Location newLocation = new Location(6,5);

        assertTrue(cannon.move(newLocation));
        assertEquals(newLocation, cannon.getLocation(), "This cannon was not moved to the correct location.");
    }

    @Test
    public void move_IllegalMove_False() {
        Cannon cannon = new Cannon(board, true);
        Location location = new Location(4, 5);
        board.setPiece(cannon, location);

        Location newLocation = new Location(2, 1);

        assertFalse(cannon.move(newLocation));
        assertEquals(location, cannon.getLocation(), "This cannon's location was changed.");
    }

    @Test
    public void equals_EqualCannon_True()
    {
        Location loc = new Location(2, 8);
        Cannon Cannon1 = new Cannon(board, true);
        Cannon1.setLocation(loc);
        Cannon Cannon2 = new Cannon(board, true);
        Cannon2.setLocation(loc);
        assertEquals(Cannon1, Cannon2);
    }

    @Test
    public void equals_NonCannonObject_False()
    {
        Location loc = new Location(2, 8);
        Cannon Cannon1 = new Cannon(board, true);
        Cannon1.setLocation(loc);
        Object obj = new Object();
        assertNotEquals(Cannon1, obj);
    }

    @Test
    public void equals_DifferentLocations_False()
    {
        Cannon Cannon1 = new Cannon(board, true);
        Location loc = new Location(2, 8);
        Cannon1.setLocation(loc);
        Cannon Cannon2 = new Cannon(board, true);
        Location loc2 = new Location(3, 7);
        Cannon2.setLocation(loc2);
        assertNotEquals(Cannon1, Cannon2);
    }

    @Test
    public void hashCode_EqualCannons_Equals()
    {
        Location loc = new Location(2, 8);
        Cannon Cannon1 = new Cannon(board, true);
        Cannon1.setLocation(loc);
        Cannon Cannon2 = new Cannon(board, true);
        Cannon2.setLocation(loc);
        assertEquals(Cannon1.hashCode(), Cannon2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal()
    {
        Location loc = new Location(2, 8);
        Cannon Cannon = new Cannon(board, true);
        Cannon.setLocation(loc);
        int hashCode = Cannon.hashCode();
        assertEquals(Cannon.hashCode(), hashCode);
    }
}
