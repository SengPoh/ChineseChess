package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ComputerPlayer.java.
 * @author Lee Seng Poh
 * @version 21-10-2023
 */
class ComputerPlayerTest {

    @Test
    public void init()
    {
        boolean isBlack = true;
        Game game = new Game();
        ComputerPlayer player = new ComputerPlayer(isBlack, game, 20);
        assertEquals(isBlack, player.isBlack());
        assertTrue(player.isComputer());
    }
}
