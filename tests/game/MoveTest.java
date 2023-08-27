package game;

import game.pieces.Chariot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Move.java.
 * @author Lee Seng Poh
 * @version 14-8-2023
 */
class MoveTest {
    private Board board;
    @BeforeEach
    public void setUp()
    {
        board = new Board(9, 10);
    }

    @AfterEach
    public void tearDown()
    {
        board = null;
    }

    @Test
    public void init()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 3);
        Move move = new Move(chariot , moveFromLocation, moveToLocation);
        assertEquals(moveFromLocation, move.getMoveFromLocation());
        assertEquals(moveToLocation, move.getMoveToLocation());
    }

    @Test
    public void init_NullPiece_Exception()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = null;
        Location moveToLocation = new Location(8, 3);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move( chariot, moveFromLocation, moveToLocation));
        assertEquals("The piece cannot be null.", exception.getMessage());
    }

    @Test
    public void init_BothNullLocations_Exception()
    {
        Chariot chariot = new Chariot(board, true);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move(chariot, null, null));
        assertEquals("The location parameter is invalid.", exception.getMessage());
    }

    @Test
    public void init_NullMoveFromLocation_Exception()
    {
        Location moveToLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveToLocation);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move(chariot, moveToLocation, null));
        assertEquals("The location parameter is invalid.", exception.getMessage());
    }

    @Test
    public void init_NullMoveToLocation_Exception()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move(chariot, moveFromLocation, null));
        assertEquals("The location parameter is invalid.", exception.getMessage());
    }

    @Test
    public void init_OutsideBoardMoveFromLocation_Exception()
    {
        Location moveFromLocation = new Location(10, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 3);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move( chariot, moveFromLocation, moveToLocation));
        assertEquals("The location parameter is invalid.", exception.getMessage());
    }

    @Test
    public void init_OutsideBoardMoveToLocation_Exception()
    {
        Location moveFromLocation = new Location(5, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(5, 10);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move( chariot, moveFromLocation, moveToLocation));
        assertEquals("The location parameter is invalid.", exception.getMessage());
    }

    @Test
    public void init_UnmatchedPieceAndMoveFromLocation_Exception()
    {
        Location pieceLocation = new Location(6, 2);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, pieceLocation);
        Location moveFromLocation = new Location(5, 3);
        Location moveToLocation = new Location(5, 4);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Move(chariot , moveFromLocation, moveToLocation));
        assertEquals("The moveFromLocation does not match the location of the piece.", exception.getMessage());
    }

    @Test
    public void getMoveFromLocation()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 3);
        Move move = new Move( chariot, moveFromLocation, moveToLocation);

        assertEquals(moveFromLocation, move.getMoveFromLocation());
    }

    @Test
    public void getMoveToLocation()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 3);
        Move move = new Move(chariot, moveFromLocation, moveToLocation);

        assertEquals(moveToLocation, move.getMoveToLocation());
    }

    @Test
    public void canMove_ValidMove_True()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 3);
        Move move = new Move(chariot, moveFromLocation, moveToLocation);

        assertTrue(move.canMove());
    }

    @Test
    public void canMove_InvalidMove_True()
    {
        Location moveFromLocation = new Location(4, 3);
        Chariot chariot = new Chariot(board, true);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(8, 4);
        Move move = new Move(chariot, moveFromLocation, moveToLocation);

        assertFalse(move.canMove());
    }
}