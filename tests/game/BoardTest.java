package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Board.java.
 * @author Lee Seng Poh
 * @version 18-6-2023
 */
class BoardTest {

    @Test
    public void testInit()
    {
        Board board = new Board(9, 10);
        assertEquals(9, board.getWidth());
        assertEquals(10, board.getLength());
    }

    @Test
    public void testInvalidInit()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->new Board(0, 0));
        assertEquals("Width and length must be more than 1.", exception.getMessage());
    }

    @Nested
    @DisplayName("Tests with chinese chessboard")
    class chessBoardTests() {

        private Board board;

        @BeforeEach
        void setUp()
        {
            board = new Board(9, 10);
        }

        @BeforeEach
        void tearDown()
        {
            board = null;
        }

        @Test
        public void testGetPiece()
        {

        }

        @Test
        public void testIsWithinBoard()
        {

            Location loc = new Location(4, 5);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void testIsWithinBoardXBoundValue()
        {
            Location loc = new Location(0, 5);
            assertTrue(board.isWithinBoard(loc));

            loc = new Location(8, 5);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void testIsWithinBoardYBoundValue()
        {
            Location loc = new Location(4, 0);
            assertTrue(board.isWithinBoard(loc));

            loc = new Location(4, 9);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void testIsWithinBoardBoundValues()
        {
            Location loc = new Location(8, 9);
            assertTrue(board.isWithinBoard(loc));

            loc = new Location(0, 0);
            assertTrue(board.isWithinBoard(loc));
        }

        @Test
        public void testIsWithinBoardXInvalidValue()
        {
            Location loc = new Location(-12, 5);
            assertFalse(board.isWithinBoard(loc), "smaller X value does not correctly ");

            loc = new Location(23, 5);
            assertFalse(board.isWithinBoard(loc));
        }
    }
}