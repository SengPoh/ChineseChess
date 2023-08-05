package GUI;

import game.Player;
import javafx.scene.layout.TilePane;

/**
 * A TilePane that contains reference to a player. This is designed to be used as the menu bar of a player.
 *
 * @author Lee Seng Poh
 * @version 4-8-2023
 */
public class PlayerTilePane extends TilePane {
    private Player player;

    public PlayerTilePane(Player player)
    {
        super();
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }
}
