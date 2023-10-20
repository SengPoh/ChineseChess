package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChineseChessApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("Xiang Qi");
        createMainMenu(primaryStage);

    }

    private void createMainMenu(Stage primaryStage)
    {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setId("main-menu");
        Button onePlayerButton = new Button("One Player");
        Button twoPlayersButton = new Button("Two Players");
        root.getChildren().addAll(onePlayerButton, twoPlayersButton);

        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createGamePane(Stage primaryStage)
    {
        GamePane root = new GamePane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
        root.setUpChessPieces();

        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}
