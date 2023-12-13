module com.example.xd {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.xd to javafx.fxml;
    exports com.example.xd;
}
