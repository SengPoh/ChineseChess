package game;

/**
 * An algorithm that searches for the best move available using Alpha–beta pruning.
 */
public class MoveSearchAlgorithm {
    private Game game;
    //how many moves this algorithm looks ahead.
    private int ply;

    public MoveSearchAlgorithm(Game game, int ply)
    {
        this.game = game;
        this.ply = ply;
    }

    public int alphaBeta()
    {
        int value = 0;
        return value;
    }
}
