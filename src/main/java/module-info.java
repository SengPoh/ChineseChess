module GUI.ChineseChessApplication {
    requires javafx.controls;
    requires javafx.fxml;

    exports GUI;
    opens GUI to javafx.fxml;
}