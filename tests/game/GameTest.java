package game;

import game.pieces.Chariot;
import game.pieces.General;
import game.pieces.Piece;
import game.pieces.Soldier;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Game.java.
 * @author Lee Seng Poh
 * @version 13-8-2023
 */

class GameTest {
    private Game game;
    private Board board;
    @BeforeEach
    void setUp()
    {
        board = new Board(9, 10);
        game = new Game(board);
        game.getPlayer(true).addPiece(new General(board, true));
        game.getPlayer(false).addPiece(new General(board, false));
    }

    @AfterEach
    void tearDown()
    {
        board = null;
        game = null;
    }

    @Test
    public void move_ValidMove_True()
    {
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
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void undo_NoMove_False()
    {
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);

        game.undo(game.getPlayer(isBlack));
        assertTrue(board.getPiece(location) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(location).isBlack(), isBlack, "The undid piece has a different color.");
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void undo_OpponentMovedPlayerNotMoved_False()
    {
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        Location newLocation = new Location(5, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);

        Location enemyLocation = new Location(6,5);
        Chariot enemyChariot = new Chariot(board, !isBlack);
        board.setPiece(enemyChariot, enemyLocation);
        game.getPlayer(!isBlack).addPiece(enemyChariot);

        assertTrue(game.move(chariot, newLocation), "The piece was not moved.");
        game.undo(game.getPlayer(!isBlack));
        //test that the opponent piece was not moved.
        assertTrue(board.getPiece(newLocation) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(newLocation).isBlack(), isBlack, "The undid piece has a different color.");
        assertTrue(board.getPiece(enemyLocation) instanceof Chariot, "The undid enemy piece has a different type.");
        assertEquals(board.getPiece(enemyLocation).isBlack(), !isBlack, "The undid piece has a different color.");
        assertEquals(game.getCurrentPlayer(), game.getPlayer(!isBlack), "It is not currently the turn of the player who undid.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(newLocation) && !tempLoc.equals(enemyLocation)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }


    @Test
    public void undo_Capture_True()
    {
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
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

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
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

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
    public void undo_MoveAndCapture_True()
    {
        boolean isBlack = false;
        Chariot chariot = new Chariot(board, isBlack);
        Location location = new Location(4, 5);
        Location newLocation1 = new Location(2, 5);
        Location newLocation2 = new Location(5, 5);
        board.setPiece(chariot, location);
        game.getPlayer(isBlack).addPiece(chariot);

        Location enemyLocation = new Location(6,5);
        Location newEnemyLocation = new Location(5, 5);
        Chariot enemyChariot = new Chariot(board, !isBlack);
        board.setPiece(enemyChariot, enemyLocation);
        game.getPlayer(!isBlack).addPiece(enemyChariot);

        assertTrue(game.move(chariot, newLocation1), "The piece was not moved.");
        assertTrue(game.move(enemyChariot, newEnemyLocation), "The enemy piece was not moved");
        assertTrue(game.move(chariot, newLocation2), "The piece was not moved(2nd).");
        game.undo(game.getPlayer(isBlack));
        assertTrue(board.getPiece(newLocation1) instanceof Chariot, "The undid piece has a different type.");
        assertEquals(board.getPiece(newLocation1).isBlack(), isBlack, "The undid piece has a different color.");
        assertTrue(board.getPiece(newEnemyLocation) instanceof Chariot, "The undid enemy piece has a different type.");
        assertEquals(board.getPiece(newEnemyLocation).isBlack(), !isBlack, "The undid piece has a different color.");
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(newLocation1) && !tempLoc.equals(newEnemyLocation)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void undo_SoldierCrossRiver_1MoveSet()
    {
        boolean isBlack = false;
        Soldier soldier = new Soldier(board, isBlack);
        Location location = new Location(2, 4);
        location.setRiverEdge(true);
        board.setLocation(location);
        board.setPiece(soldier, location);
        game.getPlayer(isBlack).addPiece(soldier);
        Location newLocation = new Location(2, 5);
        newLocation.setRiverEdge(true);
        board.setLocation(newLocation);

        assertTrue(game.move(soldier, newLocation), "The piece was not moved.");
        game.undo(game.getPlayer(isBlack));
        Piece locationPiece = board.getPiece(location);
        assertTrue(locationPiece instanceof Soldier, "The undid piece has a different type.");
        assertEquals(locationPiece.isBlack(), isBlack, "The undid piece has a different color.");
        Soldier soldier2 = new Soldier(board, isBlack);
        soldier2.setLocation(location);
        assertEquals(locationPiece.getMoves(), soldier2.getMoves(), "The undid piece's move set was not reverted");
        assertEquals(game.getCurrentPlayer(), game.getPlayer(isBlack), "It is not currently the turn of the player who undid.");

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getLength(); j++) {
                Location tempLoc = new Location(i, j);
                if (!tempLoc.equals(location)) {
                    assertNull(board.getPiece(tempLoc), "Location " + i + ", " + j + " is not empty");
                }
            }
        }
    }

    @Test
    public void evaluateScore_Winning_Positive()
    {
        boolean isBlack = true;
        General general = new General(board, isBlack);
        game.getPlayer(isBlack).addPiece(general);

        assertEquals(game.evaluateScore(isBlack), 1000000);
    }

    @Test
    public void evaluateScore_Losing_Negative()
    {
        boolean isBlack = true;
        General general = new General(board, isBlack);
        game.getPlayer(isBlack).addPiece(general);

        assertEquals(game.evaluateScore(!isBlack), -1000000);
    }
}