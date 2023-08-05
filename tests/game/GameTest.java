package game;

import game.pieces.Chariot;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Game.java.
 * @author Lee Seng Poh
 * @version 5-8-2023
 */

class GameTest {
    @Test
    public void move_ValidMove_True()
    {
        Board board = new Board(9, 10);
        Game game = new Game(board);
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);
        Location newLocation = new Location(6,5);

        assertTrue(game.move(chariot, newLocation));
        assertEquals(newLocation, chariot.getLocation(), "This chariot was not moved to the correct location.");
    }

    @Test
    public void undo_Move_True()
    {
        Board board = new Board(9, 10);
        Game game = new Game(board);
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);
        Location newLocation = new Location(6,5);

        assertTrue(game.move(chariot, newLocation), "The piece was not moved.");
        game.undo(game.getPlayer(isBlack));
        assertTrue(board.getPiece(location) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(location).isBlack(), isBlack, "The undid piece has a different color.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location) && !tempLoc.equals(newLocation)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void undo_Capture_True()
    {
        Board board = new Board(9, 10);
        Game game = new Game(board);
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);

        Location enemyLocation = new Location(6,5);
        Chariot enemyChariot = new Chariot(board, !isBlack);
        board.setPiece(enemyChariot, enemyLocation);
        game.getPlayer(!isBlack).addPiece(enemyChariot);

        assertTrue(game.move(chariot, enemyLocation), "The piece was not moved.");
        game.undo(game.getPlayer(isBlack));
        assertTrue(board.getPiece(location) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(location).isBlack(), isBlack, "The undid piece has a different color.");
        assertTrue(board.getPiece(enemyLocation) instanceof Chariot, "The undid enemy piece has a different type.");
        assertEquals(board.getPiece(enemyLocation).isBlack(), !isBlack, "The undid piece has a different color.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location) && !tempLoc.equals(enemyLocation)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void undo_CurrentPlayerUndo_True()
    {
        Board board = new Board(9, 10);
        Game game = new Game(board);
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        Location newLocation = new Location(3, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);

        Location enemyLocation = new Location(6,5);
        Location newEnemyLocation = new Location(6, 3);
        Chariot enemyChariot = new Chariot(board, !isBlack);
        board.setPiece(enemyChariot, enemyLocation);
        game.getPlayer(!isBlack).addPiece(enemyChariot);

        assertTrue(game.move(chariot, newLocation), "The piece was not moved.");
        assertTrue(game.move(enemyChariot, newEnemyLocation));
        game.undo(game.getPlayer(isBlack));
        assertTrue(board.getPiece(location) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(location).isBlack(), isBlack, "The undid piece has a different color.");
        assertTrue(board.getPiece(enemyLocation) instanceof Chariot, "The undid enemy piece has a different type.");
        assertEquals(board.getPiece(enemyLocation).isBlack(), !isBlack, "The undid piece has a different color.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location) && !tempLoc.equals(enemyLocation)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

}