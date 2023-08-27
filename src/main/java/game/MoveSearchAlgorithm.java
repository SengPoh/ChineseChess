package game;

/**
 * An algorithm that searches for the best move available using Alpha–beta pruning.
 */
public class MoveSearchAlgorithm {
    private Game game;
    //how many moves this algorithm looks ahead.
    private int ply;
    private boolean computerPlayerIsBlack;

    public MoveSearchAlgorithm(Game game, int ply, boolean computerPlayerIsBlack)
    {
        this.game = game;
        this.ply = ply;
        this.computerPlayerIsBlack = computerPlayerIsBlack;
    }

    public int alphaBeta(int alpha, int beta, int depth, boolean isMaximizingPlayer)
    {
        if (depth == 0) {
            return game.evaluateScore(computerPlayerIsBlack);
        }
        return 0;
    }
}
