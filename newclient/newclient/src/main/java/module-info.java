module org.demka {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.demka to javafx.fxml;
    exports org.demka;
}