package game.pieces;
import game.Board;
import game.Location;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for General.java.
 *
 * @author Lee Seng Poh
 * @version 14-8-2023
 */

class GeneralTest {
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
        General general = new General(board, true);

        assertEquals(board, general.getBoard(), "the board is different from expected");
        assertTrue(general.isBlack(), "the color is different from expected");

        ArrayList<Location> expectedMoveSet = new ArrayList<>(Arrays.asList(
                new Location(1, 0),
                new Location(-1, 0),
                new Location(0, 1),
                new Location(0, -1)
        ));
        ArrayList<Location> actualMoveSet = general.getMoveSet();
        assertTrue(actualMoveSet.containsAll(expectedMoveSet), "The move sets are different");
    }

    @Test
    public void init_NullBoard_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new General(null, true));
        assertEquals("A piece must be assigned to a board.", exception.getMessage());
    }

    @Test
    public void setLocation_Null_Works()
    {
        General general = new General(board, true);
        general.setLocation(null);
        assertNull(general.getLocation());
    }

    @Test
    public void setLocation_InvalidLocation_Exception()
    {
        General general = new General(board, true);

        Location invalidLocation = new Location(-1, -1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> general.setLocation(invalidLocation));
        assertEquals("Location can only be either within the board or null.", exception.getMessage());
    }

    @Test
    public void isBlack_Black_True()
    {
        General general = new General(board, true);

        assertTrue(general.isBlack());
    }

    @Test
    public void isBlack_White_False()
    {
        General general = new General(board, false);

        assertFalse(general.isBlack());
    }

    @Test
    public void isSameColor_BothBlack_True()
    {
        General general1 = new General(board, true);
        General general2 = new General(board, true);
        assertTrue(general1.isSameColor(general2));
    }

    @Test
    public void isSameColor_BothWhite_True()
    {
        General general1 = new General(board, false);
        General general2 = new General(board, false);
        assertTrue(general1.isSameColor(general2));
    }

    @Test
    public void isSameColor_BlackAndWhite_False()
    {
        General general1 = new General(board, false);
        General general2 = new General(board, true);
        assertFalse(general1.isSameColor(general2));
    }

    @Nested
    @DisplayName("Tests in palace")
    class GeneralPalaceTest {
        @BeforeEach
        void setup()
        {
            for (int i = 3; i <= 5; i++) {
                for (int j = 0; j < 3; j++) {
                    board.setPalace(new Location(i, j), true);
                }
            }
        }

        @Test
        public void getMoves_EmptyPalace_OrthogonalUnblocked()
        {
            General general = new General(board, true);
            Location generalLocation = new Location(4, 1);
            board.setPiece(general, generalLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_EmptyPalaceCorner_OrthogonalUnblocked()
        {
            General general = new General(board, true);
            Location generalLocation = new Location(3, 0);
            board.setPiece(general, generalLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(4, 0)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_AllyBlocking_OrthogonalBlocked()
        {
            General general = new General(board, true);
            Chariot allyChariot = new Chariot(board, true);
            Location generalLocation = new Location(4, 1);
            Location allyLocation = new Location(3, 1);
            board.setPiece(general, generalLocation);
            board.setPiece(allyChariot, allyLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_EnemyBlocking_OrthogonalCapture()
        {
            General general = new General(board, true);
            Chariot enemyChariot = new Chariot(board, false);
            Location generalLocation = new Location(4, 1);
            Location enemyLocation = new Location(3, 1);
            board.setPiece(general, generalLocation);
            board.setPiece(enemyChariot, enemyLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_FullyAllyBlocking_OrthogonalBlocked()
        {
            General general = new General(board, true);
            Chariot allyChariot1 = new Chariot(board, true);
            Chariot allyChariot2 = new Chariot(board, true);
            Chariot allyChariot3 = new Chariot(board, true);
            Chariot allyChariot4 = new Chariot(board, true);
            Location generalLocation = new Location(4, 1);
            Location allyLocation1 = new Location(3, 1);
            Location allyLocation2 = new Location(5, 1);
            Location allyLocation3 = new Location(4, 0);
            Location allyLocation4 = new Location(4, 2);
            board.setPiece(general, generalLocation);
            board.setPiece(allyChariot1, allyLocation1);
            board.setPiece(allyChariot2, allyLocation2);
            board.setPiece(allyChariot3, allyLocation3);
            board.setPiece(allyChariot4, allyLocation4);

            ArrayList<Location> expectedResult = new ArrayList<>();

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_FullEnemyBlocking_OrthogonalCapture()
        {
            General general = new General(board, true);
            Chariot enemyChariot1 = new Chariot(board, false);
            Chariot enemyChariot2 = new Chariot(board, false);
            Chariot enemyChariot3 = new Chariot(board, false);
            Chariot enemyChariot4 = new Chariot(board, false);
            Location generalLocation = new Location(4, 1);
            Location enemyLocation1 = new Location(3, 1);
            Location enemyLocation2 = new Location(5, 1);
            Location enemyLocation3 = new Location(4, 0);
            Location enemyLocation4 = new Location(4, 2);
            board.setPiece(general, generalLocation);
            board.setPiece(enemyChariot1, enemyLocation1);
            board.setPiece(enemyChariot2, enemyLocation2);
            board.setPiece(enemyChariot3, enemyLocation3);
            board.setPiece(enemyChariot4, enemyLocation4);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_CanFly_OrthogonalUnblockedAndFly()
        {
            General general = new General(board, true);
            General enemyGeneral = new General(board, false);
            Location generalLocation = new Location(4, 1);
            Location enemyGeneralLocation = new Location(4, 9);
            board.setPiece(general, generalLocation);
            board.setPiece(enemyGeneral, enemyGeneralLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2),
                    new Location(4, 9)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_AllyBlockingFlying_OrthogonalUnblockedWithoutFly()
        {
            General general = new General(board, true);
            General enemyGeneral = new General(board, false);
            Chariot allyChariot = new Chariot(board, true);
            Location generalLocation = new Location(4, 1);
            Location enemyGeneralLocation = new Location(4, 9);
            Location allyChariotLocation = new Location(4, 5);
            board.setPiece(general, generalLocation);
            board.setPiece(enemyGeneral, enemyGeneralLocation);
            board.setPiece(allyChariot, allyChariotLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void getMoves_EnemyBlockingFlying_OrthogonalUnblockedWithoutFly()
        {
            General general = new General(board, true);
            General enemyGeneral = new General(board, false);
            Chariot enemyChariot = new Chariot(board, false);
            Location generalLocation = new Location(4, 1);
            Location enemyGeneralLocation = new Location(4, 9);
            Location enemyChariotLocation = new Location(4, 5);
            board.setPiece(general, generalLocation);
            board.setPiece(enemyGeneral, enemyGeneralLocation);
            board.setPiece(enemyChariot, enemyChariotLocation);

            ArrayList<Location> expectedResult = new ArrayList<>(Arrays.asList(
                    new Location(3, 1),
                    new Location(5, 1),
                    new Location(4, 0),
                    new Location(4, 2)
            ));

            ArrayList<Location> actualResult = general.getMoves();
            assertEquals(expectedResult.size(), actualResult.size(), "List of moves does not have the same size");
            assertTrue(expectedResult.containsAll(general.getMoves()), "The lists of moves are different");
        }

        @Test
        public void move_LegalMove_True()
        {
            General general = new General(board, true);
            Location location = new Location(4, 1);
            board.setPiece(general, location);
            Location newLocation = new Location(4,2);

            assertTrue(general.move(newLocation));
            assertEquals(newLocation, general.getLocation(), "This general was not moved to the correct location.");
        }

        @Test
        public void move_IllegalMove_False() {
            General general = new General(board, true);
            Location location = new Location(4, 1);
            board.setPiece(general, location);

            Location newLocation = new Location(2, 1);

            assertFalse(general.move(newLocation));
            assertEquals(location, general.getLocation(), "This general's location was changed.");
        }
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