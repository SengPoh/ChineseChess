package com.example.chinesechess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * This class launches the chinese chess application.
 *
 * @author Lee Seng Poh
 * @version 20-7-2023
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
        stack.setAlignment(Pos.CENTER);
        stack.setMinSize(0, 0);

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

        primaryStage.show();
    }

    /**
     * Creates an image view of the chinese chess board.
     * @return ImageView of the chinese chess board.
     */
    private ImageView createBoardImageView()
    {
        //Load the image
        Image image = new Image(ChineseChessApplication.class.getResource("/texture/Board.png").toString());
        ImageView boardView = new ImageView();
        boardView.setImage(image);
        boardView.setPreserveRatio(true);
        boardView.setFitWidth(INIT_BOARD_WIDTH);
        return boardView;
    }

    private void populateBoardLocations(Pane parent)
    {
        Pane pane = new Pane();
        HBox hBox = new HBox(pane);

        //set the pane to be of same size as boardView
        pane.maxWidthProperty().bind(getActualWidthProperty(boardView));
        pane.minWidthProperty().bind(getActualWidthProperty(boardView));
        pane.maxHeightProperty().bind(getActualHeightProperty(boardView));
        pane.minHeightProperty().bind(getActualHeightProperty(boardView));
        parent.getChildren().add(pane);

        double locToBoardRatio = INIT_LOCATION_RADIUS / INIT_BOARD_WIDTH;
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

                circle.radiusProperty().bind(pane.heightProperty().multiply(locToBoardRatio));
                circle.layoutXProperty().bind(pane.widthProperty().multiply(currentX / INIT_BOARD_WIDTH));
                circle.layoutYProperty().bind(pane.heightProperty().multiply(currentY / boardView.getFitHeight()));

                currentY = currentY + spacingY;
            }
            currentX = currentX + spacingX;
            currentY = initialY;
        }
    }

    private DoubleBinding getActualWidthProperty(ImageView imageView)
    {
        Image image = boardView.getImage();
        Double aspectRatio = image.getHeight() / image.getWidth();
        DoubleBinding actualWidth = (DoubleBinding) Bindings.min(boardView.fitWidthProperty(), boardView.fitHeightProperty().divide(aspectRatio));

        return actualWidth;
    }

    private DoubleBinding getActualHeightProperty(ImageView imageView)
    {
        Image image = boardView.getImage();
        Double aspectRatio = image.getHeight() / image.getWidth();
        DoubleBinding actualHeight = (DoubleBinding) Bindings.min(boardView.fitHeightProperty(), boardView.fitWidthProperty().multiply(aspectRatio));

        return actualHeight;
    }


    /**
     * Create the menu bar for a player.
     * @param parent The parent pane this menu bar should be placed in.
     */
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
