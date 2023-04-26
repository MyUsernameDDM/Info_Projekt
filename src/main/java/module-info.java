module com.example.info_projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires alphavantage.java;


    opens MainModel to javafx.fxml;
    exports MainModel;
}