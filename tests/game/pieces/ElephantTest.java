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
 * @version 14-8-2023
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

    @Test
    public void getMoves_EmptyBoard_DiagonalUnblocked()
    {
        Elephant elephant = new Elephant(board, true);
        Location elephantLocation = new Location(2, 2);
        board.setPiece(elephant, elephantLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 0),
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EmptyBoardCorner_DiagonalUnblocked()
    {
        Elephant elephant = new Elephant(board, true);
        Location elephantLocation = new Location(0, 0);
        board.setPiece(elephant, elephantLocation);

        ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(new Location(2, 2));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_AllyBlocking_DiagonalBlocked()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot allyChariot = new Chariot(board, true);
        Location elephantLocation = new Location(2, 2);
        Location allyLocation = new Location(0,0);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(allyChariot, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_EnemyBlocking_DiagonalCapture()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot enemyChariot = new Chariot(board, false);
        Location elephantLocation = new Location(2, 2);
        Location enemyLocation = new Location(0,0);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(enemyChariot, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 0),
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullyAllyBlocking_DiagonalBlocked()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot allyChariot1 = new Chariot(board, true);
        Chariot allyChariot2 = new Chariot(board, true);
        Chariot allyChariot3 = new Chariot(board, true);
        Chariot allyChariot4 = new Chariot(board, true);
        Location elephantLocation = new Location(2, 2);
        Location allyLocation1 = new Location(0, 0);
        Location allyLocation2 = new Location(0, 4);
        Location allyLocation3 = new Location(4, 0);
        Location allyLocation4 = new Location(4, 4);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(allyChariot1, allyLocation1);
        board.setPiece(allyChariot2, allyLocation2);
        board.setPiece(allyChariot3, allyLocation3);
        board.setPiece(allyChariot4, allyLocation4);

        ArrayList<Location> expectedResult = new ArrayList<>();

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_FullEnemyBlocking_DiagonalCapture()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot enemyChariot1 = new Chariot(board, false);
        Chariot enemyChariot2 = new Chariot(board, false);
        Chariot enemyChariot3 = new Chariot(board, false);
        Chariot enemyChariot4 = new Chariot(board, false);
        Location elephantLocation = new Location(2, 2);
        Location enemyLocation1 = new Location(0, 0);
        Location enemyLocation2 = new Location(0, 4);
        Location enemyLocation3 = new Location(4, 0);
        Location enemyLocation4 = new Location(4, 4);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(enemyChariot1, enemyLocation1);
        board.setPiece(enemyChariot2, enemyLocation2);
        board.setPiece(enemyChariot3, enemyLocation3);
        board.setPiece(enemyChariot4, enemyLocation4);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 0),
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_MidpointAllyBlocking_DiagonalBlocked()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot allyChariot = new Chariot(board, true);
        Location elephantLocation = new Location(2, 2);
        Location allyLocation = new Location(1,1);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(allyChariot, allyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_MidpointEnemyBlocking_DiagonalBlocked()
    {
        Elephant elephant = new Elephant(board, true);
        Chariot enemyChariot = new Chariot(board, false);
        Location elephantLocation = new Location(2, 2);
        Location enemyLocation = new Location(1,1);
        board.setPiece(elephant, elephantLocation);
        board.setPiece(enemyChariot, enemyLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 4),
                new Location(4, 0),
                new Location(4, 4)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void getMoves_RiverEdgeUnblocked_CannotCrossRiver()
    {
        Elephant elephant = new Elephant(board, true);
        Location elephantLocation = new Location(2, 4);

        for (int i = 0; i < board.getWidth(); i++) {
            board.setRiverEdge(new Location(i, 4), true);
            board.setRiverEdge(new Location(i, 5), true);
        }

        board.setPiece(elephant, elephantLocation);

        ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                new Location(0, 2),
                new Location(4, 2)
        ));

        ArrayList<Location> actualResult = elephant.getMoves();
        assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
        assertTrue(expectedResult.containsAll(elephant.getMoves()), "The lists of moves are different");
    }

    @Test
    public void move_LegalMove_True()
    {
        Elephant elephant = new Elephant(board, true);
        Location location = new Location(2, 2);
        board.setPiece(elephant, location);
        Location newLocation = new Location(0,0);

        assertTrue(elephant.move(newLocation));
        assertEquals(newLocation, elephant.getLocation(), "This elephant was not moved to the correct location.");
    }

    @Test
    public void move_IllegalMove_False() {
        Elephant elephant = new Elephant(board, true);
        Location location = new Location(2, 2);
        board.setPiece(elephant, location);

        Location newLocation = new Location(2, 1);

        assertFalse(elephant.move(newLocation));
        assertEquals(location, elephant.getLocation(), "This elephant's location was changed.");
    }
    
    @Test
    public void equals_EqualElephant_True()
    {
        Location loc = new Location(2, 8);
        Elephant Elephant1 = new Elephant(board, true);
        Elephant1.setLocation(loc);
        Elephant Elephant2 = new Elephant(board, true);
        Elephant2.setLocation(loc);
        assertEquals(Elephant1, Elephant2);
    }

    @Test
    public void equals_NonElephantObject_True()
    {
        Location loc = new Location(2, 8);
        Elephant Elephant1 = new Elephant(board, true);
        Elephant1.setLocation(loc);
        Object obj = new Object();
        assertNotEquals(Elephant1, obj);
    }

    @Test
    public void equals_DifferentLocations_True()
    {
        Elephant Elephant1 = new Elephant(board, true);
        Location loc = new Location(2, 8);
        Elephant1.setLocation(loc);
        Elephant Elephant2 = new Elephant(board, true);
        Location loc2 = new Location(3, 7);
        Elephant2.setLocation(loc2);
        assertNotEquals(Elephant1, Elephant2);
    }

    @Test
    public void hashCode_EqualElephants_Equals()
    {
        Location loc = new Location(2, 8);
        Elephant Elephant1 = new Elephant(board, true);
        Elephant1.setLocation(loc);
        Elephant Elephant2 = new Elephant(board, true);
        Elephant2.setLocation(loc);
        assertEquals(Elephant1.hashCode(), Elephant2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal()
    {
        Location loc = new Location(2, 8);
        Elephant Elephant = new Elephant(board, true);
        Elephant.setLocation(loc);
        int hashCode = Elephant.hashCode();
        assertEquals(Elephant.hashCode(), hashCode);
    }
    
}