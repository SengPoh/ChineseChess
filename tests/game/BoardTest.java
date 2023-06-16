package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void testInit()
    {
        Board board = new Board(9, 10);
        assertEquals(9, board.getWidth());
        assertEquals(10, board.getLength());
    }

    @Test
    public void testIsWithinBoard()
    {
        Board board = new Board(9, 10);
        Location loc = new Location(4, 5);
        assertTrue(board.isWithinBoard(loc));
    }

    @Test
    public void testIsWithinBoardXBoundValue()
    {
        Board board = new Board(9, 10);

        Location loc = new Location(0, 5);
        assertTrue(board.isWithinBoard(loc));

        loc = new Location(8, 5);
        assertTrue(board.isWithinBoard(loc));
    }

    @Test
    public void testIsWithinBoardYBoundValue()
    {
        Board board = new Board(9, 10);

        Location loc = new Location(4, 0);
        assertTrue(board.isWithinBoard(loc));

        loc = new Location(4, 9);
        assertTrue(board.isWithinBoard(loc));
    }

    @Test
    public void testIsWithinBoardBoundValues()
    {
        Board board = new Board(9, 10);

        Location loc = new Location(8, 9);
        assertTrue(board.isWithinBoard(loc));

        loc = new Location(0, 0);
        assertTrue(board.isWithinBoard(loc));
    }

    @Test
    public void testIsWithinBoardXInvalidValue()
    {
        Board board = new Board(9, 10);

        Location loc = new Location(-12, 5);
        assertFalse(board.isWithinBoard(loc), "smaller X value does not correctly ");

        loc = new Location(23, 5);
        assertFalse(board.isWithinBoard(loc));
    }
}