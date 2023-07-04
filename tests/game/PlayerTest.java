package game;

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
}