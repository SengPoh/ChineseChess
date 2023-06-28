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
 * @version 28-6-2023
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
    }

}