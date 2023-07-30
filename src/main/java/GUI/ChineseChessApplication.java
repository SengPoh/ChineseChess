package GUI;

import game.Game;
import game.Location;
import game.pieces.Piece;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
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
    private LocationCircle[][] locationCircles;
    private Game game;
    private Piece selectedPiece;        //The piece currently selected.


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        game = new Game();

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
        updateBoard();
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

    /**
     * Populate the board with circles to indicate the locations on the board.
     * @param parent The pane to place these locations in.
     */
    private void populateBoardLocations(Pane parent)
    {
        Pane pane = new Pane();
        parent.getChildren().add(pane);

        //set the pane to be of same size as boardView
        pane.maxWidthProperty().bind(getActualWidthProperty(boardView));
        pane.minWidthProperty().bind(getActualWidthProperty(boardView));
        pane.maxHeightProperty().bind(getActualHeightProperty(boardView));
        pane.minHeightProperty().bind(getActualHeightProperty(boardView));

        locationCircles = new LocationCircle[9][10];

        double locToBoardRatio = INIT_LOCATION_RADIUS / INIT_BOARD_WIDTH;
        //spacing between each circle
        double spacingX = 33.0;
        double spacingY = 34.0;
        //x and y coordinates of the circle
        double initialX = 17.0;
        double initialY = 15.0;
        double currentX = initialX;
        double currentY = initialY;

        for (int i = 0; i < game.getBoardWidth(); i++) {
            for (int j = 0; j < game.getBoardLength(); j++) {
                Location location = new Location(i, j);
                LocationCircle circle = new LocationCircle(location, INIT_LOCATION_RADIUS);
                circle.setLayoutX(currentX);
                circle.setLayoutY(currentY);
                pane.getChildren().add(circle);
                locationCircles[i][j] = circle;

                circle.radiusProperty().bind(pane.heightProperty().multiply(locToBoardRatio));
                circle.layoutXProperty().bind(pane.widthProperty().multiply(currentX / INIT_BOARD_WIDTH));
                circle.layoutYProperty().bind(pane.heightProperty().multiply(currentY / boardView.getFitHeight()));

                currentY = currentY + spacingY;
            }
            currentX = currentX + spacingX;
            currentY = initialY;
        }
    }

    /**
     * Get the actual width property instead of just fit width property of an ImageView in the form of DoubleBinding.
     * @param imageView The ImageView whose actual width is being queried.
     * @return The actual width of the ImageView in the form of DoubleBinding.
     */
    private DoubleBinding getActualWidthProperty(ImageView imageView)
    {
        Image image = imageView.getImage();
        double aspectRatio = image.getHeight() / image.getWidth();

        return (DoubleBinding) Bindings.min(imageView.fitWidthProperty(), imageView.fitHeightProperty().divide(aspectRatio));
    }

    /**
     * Get the actual height property instead of just fit height property of an ImageView in the form of DoubleBinding.
     * @param imageView The ImageView whose actual height is being queried.
     * @return The actual height of the ImageView in the form of DoubleBinding.
     */
    private DoubleBinding getActualHeightProperty(ImageView imageView)
    {
        Image image = imageView.getImage();
        double aspectRatio = image.getHeight() / image.getWidth();

        return (DoubleBinding) Bindings.min(imageView.fitHeightProperty(), imageView.fitWidthProperty().multiply(aspectRatio));
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

    private void updateBoard()
    {
        for (int i = 0; i < game.getBoardWidth(); i++) {
            for (int j = 0; j < game.getBoardLength(); j++) {
                Location location = new Location(i, j);
                Piece piece = game.getPiece(location);
                if (piece != null) {
                    loadPieceImage(piece, location);
                }
            }
        }
    }

    private void loadPieceImage(Piece piece, Location location)
    {
        LocationCircle circle = locationCircles[location.getX()][location.getY()];
        Image image = new Image(getPieceTexturePath(piece));
        circle.setFill(new ImagePattern(image));
    }

    private String getPieceTexturePath(Piece piece)
    {
        String texturePath = "/texture/" + piece.getClass().getSimpleName() + "_";

        if (piece.isBlack()) {
            texturePath = texturePath + "Black";
        } else {
            texturePath = texturePath + "Red";
        }
        texturePath = texturePath + ".png";

        return ChineseChessApplication.class.getResource(texturePath).toString();
    }

    /**
     * What happens when a LocationCircle is clicked.
     * @param event The ActionEvent(mouse click) that is triggered.
     */
    private void locationCircleClick(ActionEvent event)
    {
        LocationCircle circle = (LocationCircle) event.getSource();
        Location location = circle.getLocation();
        if (selectedPiece == null) {        //if no piece has been selected
            selectedPiece = game.getPiece(location);
        } else {
            game.move(selectedPiece, location);
            selectedPiece = null;
        }
    }
}
