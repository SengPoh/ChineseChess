package game;

import java.util.Queue;

/**
 * Represents the match being played.
 *
 * @author Lee Seng Poh
 * @version 5-7-2023
 */
public class Game {

    private Queue<Player> players;
    private Board board;

    public Game()
    {
        setup();
    }

    public void setup()
    {

    }

    private void setBoard()
    {
        board = new Board(9, 10);
        setPalace();
        setRiver();
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
