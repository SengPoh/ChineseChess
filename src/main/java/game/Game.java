package game;

import game.pieces.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents the match being played.
 *
 * @author Lee Seng Poh
 * @version 4-8-2023
 */
public class Game {

    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Board board;
    private boolean isOngoing;

    public Game()
    {
        setup();
        isOngoing = true;
    }

    /**
     * For testing purposes.
     * @param board Board in this game.
     */
    public Game(Board board)
    {
        Player redPlayer = new Player(false);
        Player blackPlayer = new Player(true);
        players = new ArrayList<>();
        currentPlayerIndex = 0;
        players.add(redPlayer);
        players.add(blackPlayer);
        this.board = board;
        isOngoing = true;
    }

    /**
     * Set up the game with 2 players and the board.
     */
    public void setup()
    {
        Player redPlayer = new Player(false);
        Player blackPlayer = new Player(true);
        players = new ArrayList<>();
        currentPlayerIndex = 0;
        players.add(redPlayer);
        players.add(blackPlayer);
        setupBoard();
    }

    /**
     * Get the current player.
     * @return The current player.
     */
    public Player getCurrentPlayer()
    {
        return players.get(currentPlayerIndex);
    }

    /**
     * Return the player with the same color as specified.
     * @param isBlack True if the player wanted is black.
     * @return The player with the specified color.
     */
    public Player getPlayer(boolean isBlack)
    {
        for (Player player : players) {
            if (player.isBlack() == isBlack) {
                return player;
            }
        }
        return null;        //no player that matches is found
    }

    /**
     * Cycle to the next player.
     */
    private void nextPlayer()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Cycle to the previous player.
     */
    private void previousPlayer()
    {
        if (currentPlayerIndex == 0) {
            currentPlayerIndex = players.size();
        }
        currentPlayerIndex = currentPlayerIndex - 1;
    }

    /**
     * Set up the board with pieces on it.
     */
    private void setupBoard()
    {
        board = new Board(9, 10);
        setPalace();
        setRiver();
        setRedPieces();
        setBlackPieces();
    }

    /**
     * Set the red pieces on the board. Red pieces are set on the bottom half of the board.
     */
    private void setRedPieces()
    {
        setPiece(new Chariot(board, false), new Location(0, 0));
        setPiece(new Horse(board, false), new Location(1, 0));
        setPiece(new Elephant(board, false), new Location(2, 0));
        setPiece(new Advisor(board, false), new Location(3, 0));
        setPiece(new General(board, false), new Location(4, 0));
        setPiece(new Advisor(board, false), new Location(5, 0));
        setPiece(new Elephant(board, false), new Location(6, 0));
        setPiece(new Horse(board, false), new Location(7, 0));
        setPiece(new Chariot(board, false), new Location(8, 0));
        setPiece(new Cannon(board, false), new Location(1, 2));
        setPiece(new Cannon(board, false), new Location(7, 2));
        for (int i = 0; i < board.getLength(); i = i + 2) {
            setPiece(new Soldier(board, false), new Location(i, 3));
        }
    }

    /**
     * Set the black pieces on the board. Black pieces are set on the upper half of the board.
     */
    private void setBlackPieces()
    {
        setPiece(new Chariot(board, true), new Location(0, 9));
        setPiece(new Horse(board, true), new Location(1, 9));
        setPiece(new Elephant(board, true), new Location(2, 9));
        setPiece(new Advisor(board, true), new Location(3, 9));
        setPiece(new General(board, true), new Location(4, 9));
        setPiece(new Advisor(board, true), new Location(5, 9));
        setPiece(new Elephant(board, true), new Location(6, 9));
        setPiece(new Horse(board, true), new Location(7, 9));
        setPiece(new Chariot(board, true), new Location(8, 9));
        setPiece(new Cannon(board, true), new Location(1, 7));
        setPiece(new Cannon(board, true), new Location(7, 7));
        for (int i = 0; i < board.getLength(); i = i + 2) {
            setPiece(new Soldier(board, true), new Location(i, 6));
        }
    }

    /**
     * Move a piece to a specified location if it is a legal move and it is a piece of the current player.
     * Returns true if the piece is moved. The game ends if the move captures a general.
     * @param piece The piece to be moved.
     * @param location The location to be moved to.
     * @return True if this piece is moved.
     */
    public boolean move(Piece piece, Location location)
    {
        if (!isOngoing) {
            return false;       //does not move if the game is over
        }

        Location moveFromLocation = board.getLocation(piece.getLocation());
        Player movingPlayer = getPlayer(piece.isBlack());
        movingPlayer.recordMove(moveFromLocation, board.getLocation(location));

        boolean moved = false;
        if (piece != null && getCurrentPlayer().isBlack() == piece.isBlack()) {
            Piece locationPiece = null;
            if (!board.isEmpty(location)) {
                locationPiece = getPiece(location);
            }

            moved = board.move(piece, location);
            if (moved) {
                if (locationPiece != null) {
                    getPlayer(locationPiece.isBlack()).removePiece(locationPiece);       //remove the piece that was captured.
                }
                checkOngoing();
                nextPlayer();
            } else {
                movingPlayer.popPreviousMove();     //remove the record if did not move.
            }
        }
        return moved;
    }

    /**
     * Undo moves until the last move of the specified player is undone.
     * @param player The specified player whose last move to undo.
     */
    public void undo(Player player)
    {
        previousPlayer();
        boolean undid = false;

        while (!undid) {
            Player currentPlayer = getCurrentPlayer();
            if (currentPlayer != player) {
                undoPlayer(currentPlayer);
                previousPlayer();
            } else {
                undoPlayer(currentPlayer);
                undid = true;
            }
            checkOngoing();
        }
    }

    /**
     * Undo the last move of a specified player. Returns true if the undo was successful and
     * false if the player has no move to undo.
     * @param player The player whose move to undo.
     * @return boolean True if the undo was successful and false if the player has no move to undo.
     */
    private boolean undoPlayer(Player player)
    {
        Location[] locations = player.popPreviousMove();

        if (locations == null) {
            return false;
        }

        for (Location oldLocation : locations) {            //location before the move
            Location currentLocation = board.getLocation(oldLocation);      //location after the move.
            //restore the piece to its previous state
            Piece currentPiece = currentLocation.getPiece();
            if (currentPiece != null) {
                getPlayer(currentPiece).removePiece(currentPiece);
            }
            Piece oldPiece = oldLocation.getPiece();
            if (oldPiece != null) {
                getPlayer(oldPiece).addPiece(oldPiece);
            }
            board.setLocation(oldLocation);
        }
        return true;
    }

    /**
     * Resign the specified player.
     * @param player The player that is resigning.
     */
    public void resign(Player player)
    {
        player.setLost(true);
        isOngoing = false;
    }

    /**
     * Checks whether the game is ongoing. If a player has lost, the game is over.
     * Returns true if the game is ongoing.
     * @return True if the game is ongoing.
     */
    public boolean checkOngoing()
    {
        boolean hasLoser = false;
        //check players for a loser.
        for (Player player : players) {
            player.checkLost();
            if (player.lost()) {
                hasLoser = true;
            }
        }
        isOngoing = !hasLoser;

        return isOngoing;
    }

    /**
     * Returns the winner of the match, or null if the match is still ongoing.
     * @return The winner of the match, or null if the match is still ongoing.
     */
    public Player getWinner()
    {
        if (isOngoing) {
           return null;
        }

        Player winner = null;
        int i = 0;
        while (winner == null && i < players.size()) {
            Player player = players.get(i);
            if (!player.lost()) {
                winner = player;
            }
            i++;
        }
        return winner;
    }

    /**
     * Get the player with the color corresponding to the specified piece.
     * @param piece The piece whose owner is to be found.
     * @return The owner of the piece.
     */
    public Player getPlayer(Piece piece)
    {
        if (piece == null) {
            return null;
        }
        Player owner = null;
        for (Player player : players) {
            if (piece.isBlack() == player.isBlack()) {
                owner = player;
            }
        }
        return owner;
    }

    /**
     * Sets a specified piece on the specified location on the board and add it
     * to the player with the same color as the piece.
     * @param piece The piece to be set.
     * @param location The location to be set on.
     */
    private void setPiece(Piece piece, Location location)
    {
        board.setPiece(piece, location);
        getPlayer(piece).addPiece(piece);
    }

    /**
     * Returns the piece at the specified location on the board, if there are any is
     * present there. Returns null if there is no piece found.
     * @param location The location of the piece.
     * @return he piece on the location or null if there is none.
     */
    public Piece getPiece(Location location)
    {
        return board.getPiece(location);
    }

    /**
     * Remove any piece from the specified location on the board, if there are any is
     * present there.
     * @param location The location to be emptied.
     * @return The piece that is removed is there is any.
     */
    public Piece clearLocation(Location location)
    {
        return board.clearLocation(location);
    }

    /**
     * Returns the horizontal width of the board.
     * @return The horizontal width of the board.
     */
    public int getBoardWidth()
    {
        return board.getWidth();
    }

    /**
     * Returns the vertical length of the board.
     * @return The vertical length of the board.
     */
    public int getBoardLength()
    {
        return board.getLength();
    }

    /**
     * Set the palace locations on the board.
     */
    private void setPalace()
    {
        for (int i = 3; i <= 5; i++) {
            for (int j = 0; j < 3; j++) {
                Location location = new Location(i, j);
                board.setPalace(location, true);
            }

            for (int j = 7; j < 10; j++) {
                Location location = new Location(i, j);
                board.setPalace(location, true);
            }
        }
    }

    /**
     * Set the river edge locations on the board.
     */
    private void setRiver()
    {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 4; j <= 5; j++) {
                Location location = new Location(i, j);
                board.setRiverEdge(location, true);
            }
        }
    }
}
