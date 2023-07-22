module com.example.chinesechess {
    requires javafx.controls;
    requires javafx.fxml;

    opens GUI to javafx.fxml;
    exports GUI;
    exports game;
}