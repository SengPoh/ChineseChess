package GUI;

import game.Game;
import game.Location;
import game.Player;
import game.pieces.Piece;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class launches the chinese chess application.
 *
 * @author Lee Seng Poh
 * @version 5-8-2023
 */
public class ChineseChessApplication extends Application {
    public static final double INIT_BOARD_WIDTH = 300.0;
    public static final double INIT_LOCATION_RADIUS = 15.0;
    private ImageView boardView;
    private StackPane boardPane;        //The pane for the chess board view.
    private Pane movesPane;
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

        boardPane = new StackPane(boardView);
        boardPane.setAlignment(Pos.CENTER);
        boardPane.setMinSize(0, 0);

        VBox root = new VBox();
        VBox.setVgrow(boardPane, Priority.ALWAYS);
        createMenuBar(root, game.getPlayer(false));
        root.getChildren().add(boardPane);
        createMenuBar(root, game.getPlayer(true));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ChineseChessApplication.class.getResource("/StyleSheet.css").toString());

        primaryStage.setTitle("Xiang Qi");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

        //Resize the board according to window size
        boardView.fitHeightProperty().bind(boardPane.heightProperty());
        boardView.fitWidthProperty().bind(boardPane.widthProperty());

        populateBoardLocations();
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
     */
    private void populateBoardLocations()
    {
        Pane pane = new Pane();
        boardPane.getChildren().add(pane);

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
                pane.getChildren().add(circle);
                locationCircles[i][j] = circle;
                circle.setOnMouseClicked(this::locationCircleClick);

                circle.setLayoutX(currentX);
                circle.setLayoutY(currentY);
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
    private void createMenuBar(Pane parent, Player player)
    {
        PlayerTilePane pane = new PlayerTilePane(player);
        pane.getStyleClass().add("tile-pane");
        pane.prefWidthProperty().bind(boardView.fitWidthProperty());
        pane.setPrefHeight(pane.getHeight());
        parent.getChildren().add(pane);

        Button undoButton = new Button("Undo");
        undoButton.setMaxWidth(Double.MAX_VALUE);
        undoButton.setOnMouseClicked(this::undoButtonClick);
        Button resignButton = new Button("Resign");
        resignButton.setMaxWidth(Double.MAX_VALUE);
        Button helpButton = new Button("Help");
        helpButton.setMaxWidth(Double.MAX_VALUE);

        pane.getChildren().addAll(undoButton, resignButton, helpButton);
    }

    /**
     * Update the view of the board based on its current condition.
     */
    private void updateBoard()
    {
        for (int i = 0; i < game.getBoardWidth(); i++) {
            for (int j = 0; j < game.getBoardLength(); j++) {
                Location location = new Location(i, j);
                Piece piece = game.getPiece(location);
                loadPieceImage(piece, location);
            }
        }
    }

    /**
     * Load the texture of the piece on the location given.
     * @param piece The piece whose image is loaded.
     * @param location The location of the piece.
     */
    private void loadPieceImage(Piece piece, Location location)
    {
        LocationCircle circle = locationCircles[location.getX()][location.getY()];
        if (piece == null) {
            circle.setFill(Color.TRANSPARENT);
        } else {
            Image image = new Image(getPieceTexturePath(piece));
            circle.setFill(new ImagePattern(image));
            if (!piece.isBlack()) {
                circle.setRotate(180);
            } else {
                circle.setRotate(0);
            }
        }
    }

    /**
     * Get the String path of the specified piece's texture.
     * @param piece The piece whose texture is to be found.
     * @return The path of the texture.
     */
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
     * Display the moves available when a piece is selected.
     */
    private void displayMoves()
    {
        boardPane.getChildren().remove(movesPane);
        if (selectedPiece != null) {
            movesPane = new Pane();
            movesPane.setMouseTransparent(true);
            boardPane.getChildren().add(movesPane);

            movesPane.maxWidthProperty().bind(getActualWidthProperty(boardView));
            movesPane.minWidthProperty().bind(getActualWidthProperty(boardView));
            movesPane.maxHeightProperty().bind(getActualHeightProperty(boardView));
            movesPane.minHeightProperty().bind(getActualHeightProperty(boardView));

            ArrayList<Location> moves = selectedPiece.getMoves();
            for (Location move : moves) {
                Circle circle = new Circle();
                circle.setOpacity(0.1);
                movesPane.getChildren().add(circle);
                LocationCircle locationCircle = locationCircles[move.getX()][move.getY()];

                circle.radiusProperty().bind(locationCircle.radiusProperty().multiply(0.5));
                circle.layoutXProperty().bind(locationCircle.layoutXProperty());
                circle.layoutYProperty().bind(locationCircle.layoutYProperty());
            }
        }
    }

    private void displayWinner()
    {
        BorderPane winnerPane = new BorderPane();
        boardPane.getChildren().add(winnerPane);
        winnerPane.setMaxSize(300, 200);
        winnerPane.setId("winner-pane");

        Text winnerText = new Text(game.getWinner().getColorString().toUpperCase() + " WINS");
        winnerPane.setCenter(winnerText);
        winnerText.setId("winner-text");

        TilePane buttons = new TilePane();
        buttons.getStyleClass().add("tile-pane");
        buttons.setPrefHeight(100);
        winnerPane.setBottom(buttons);
        Button rematchButton = new Button("Rematch");
        buttons.getChildren().add(rematchButton);
    }

    /**
     * Action taken when a LocationCircle is clicked.
     * @param event The MouseEvent that triggers this action.
     */
    private void locationCircleClick(MouseEvent event)
    {
        LocationCircle circle = (LocationCircle) event.getSource();
        Location location = circle.getLocation();
        Piece piece = game.getPiece(location);
        if (selectedPiece == null && piece != null && piece.isBlack() == game.getCurrentPlayer().isBlack()) {
            selectedPiece = game.getPiece(location);
            locationCircles[location.getX()][location.getY()].setStroke(Color.YELLOW);
        } else if (selectedPiece != null) {
            Location pieceLocation = selectedPiece.getLocation();
            locationCircles[pieceLocation.getX()][pieceLocation.getY()].setStroke(Color.TRANSPARENT);
            game.move(selectedPiece, location);
            selectedPiece = null;
            updateBoard();
        }
        displayMoves();

        if (game.getWinner() != null) {
            displayWinner();
        }
    }

    /**
     * Action taken when an undo button is clicked.
     * @param event The MouseEvent that triggers this action.
     */
    private void undoButtonClick(MouseEvent event)
    {
        Button undoButton = (Button) event.getSource();
        PlayerTilePane pane = (PlayerTilePane) undoButton.getParent();
        Player player = pane.getPlayer();
        game.undo(player);
        updateBoard();
    }
}
