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
 * @version 14-8-2023
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

    @Nested
    @DisplayName("Tests in palace")
    class AdvisorPalaceTest {
        @BeforeEach
        void setup() {
            for (int i = 3; i <= 5; i++) {
                for (int j = 0; j < 3; j++) {
                    board.setPalace(new Location(i, j), true);
                }
            }
        }

        @Test
        public void getMoves_EmptyPalace_DiagonalUnblocked()
        {
            Advisor advisor = new Advisor(board, true);
            Location advisorLocation = new Location(4, 1);
            board.setPiece(advisor, advisorLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 0),
                    new Location(3, 2),
                    new Location(5, 0),
                    new Location(5, 2)
            ));

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_EmptyPalaceCorner_DiagonalUnblocked()
        {
            Advisor advisor = new Advisor(board, true);
            Location advisorLocation = new Location(3, 0);
            board.setPiece(advisor, advisorLocation);

            ArrayList<Location> expectedResult = new ArrayList<>();
            expectedResult.add(new Location(4, 1));

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }
        
        @Test
        public void getMoves_AllyBlocking_DiagonalBlocked()
        {
            Advisor advisor = new Advisor(board, true);
            Chariot allyChariot = new Chariot(board, true);
            Location advisorLocation = new Location(4, 1);
            Location allyLocation = new Location(3, 0);
            board.setPiece(advisor, advisorLocation);
            board.setPiece(allyChariot, allyLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 2),
                    new Location(5, 0),
                    new Location(5, 2)
            ));

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_EnemyBlocking_DiagonalBlocked()
        {
            Advisor advisor = new Advisor(board, true);
            Chariot enemyChariot = new Chariot(board, false);
            Location advisorLocation = new Location(4, 1);
            Location enemyLocation = new Location(3, 0);
            board.setPiece(advisor, advisorLocation);
            board.setPiece(enemyChariot, enemyLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 0),
                    new Location(3, 2),
                    new Location(5, 0),
                    new Location(5, 2)
            ));

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }
        
        @Test
        public void getMoves_FullyAllyBlocking_DiagonalBlocked()
        {
            Advisor advisor = new Advisor(board, true);
            Chariot allyChariot1 = new Chariot(board, true);
            Chariot allyChariot2 = new Chariot(board, true);
            Chariot allyChariot3 = new Chariot(board, true);
            Chariot allyChariot4 = new Chariot(board, true);
            Location advisorLocation = new Location(4, 1);
            Location allyLocation1 = new Location(3, 0);
            Location allyLocation2 = new Location(3, 2);
            Location allyLocation3 = new Location(5, 0);
            Location allyLocation4 = new Location(5, 2);
            board.setPiece(advisor, advisorLocation);
            board.setPiece(allyChariot1, allyLocation1);
            board.setPiece(allyChariot2, allyLocation2);
            board.setPiece(allyChariot3, allyLocation3);
            board.setPiece(allyChariot4, allyLocation4);

            ArrayList<Location> expectedResult = new ArrayList<>();

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_FullEnemyBlocking_DiagonalCapture()
        {
            Advisor advisor = new Advisor(board, true);
            Chariot enemyChariot1 = new Chariot(board, false);
            Chariot enemyChariot2 = new Chariot(board, false);
            Chariot enemyChariot3 = new Chariot(board, false);
            Chariot enemyChariot4 = new Chariot(board, false);
            Location advisorLocation = new Location(4, 1);
            Location enemyLocation1 = new Location(3, 0);
            Location enemyLocation2 = new Location(3, 2);
            Location enemyLocation3 = new Location(5, 0);
            Location enemyLocation4 = new Location(5, 2);
            board.setPiece(advisor, advisorLocation);
            board.setPiece(enemyChariot1, enemyLocation1);
            board.setPiece(enemyChariot2, enemyLocation2);
            board.setPiece(enemyChariot3, enemyLocation3);
            board.setPiece(enemyChariot4, enemyLocation4);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 0),
                    new Location(3, 2),
                    new Location(5, 0),
                    new Location(5, 2)
            ));

            ArrayList<Location> actualResult = advisor.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(advisor.getMoves()), "The lists of moves are different");
        }

        @Test
        public void move_LegalMove_True()
        {
            Advisor advisor = new Advisor(board, true);
            Location location = new Location(4, 1);
            board.setPiece(advisor, location);
            Location newLocation = new Location(3,0);

            assertTrue(advisor.move(newLocation));
            assertEquals(newLocation, advisor.getLocation(), "This advisor was not moved to the correct location.");
        }

        @Test
        public void move_IllegalMove_False() {
            Advisor advisor = new Advisor(board, true);
            Location location = new Location(4, 1);
            board.setPiece(advisor, location);

            Location newLocation = new Location(2, 1);

            assertFalse(advisor.move(newLocation));
            assertEquals(location, advisor.getLocation(), "This advisor's location was changed.");
        }
    }

    @Test
    public void equals_EqualAdvisor_True()
    {
        Location loc = new Location(2, 8);
        Advisor advisor1 = new Advisor(board, true);
        advisor1.setLocation(loc);
        Advisor advisor2 = new Advisor(board, true);
        advisor2.setLocation(loc);
        assertEquals(advisor1, advisor2);
    }

    @Test
    public void equals_NonAdvisorObject_False()
    {
        Location loc = new Location(2, 8);
        Advisor advisor1 = new Advisor(board, true);
        advisor1.setLocation(loc);
        Object obj = new Object();
        assertNotEquals(advisor1, obj);
    }

    @Test
    public void equals_DifferentLocations_False()
    {
        Advisor advisor1 = new Advisor(board, true);
        Location loc = new Location(2, 8);
        advisor1.setLocation(loc);
        Advisor advisor2 = new Advisor(board, true);
        Location loc2 = new Location(3, 7);
        advisor2.setLocation(loc2);
        assertNotEquals(advisor1, advisor2);
    }

    @Test
    public void hashCode_EqualAdvisors_Equals()
    {
        Location loc = new Location(2, 8);
        Advisor advisor1 = new Advisor(board, true);
        advisor1.setLocation(loc);
        Advisor advisor2 = new Advisor(board, true);
        advisor2.setLocation(loc);
        assertEquals(advisor1.hashCode(), advisor2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal()
    {
        Location loc = new Location(2, 8);
        Advisor advisor = new Advisor(board, true);
        advisor.setLocation(loc);
        int hashCode = advisor.hashCode();
        assertEquals(advisor.hashCode(), hashCode);
    }
}