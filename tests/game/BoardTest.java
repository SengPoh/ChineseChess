package game;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Board.java.
 * @author Lee Seng Poh
 * @version 18-6-2023
 */
class BoardTest {

    @Test
    public void init()
    {
        Board board = new Board(9, 10);
        assertEquals(9, board.getWidth());
        assertEquals(10, board.getLength());
    }

    @Test
    public void init_InvalidXValue_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->new Board(0, 10));
        assertEquals("Width and length must be more than 1.", exception.getMessage());
    }

    @Test
    public void init_InvalidYValue_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->new Board(9, 0));
        assertEquals("Width and length must be more than 1.", exception.getMessage());
    }

    @Test
    public void init_InvalidValues_Exception()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->new Board(0, 0));
        assertEquals("Width and length must be more than 1.", exception.getMessage());
    }

    @Nested
    @DisplayName("Tests with chinese chessboard")
    class ChessBoardTests {

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
        public void testGetPiece()
        {

        }

        @Test
        public void isWithinBoard_ValidValues_True()
        {
            Location loc = new Location(4, 5);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_XLowerBoundValue_True()
        {
            Location loc = new Location(0, 5);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_XUpperBoundValue_True()
        {
            Location loc = new Location(8, 5);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_YLowerBoundValue_True()
        {
            Location loc = new Location(4, 0);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_YUpperBoundValue_True()
        {
            Location loc = new Location(4, 9);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_LowerBoundValues_True()
        {
            Location loc = new Location(0, 0);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_UpperBoundValues_True()
        {
            Location loc = new Location(8, 9);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_XTooSmallValue_False()
        {
            Location loc = new Location(-1, 5);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_XTooLargeValue_False()
        {
            Location loc = new Location(9, 5);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_YTooSmallValue_False()
        {
            Location loc = new Location(4, -1);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_YTooLargeValue_False()
        {
            Location loc = new Location(4, 10);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_TooSmallValues_False()
        {
            Location loc = new Location(-1, -1);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isWithinBoard_TooLargeValues_False()
        {
            Location loc = new Location(9, 10);
            assertFalse(board.isWithinBoard(loc));
        }

        @Test
        public void isEmpty_EmptyLocation_True()
        {
            Location loc = new Location(4, 5);
            assertTrue(board.isEmpty(loc));
        }

        @Test
        public void isEmpty_NotEmptyLocation_False()
        {

        }

        @Test
        public void isEmpty_NullLocation_Exception()
        {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> board.isEmpty(null));
            assertEquals("Location cannot be null.", exception.getMessage());
        }

        @Test
        public void isEmpty_OutOfBoardLocation_Exception()
        {
            Location loc = new Location(9, 10);
            assertFalse(board.isWithinBoard(loc));
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> board.isEmpty(loc));
            assertEquals("This location is not within the board.", exception.getMessage());
        }
    }
}