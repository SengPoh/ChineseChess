package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChineseChessApplication extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Xiang Qi");
        createMainMenu();
    }

    /**
     * Creates the main menu with buttons to start a one or two player game.
     */
    private void createMainMenu()
    {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setId("main-menu");
        Button onePlayerButton = new Button("One Player");
        Button twoPlayersButton = new Button("Two Players");
        twoPlayersButton.setOnMouseClicked(this::twoPlayerButtonClick);
        root.getChildren().addAll(onePlayerButton, twoPlayersButton);

        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Action taken when the two player button is clicked.
     * This creates a game pane with 2 human players.
     * @param event The MouseEvent that triggers this action.
     */
    private void twoPlayerButtonClick(MouseEvent event)
    {
        createGamePane();
    }

    /**
     * Creates the pane with a game.
     */
    private void createGamePane()
    {
        GamePane root = new GamePane();
        Scene scene = new Scene(root, root.getMinWidth(), root.getMinHeight());
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
        root.setUpChessPieces();

        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}
