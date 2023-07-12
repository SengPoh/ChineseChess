package com.example.chinesechess;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This class launches the chinese chess application.
 *
 * @author Lee Seng Poh
 * @version 12-7-2023
 */
public class ChineseChessApplication extends Application {
    public static final double INIT_BOARD_WIDTH = 300.0;
    public static final double INIT_LOCATION_RADIUS = 13.0;
    private ImageView boardView;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        boardView = createBoardImageView();

        StackPane stack = new StackPane(boardView);
        stack.setMinHeight(0);
        stack.setMinWidth(0);
        VBox root = new VBox();
        VBox.setVgrow(stack, Priority.ALWAYS);

        createMenuBar(root);
        root.getChildren().add(stack);
        createMenuBar(root);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());

        primaryStage.setTitle("Xiang Qi");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

        //Resize the board according to window size
        boardView.fitHeightProperty().bind(stack.heightProperty());
        boardView.fitWidthProperty().bind(stack.widthProperty());

        populateBoardLocations(stack);
    }

    /**
     * Creates an image view of the chinese chess board.
     * @return ImageView of the chinese chess board.
     */
    private ImageView createBoardImageView()
    {
        //Load the image
        Image image = new Image(ChineseChessApplication.class.getResource("/texture/Board.png").toString());
        ImageView boardImage = new ImageView();
        boardImage.setImage(image);
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(INIT_BOARD_WIDTH);
        return boardImage;
    }

    private void populateBoardLocations(Pane parent)
    {
        Pane pane = new Pane();
        parent.getChildren().add(pane);

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
                pane.getChildren().add(circle);

                circle.radiusProperty().bind(parent.heightProperty().multiply(ratio));
                circle.layoutXProperty().bind(boardView.fitWidthProperty().multiply(currentX / INIT_BOARD_WIDTH));
                circle.layoutYProperty().bind(boardView.fitHeightProperty().multiply(currentY / boardView.getFitHeight()));

                currentY = currentY + spacingY;
            }
            currentX = currentX + spacingX;
            currentY = initialY;
        }
    }

    private void createMenuBar(Pane parent)
    {
        TilePane pane = new TilePane();
        pane.getStyleClass().add("tile-pane");
        pane.prefWidthProperty().bind(boardView.fitWidthProperty());
        pane.setPrefHeight(pane.getHeight());
        parent.getChildren().add(pane);

        Button undoButton = new Button("Undo");
        undoButton.setMaxWidth(Double.MAX_VALUE);
        Button resignButton = new Button("Resign");
        resignButton.setMaxWidth(Double.MAX_VALUE);
        Button helpButton = new Button("Help");
        helpButton.setMaxWidth(Double.MAX_VALUE);

        pane.getChildren().addAll(undoButton, resignButton, helpButton);
    }
}
