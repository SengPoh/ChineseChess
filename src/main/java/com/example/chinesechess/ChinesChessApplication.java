package com.example.chinesechess;

import game.Location;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class launches the chinese chess application.
 *
 * @author Lee Seng Poh
 * @version 10-7-2023
 */
public class ChinesChessApplication extends Application {
    public static final double INIT_BOARD_WIDTH = 300.0;
    public static final double INIT_LOCATION_RADIUS = 13.0;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        ImageView boardImage = createBoardImageView();

        StackPane root = new StackPane();
        root.getChildren().add(boardImage);
        populateBoardLocations(root);

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

    /**
     * Creates an image view of the chinese chess board.
     * @return ImageView of the chinese chess board.
     */
    private ImageView createBoardImageView()
    {
        //Load the image
        Image image = new Image(ChinesChessApplication.class.getResource("/texture/Board.png").toString());
        ImageView boardImage = new ImageView();
        boardImage.setImage(image);
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(INIT_BOARD_WIDTH);
        return boardImage;
    }

    private void populateBoardLocations(Pane parent)
    {
        Pane anchorPane = new Pane();
        parent.getChildren().add(anchorPane);

        double ratio = INIT_LOCATION_RADIUS / INIT_BOARD_WIDTH;
        //spacing between each circle
        double spacingX = 33.0;
        double spacingY = 34.0;
        //x and y coordinates of the circle
        double initialX = 17.0;
        double initialY = 15.0;
        double currentX = initialX;
        double currentY = initialY;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                Circle circle = new Circle(INIT_LOCATION_RADIUS);
                circle.setLayoutX(currentX);
                circle.setLayoutY(currentY);
                anchorPane.getChildren().add(circle);

                circle.radiusProperty().bind(parent.heightProperty().multiply(ratio));

                currentY = currentY + spacingY;
            }
            currentX = currentX + spacingX;
            currentY = initialY;
        }
    }
}
