package game.pieces;

import game.Board;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Chariot.java.
 *
 * @author Lee Seng Poh
 * @version 18-6-2023
 */

class ChariotTest {

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


}