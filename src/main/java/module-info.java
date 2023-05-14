module com.example.info_projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires alphavantage.java;
    requires com.google.gson;
    requires okhttp3;
    requires annotations;


    opens MainModel to javafx.fxml;
    exports MainModel;
}