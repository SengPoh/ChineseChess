package game;

import game.pieces.Chariot;
import game.pieces.Piece;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Board.java.
 * @author Lee Seng Poh
 * @version 19-6-2023
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
            Location location = new Location(4, 5);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_XLowerBoundValue_True()
        {
            Location location = new Location(0, 5);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_XUpperBoundValue_True()
        {
            Location location = new Location(board.getWidth() - 1, 5);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_YLowerBoundValue_True()
        {
            Location location = new Location(4, 0);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_YUpperBoundValue_True()
        {
            Location location = new Location(4, board.getLength() -1);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_LowerBoundValues_True()
        {
            Location location = new Location(0, 0);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_UpperBoundValues_True()
        {
            Location location = new Location(board.getWidth() - 1, board.getLength() -1);
            assertTrue(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_XTooSmallValue_False()
        {
            Location location = new Location(-1, 5);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_XTooLargeValue_False()
        {
            Location location = new Location(9, 5);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_YTooSmallValue_False()
        {
            Location location = new Location(4, -1);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_YTooLargeValue_False()
        {
            Location location = new Location(4, 10);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_TooSmallValues_False()
        {
            Location location = new Location(-1, -1);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void isWithinBoard_TooLargeValues_False()
        {
            Location location = new Location(9, 10);
            assertFalse(board.isWithinBoard(location));
        }

        @Test
        public void setPiece_ValidLocation()
        {
            Location location = new Location(4, 5);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.getPiece(location));
        }

        @Test
        public void setPiece_LowerBoundLocation()
        {
            Location location = new Location(0, 0);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.getPiece(location));
        }

        @Test
        public void setPiece_UpperBoundLocation()
        {
            Location location = new Location(board.getWidth() - 1, board.getLength() - 1);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.getPiece(location));
        }

        @Test
        public void setPiece_LocationWithAnotherPiece_PieceIsNotPlace()
        {
            Location location = new Location(4, 5);
            Piece piece = new Chariot(board, true);
            Piece newPiece = new Chariot(board, true);
            board.setPiece(piece, location);
            board.setPiece(newPiece, location);
            assertNotEquals(newPiece, board.getPiece(location));
            assertEquals(piece, board.getPiece(location));
        }

        @Test
        public void clearLocation_NullLocation_Null()
        {
            assertNull(board.clearLocation(null));
        }

        @Test
        public void clearLocation_EmptyLocation_Null()
        {
            Location location = new Location(4, 5);
            assertNull(board.clearLocation(location));
        }

        @Test
        public void clearLocation_OutsideBoardTooSmallLocation_Null()
        {
            Location location = new Location(-1, -1);
            assertNull(board.clearLocation(location));
        }

        @Test
        public void clearLocation_OutsideBoardTooLargeLocation_Null()
        {
            Location location = new Location(8, 9);
            assertNull(board.clearLocation(location));
        }


        @Test
        public void clearLocation_NotEmptyLocation_PieceAtTheLocation()
        {
            Location location = new Location(4, 5);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.clearLocation(location));
            assertTrue(board.isEmpty(location));
            assertNull(piece.getLocation());
        }

        @Test
        public void clearLocation_LowerBoundLocation_PieceAtTheLocation()
        {
            Location location = new Location(0, 0);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.clearLocation(location));
            assertTrue(board.isEmpty(location));
            assertNull(piece.getLocation());
        }

        @Test
        public void clearLocation_UpperBoundLocation_PieceAtTheLocation()
        {
            Location location = new Location(8, 9);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertEquals(piece, board.clearLocation(location));
            assertTrue(board.isEmpty(location));
            assertNull(piece.getLocation());
        }

        @Test
        public void clearLocation_FilledBoardNotEmptyLocation_OnlyClearedLocationAffected()
        {
            Location location = new Location(4, 5);
            Piece[][] expectedResult = new Piece[board.getWidth()][board.getLength()];
            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    Piece piece = new Chariot(board, true);
                    board.setPiece(piece, tempLoc);
                    if (!tempLoc.equals(location)) {
                        expectedResult[i][j] = piece;
                    }
                }
            }

            Piece resultPiece = board.getPiece(location);
            assertEquals(resultPiece, board.clearLocation(location));
            assertTrue(board.isEmpty(location), "The location is not empty");
            assertNull(resultPiece.getLocation(), "Clear piece does not have null location");

            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    if (!location.equals(tempLoc)) {
                        assertEquals(expectedResult[i][j], board.getPiece(tempLoc));
                    }
                }
            }
        }

        @Test
        public void clearLocation_FilledBoardNotEmptyLowerBoundLocation_OnlyClearedLocationAffected()
        {
            Location location = new Location(0, 0);
            Piece[][] expectedResult = new Piece[board.getWidth()][board.getLength()];
            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    Piece piece = new Chariot(board, true);
                    board.setPiece(piece, tempLoc);
                    if (!tempLoc.equals(location)) {
                        expectedResult[i][j] = piece;
                    }
                }
            }

            Piece resultPiece = board.getPiece(location);
            assertEquals(resultPiece, board.clearLocation(location));
            assertTrue(board.isEmpty(location), "The location is not empty");
            assertNull(resultPiece.getLocation(), "Clear piece does not have null location");

            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    if (!location.equals(tempLoc)) {
                        assertEquals(expectedResult[i][j], board.getPiece(tempLoc));
                    }
                }
            }
        }

        @Test
        public void clearLocation_FilledBoardNotEmptyUpperBoundLocation_OnlyClearedLocationAffected()
        {
            Location location = new Location(8, 9);
            Piece[][] expectedResult = new Piece[board.getWidth()][board.getLength()];
            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    Piece piece = new Chariot(board, true);
                    board.setPiece(piece, tempLoc);
                    if (!tempLoc.equals(location)) {
                        expectedResult[i][j] = piece;
                    }
                }
            }

            Piece resultPiece = board.getPiece(location);
            assertEquals(resultPiece, board.clearLocation(location));
            assertTrue(board.isEmpty(location), "The location is not empty");
            assertNull(resultPiece.getLocation(), "Clear piece does not have null location");

            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getLength(); j++) {
                    Location tempLoc = new Location(i, j);
                    if (!location.equals(tempLoc)) {
                        assertEquals(expectedResult[i][j], board.getPiece(tempLoc));
                    }
                }
            }
        }


        @Test
        public void isEmpty_EmptyLocation_True()
        {
            Location location = new Location(4, 5);
            assertTrue(board.isEmpty(location));
        }

        @Test
        public void isEmpty_NotEmptyLocation_False()
        {
            Location location = new Location(4, 5);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertFalse(board.isEmpty(location));
        }

        @Test
        public void isEmpty_NotEmptyLowerBoundLocation_False()
        {
            Location location = new Location(0, 0);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertFalse(board.isEmpty(location));
        }

        @Test
        public void isEmpty_NotEmptyUpperBoundLocation_False()
        {
            Location location = new Location(board.getWidth() - 1, board.getLength() - 1);
            Piece piece = new Chariot(board, true);
            board.setPiece(piece, location);
            assertFalse(board.isEmpty(location));
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
            Location location = new Location(9, 10);
            assertFalse(board.isWithinBoard(location));
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> board.isEmpty(location));
            assertEquals("This location is not within the board.", exception.getMessage());
        }
    }
}