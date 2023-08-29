package game;

import java.util.ArrayList;

/**
 * An algorithm that searches for the best move available using Alpha–beta pruning.
 */
public class ComputerPlayer extends Player {
    private final Game game;
    private final int ply;

    /**
     * Initialises the computer player.
     * @param isBlack The color of this player.
     * @param game The game this player is playing.
     * @param ply The number of steps this computer looks ahead.
     */
    public ComputerPlayer(boolean isBlack, Game game, int ply)
    {
        super(isBlack);
        this.game = game;
        this.ply = ply;
    }

    public int alphaBeta(int alpha, int beta, int depth, boolean isMaximizingPlayer)
    {
        if (depth == 0) {
            return game.evaluateScore(isBlack());
        }

        ArrayList<Move> moves = game.getCurrentPlayer().getMoves();

        if (isMaximizingPlayer) {
            double value = Double.NEGATIVE_INFINITY;
            for (Move move : moves) {
                if (!game.move(move)) {
                    throw new IllegalStateException("This move cannot be made in this condition.");
                };
                value = Math.max(value, alphaBeta(alpha, beta, depth - 1, false));
            }
        }
        return 0;
    }
}
