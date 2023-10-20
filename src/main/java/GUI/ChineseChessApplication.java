package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChineseChessApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("Xiang Qi");
        createGamePane(primaryStage);

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
