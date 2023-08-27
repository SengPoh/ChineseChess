package game;

import game.pieces.Chariot;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Player.java.
 * @author Lee Seng Poh
 * @version 4-7-2023
 */

class PlayerTest {
    @Test
    public void isBlack_BlackPlayer_True()
    {
        Player player = new Player(true);
        assertTrue(player.isBlack());
    }

    @Test
    public void isBlack_NotBlackPlayer_False()
    {
        Player player = new Player(false);
        assertFalse(player.isBlack());
    }

    @Test
    public void recordMove_LocationWithPiece_recorded()
    {
        Board board = new Board(9, 10);
        Player player = new Player(false);
        Location moveFromLocation = new Location(4, 6);
        Chariot chariot = new Chariot(board, false);
        board.setPiece(chariot, moveFromLocation);
        Location moveToLocation = new Location(6, 6);
        player.recordMove(chariot, moveFromLocation, moveToLocation);

        Move move = player.popPreviousMove();
        assertEquals(move.getMoveFromLocation(), moveFromLocation);
        assertEquals(move.getMoveToLocation(), moveToLocation);
        assertTrue(move.getMoveFromLocation().getPiece() instanceof Chariot);
    }
}