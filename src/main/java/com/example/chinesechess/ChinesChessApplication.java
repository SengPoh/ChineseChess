package com.example.chinesechess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class launches the chinese chess application.
 *
 * @author Lee Seng Poh
 * @version 10-7-2023
 */
public class ChinesChessApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Load the image
        Image image = new Image(ChinesChessApplication.class.getResource("/texture/Board.png").toString());
        ImageView boardImage = new ImageView();
        boardImage.setImage(image);
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(300);

        StackPane root = new StackPane();
        root.getChildren().add(boardImage);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Xiang Qi");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

        //Resize the board according to window size
        boardImage.fitHeightProperty().bind(root.heightProperty());
        boardImage.fitWidthProperty().bind(root.widthProperty());
    }

}
