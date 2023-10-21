package game;

import java.util.ArrayList;

/**
 * An algorithm that searches for the best move available using Alpha–beta pruning.
 *
 * @author Lee Seng Poh, with reference to https://www.chessprogramming.org/Alpha-Beta#Implementation
 * @version 21-10-2023
 */
public class ComputerPlayer extends Player {
    private final Game game;
    //number of steps ahead looked when deciding move (not including initial move).
    private final int ply;
    private Move currentBestMove;

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
        if (ply < 0) {
            throw new IllegalArgumentException("Ply must be more than 0.");
        }
        this.ply = ply;
        setIsComputer(true);
    }

    public Move decideMove()
    {
        alphaBetaMax(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, ply);
        if (!currentBestMove.canMove()) {
            throw new IllegalStateException("Error in deciding move.");
        }
        return currentBestMove;
    }

    /**
     * Maximizer part of alpha-beta pruning algorithm.
     * Maximizes the score at the current depth with a lower bound of alpha and an upper bound of beta.
     * @param alpha The lower bound of the acceptable score.
     * @param beta The upper bound of the acceptable score.
     * @param depth The current depth being looked at.
     * @return The score for this position.
     */
    public double alphaBetaMax(double alpha, double beta, int depth)
    {
        if (depth == 0) {
            return game.evaluateScore(isBlack());
        }

        ArrayList<Move> moves = game.getCurrentPlayer().getMoves();

        for (Move move : moves) {
            boolean moved = game.move(move);
            if (!moved) {
                throw new IllegalStateException("This move cannot be made in this condition.");
            }
            double score =  alphaBetaMin(alpha, beta, depth - 1);
            game.undo(game.getPlayer(move.getPiece()));
            if (score >= beta) {
                return beta;    //fail hard beta-cutoff
            }
            if (score > alpha) {
                alpha = score;

                if (depth == ply) {
                    currentBestMove = move;
                }
            }
        }
        return alpha;
    }

    /**
     * Minimizer part of alpha-beta pruning algorithm.
     * Minimizes the score at the current depth with a lower bound of alpha and an upper bound of beta.
     * @param alpha The lower bound of the acceptable score.
     * @param beta The upper bound of the acceptable score.
     * @param depth The current depth being looked at.
     * @return The score for this position.
     */
    public double alphaBetaMin(double alpha, double beta, int depth)
    {
        if (depth == 0) {
            return game.evaluateScore(isBlack());
        }

        ArrayList<Move> moves = game.getCurrentPlayer().getMoves();

        for (Move move : moves) {
            boolean moved = game.move(move);
            if (!moved) {
                throw new IllegalStateException("This move cannot be made in this condition.");
            }
            double score =  alphaBetaMax(alpha, beta, depth - 1);
            game.undo(game.getPlayer(move.getPiece()));
            if (score <= alpha) {
                return alpha;    //fail hard alpha-cutoff
            }
            if (score < beta) {
                beta = score;
            }
        }
        return beta;
    }
}
