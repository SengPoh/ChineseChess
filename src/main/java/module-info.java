module com.example.chinesechess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chinesechess to javafx.fxml;
    exports com.example.chinesechess;
}